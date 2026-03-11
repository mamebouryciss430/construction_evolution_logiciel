package com.risk.model;

import com.risk.services.MapIO;
import com.risk.services.MapValidate;
import com.risk.services.StartUpPhase;
import com.risk.services.Util.WindowUtil;
import com.risk.controller.GamePlayController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TournamentModel {

    /** Static flag to check if tournament is being played*/
    public static boolean isTournament = false;

    /**
     * Constrcutor for TournamentModel
     *
     */
    public TournamentModel(){
        isTournament = true;
    }

    /**
     * Method for checking if the map is valid or not,
     * then uploading it to play the tournament
     *
     * @param mapList List of maps to be updated when a file is uploaded
     * @return Map File
     */
    public File checkAndLoadMap(List<MapIO> mapList) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a Map File");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Map File Extensions (*.map or *.MAP)", "*.map", "*.MAP"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            String fileName = selectedFile.getAbsolutePath();
            System.out.println("File location: " + fileName);
            MapValidate mapValidate = new MapValidate();

            if (mapValidate.validateMapFile(fileName)) {
                MapIO readMap = new MapIO(mapValidate);
                mapList.add(readMap);
                return selectedFile;
            }
            WindowUtil.popUpWindow("Invalid Map", "Problem with map file", "Please selecte another file");

        }

        return null;
    }

    /**
     *
     * Method for controlling the flow of each game in the tournament
     *
     * @param playerList List of players playing
     * @param numberOfTurnsToPlay NUmber of turns to be played in each game
     * @param gameCount Number of the game being played
     * @param mapIO Map on which the game would be played
     * @param textArea Text Area where the game details would be printed
     * @return HashMAp with the winner name and game number.
     */

    public HashMap<Player, Integer> playGame(List<Player> playerList, int numberOfTurnsToPlay, int gameCount, MapIO mapIO, TextArea textArea) {

        List<Player> currentGamePlayerList = initializePlayers(playerList);
        GamePlayController gamePlayController = createGameController(mapIO, currentGamePlayerList);

        executeStartupPhase(mapIO, currentGamePlayerList, gamePlayController);

        while (numberOfTurnsToPlay > 0) {
            Iterator<Player> playerListIterator = currentGamePlayerList.iterator();

            while (playerListIterator.hasNext()) {
                Player nextPlayer = playerListIterator.next();

                HashMap<Player, Integer> winnerMap =
                        playTurnForPlayer(nextPlayer, currentGamePlayerList,
                                gamePlayController, mapIO, gameCount);

                if (winnerMap != null) {
                    return winnerMap;
                }
            }

            numberOfTurnsToPlay--;
        }

        HashMap<Player, Integer> winnerMap = new HashMap<>();
        winnerMap.put(null, gameCount);
        return winnerMap;
    }


    private HashMap<Player, Integer> playTurnForPlayer(
            Player player,
            List<Player> currentGamePlayerList,
            GamePlayController gamePlayController,
            MapIO mapIO,
            int gameCount) {

        Player.setPlayerPlaying(player);

        Card card = new Card();
        card.automateCardWindow(Player.currentPlayer);
        List<Card> playerOwnedCards = Player.currentPlayer.getCardList();

        if (playerOwnedCards != null) {
            List<Card> cards = card.generateValidCardCombination(playerOwnedCards);
            if (cards != null && cards.size() >= 3) {
                card.cardsToBeExchanged(cards);
                card.setCardsToExchange(playerOwnedCards);
                gamePlayController.exchangeCards(card);
            }
        }

        ObservableList<Country> observableListReinforcementPhase =
                FXCollections.observableArrayList(Player.currentPlayer.getPlayerCountries());
        player.noOfReinforcementArmies(Player.currentPlayer);

        if (Player.currentPlayer.getArmyCount() > 0) {
            Player.currentPlayer.reinforcementPhase(
                    observableListReinforcementPhase, null, gamePlayController.getGamePlayerList());
        }

        ListView<Country> listViewOfCountries = new ListView<>(
                FXCollections.observableArrayList(Player.currentPlayer.getPlayerCountries()));

        HashMap<Player, Integer> winnerMap =
                handleAttackPhase(listViewOfCountries, currentGamePlayerList,
                        gamePlayController, gameCount);

        if (winnerMap != null) {
            return winnerMap;
        }

        ListView<Country> listViewOfCountriesForFortification = new ListView<>(
                FXCollections.observableArrayList(Player.currentPlayer.getPlayerCountries()));

        if (PlayerPhaseManager.isFortificationPhaseValid(mapIO, Player.currentPlayer, Player.currentPlayer)) {
            Player.currentPlayer.getPlayerBehaviour()
                    .fortificationPhase(listViewOfCountriesForFortification, null, Player.currentPlayer);
        }

        return null;
    }

    private HashMap<Player, Integer> handleAttackPhase(
            ListView<Country> listViewOfCountries,
            List<Player> currentGamePlayerList,
            GamePlayController gamePlayController,
            int gameCount) {

        while (Player.currentPlayer.playerCanAttack(listViewOfCountries)) {

            // Attack
            Player.currentPlayer.getPlayerBehaviour()
                    .attackPhase(listViewOfCountries, null, Player.currentPlayer);

            // If player won a country → allocate card
            if (Player.currentPlayer.getCountryWon() > 0) {
                gamePlayController.setPlayerPlaying(Player.currentPlayer);
                gamePlayController.allocateCardToPlayer();
            }

            // Check players who lost
            List<Player> lostPlayerList = PlayerVictoryManager.playersWhoLost(
                    currentGamePlayerList,
                    Player.currentPlayer,
                    Player.currentPlayer
            );

            if (!lostPlayerList.isEmpty()) {
                for (Player p : lostPlayerList) {
                    System.out.println(p.getName() + " lost the game");
                    currentGamePlayerList.remove(p);
                }
            }

            // Check winner
            Player winner = PlayerVictoryManager
                    .winnerIfAny(currentGamePlayerList, Player.currentPlayer)
                    .orElse(null);

            if (winner != null) {
                HashMap<Player, Integer> winnerMap = new HashMap<>();
                winnerMap.put(winner, gameCount);
                System.out.println(winner.getName() + " won the game");
                return winnerMap;
            }
        }

        return null;
    }

    private List<Player> initializePlayers(List<Player> playerList) {
        List<Player> current = new ArrayList<>();
        current.addAll(playerList);
        return current;
    }

    private GamePlayController createGameController(MapIO mapIO, List<Player> players) {
        HashMap<String, String> map = new HashMap<>();
        for (Player p : players) {
            map.put(p.getName(), p.getPlayerType());
        }
        GamePlayController controller = new GamePlayController(mapIO, map);
        controller.setGamePlayerList(new ArrayList<>());
        return controller;
    }

    private void executeStartupPhase(MapIO mapIO, List<Player> players, GamePlayController controller) {
        StartUpPhase start = new StartUpPhase();
        controller.setCardStack(start.assignCardToCountry(mapIO));

        Player dummy = new Player();
        dummy.assignArmiesToPlayers(players);
        start.assignCountryToPlayer(mapIO, players);

        for (Player p : players) {
            while (p.getArmyCount() > 0) {
                PlayerPhaseManager.automaticAssignPlayerArmiesToCountry(p, p);
            }
            controller.getGamePlayerList().add(p);
        }
    }
}
