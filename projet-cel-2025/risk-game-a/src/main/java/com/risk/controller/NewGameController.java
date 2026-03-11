package com.risk.controller;

import com.risk.Environment;
import com.risk.model.*;
import com.risk.utilities.Validation;
import com.risk.view.INewGameView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * In NewGameController, the data flow into model object and updates the view
 * whenever data changes.
 *
 * @version 1.0.0
 *
 */

public class NewGameController implements ViewActionListener {

    /** The view. */
    private INewGameView theView;

    /** The list of players. */
    private ArrayList<PlayerModel> listOfPlayers = new ArrayList<>();

    /** The game map model. */
    private GameMapModel gameMapModel = new GameMapModel();

    /** The game play model. */
    private GamePlayModel gamePlayModel = new GamePlayModel();

    /** The no of players. */
    private int noOfPlayers;

    /** The Player name. */
    private String PlayerName = "";

    /** The Player type. */
    private String PlayerType = "";

    private static  final String INVALID = "Invalid";

    /**
     * Constructor initializes values and sets the screen too visible.
     */
    public NewGameController() {
        this.theView = Environment.getInstance().getViewManager().newNewGameView();
        this.theView.addActionListener(this);
        this.theView.showView();
    }

    /**
     * This method performs action, by Listening the action event set in view.
     *
     * @param event the action event
     * @see ViewActionListener
     */
    @Override
    public void actionPerformed(ViewActionEvent event) {

        if (INewGameView.ACTION_BROWSE_MAP.equals(event.getSource())) {
            handleBrowseMap();
        } else if (INewGameView.ACTION_NEXT.equals(event.getSource())) {
            handleNext();
        } else if (INewGameView.ACTION_CANCEL.equals(event.getSource())) {
           handleCancel();
        }
    }

    private void handleBrowseMap () {
        try {
            gameMapModel = theView.loadGameMapModel();
            if (gameMapModel == null) {
                return;
            }

            Validation MapValidation = new Validation();
            boolean flag1 = MapValidation.emptyLinkCountryValidation(this.gameMapModel);

            boolean flag3 = MapValidation.emptyContinentValidation(this.gameMapModel);
            boolean flag2 = MapValidation.checkInterlinkedContinent(this.gameMapModel);
            boolean nonContinent = MapValidation.nonContinentValidation(this.gameMapModel);
            System.out.println(flag1 + " " + flag2 + " " + flag3);
            if (nonContinent) {
                showMessage("One of the continent is invalid,Map is not linked properly");
                return;
            }
            if (flag1) {
                showMessage("Empty continent validation failed");
                return;
            }
            if (flag3) {
                showMessage("Empty link country validation failed");
                return;
            }
            System.out.println(" All the map validations are correct");
            theView.showMessageDialog(
                    "File Loaded Successfully! Click Next to Play!",
                    "Map Loaded");


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleNext(){
        noOfPlayers = theView.getNumOfPlayers();
        try {
            playerValidation();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public void handleCancel (){
        new WelcomeScreenController();
        this.theView.hideView();
    }

    public void showMessage (String message){
        System.out.println(message);
        theView.showOptionDialog(
                message,
                INVALID);
    }
    /**
     * Check for the player validation.
     *
     * @throws ParseException the parse exception
     */

        private void playerValidation() throws ParseException {
            if (gameMapModel.getCountries().size() <= noOfPlayers) {
                theView.showMessageDialog(
                        "Number of countries in the Map is less than Number of Players. Select map or player Again!",
                        "Map Loaded"
                );
                return;
            }

            System.out.println("no of players " + noOfPlayers);

            // Compteurs pour générer des noms par défaut
            Map<String, Integer> counters = new HashMap<>();
            counters.put("Human", 1);
            counters.put("Aggressive", 1);
            counters.put("Benevolent", 1);
            counters.put("Random", 1);
            counters.put("Cheater", 1);

            for (int i = 0; i < noOfPlayers; i++) {
                String type = getPlayerType(i);
                String name = getPlayerName(i);

                PlayerModel pm = createPlayer(type, name, counters);
                listOfPlayers.add(pm);
            }

            gamePlayModel.setGameMap(gameMapModel);
            gamePlayModel.setPlayers(listOfPlayers);
            gamePlayModel.setCards(gamePlayModel.getCardFromJSON());

            new StartupController(gamePlayModel);
            theView.hideView();
        }

        /** Récupère le type du joueur selon son index */
        private @Nullable String getPlayerType(int index) {
            switch (index) {
                case 0: return theView.getPlayer1Type();
                case 1: return theView.getPlayer2Type();
                case 2: return theView.getPlayer3Type();
                case 3: return theView.getPlayer4Type();
                case 4: return theView.getPlayer5Type();
                default: return null;
            }
        }

        /** Récupère le nom du joueur selon son index */
        private @Nullable String getPlayerName(int index) {
            switch (index) {
                case 0: return theView.getPlayer1Name();
                case 1: return theView.getPlayer2Name();
                case 2: return theView.getPlayer3Name();
                case 3: return theView.getPlayer4Name();
                case 4: return theView.getPlayer5Name();
                default: return null;
            }
        }

        /** Crée un joueur avec type et nom normalisés */
        @Contract("_, _, _ -> new")
        private @NotNull PlayerModel createPlayer(String type, String name, Map<String, Integer> counters) {
            if (type == null || type.trim().isEmpty()) {
                type = "Human";
            }

            if (name == null || name.trim().isEmpty()) {
                int count = counters.get(type);
                name = type + " " + count;
                counters.put(type, count + 1);
            }

            System.out.println("PlayerName " + name);

            return new PlayerModel(
                    name,
                    type,
                    0,
                    Color.WHITE,
                    0,
                    new ArrayList<CountryModel>(),
                    new ArrayList<CardModel>()
            );
        }

}
