package com.risk.controller;

import com.risk.Environment;
import com.risk.model.*;
import com.risk.utilities.Validation;
import com.risk.view.ITournamentDetailView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;


/**
 * @author gursimransingh
 */

public class TournmentDetailController implements ViewActionListener {

    /** The view. */
    private ITournamentDetailView theTournamentDetailView;

    /** The tournament model. */
    private TournamentModel theTournamentModel = new TournamentModel();

    /** The map file. */
    private File[] mapFile = new File[5];

    /** The no of maps. */
    private int noOfMaps;

    /** The game play model. */
    private GamePlayModel gamePlayModel;

    /** The map loaded. */
    private boolean mapLoaded = false;

    /** The no of players. */
    private int noOfPlayers;

    /** The Player name. */
    private String PlayerType, PlayerName;

    /**
     * Constructor initializes values and sets the screen too visible.
     */
    TournmentDetailController() {
        this.theTournamentDetailView =
                Environment.getInstance().getViewManager().newTournamentDetailView();
        this.theTournamentDetailView.addActionListener(this);
        this.theTournamentDetailView.showView();

        for (int i = 0; i < 5; i++) {
            mapFile[i] = null;
        }
    }

    /**
     * This method performs action, by Listening the action event set in view.
     *
     * @param event the action event
     * @see ViewActionListener
     */
    @Override
    public void actionPerformed(ViewActionEvent event) {
        if (ITournamentDetailView.ACTION_SAVE_AND_PLAY.equals(event.getSource())) {
            /* The valid game. */
            boolean validGame = true;

            /* The no of games. */
            int noOfGames = theTournamentDetailView.getNoOfGames();
            this.theTournamentModel.setNoOfGames(noOfGames);
            noOfPlayers = theTournamentDetailView.getNoOfPlayers();
            try {
                playerValidation();
            } catch (ParseException e) {
                validGame = false;
                e.printStackTrace();
            }

            /* The no of turns. */
            int noOfTurns = theTournamentDetailView.getNoOfTurnsText();
            if (!mapLoaded) {
                validGame = false;
            }
            if (validGame) {
                for (int i = 0; i < noOfGames; i++) {
                    for (int j = 0; j < this.theTournamentModel.getGamePlay().size(); j++) {
                        new StartUpTournamentController(this.theTournamentModel.getGamePlay().get(j), noOfTurns);
                    }
                }

                this.theTournamentDetailView.hideView();
            }
        } else if (ITournamentDetailView.ACTION_EXIT.equals(event.getSource())) {
            this.theTournamentDetailView.hideView();
        } else if (ITournamentDetailView.ACTION_BROWSE_MAP_1.equals(event.getSource())) {
            mapFile[0] = theTournamentDetailView.getMap1();
        } else if (ITournamentDetailView.ACTION_BROWSE_MAP_2.equals(event.getSource())) {
            mapFile[1] = theTournamentDetailView.getMap2();
        } else if (ITournamentDetailView.ACTION_BROWSE_MAP_3.equals(event.getSource())) {
            mapFile[2] = theTournamentDetailView.getMap3();
        } else if (ITournamentDetailView.ACTION_BROWSE_MAP_4.equals(event.getSource())) {
            mapFile[3] = theTournamentDetailView.getMap4();
        } else if (ITournamentDetailView.ACTION_BROWSE_MAP_5.equals(event.getSource())) {
            mapFile[4] = theTournamentDetailView.getMap5();
        } else if (ITournamentDetailView.ACTION_VALIDATE_MAP.equals(event.getSource())) {
            noOfMaps = theTournamentDetailView.getNoOfMaps();
            for (int i = 0; i < noOfMaps; i++) {
                if (mapFile[i] == null) {
                    theTournamentDetailView.showOptionDialog(
                            "Select the " + (i + 1) + " appropriate maps",
                            "Invalid");
                } else {
                    if (mapVerification(mapFile[i])) {
                        if (noOfMaps == (i + 1)) {
                            mapLoaded = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * Map verification.
     *
     * @param mapFile the map file
     * @return true, if successful
     */
    public boolean mapVerification(File mapFile) {
        gamePlayModel = new GamePlayModel();
        boolean validMap = true;
        GameMapModel gameMapModel = new GameMapModel(mapFile);

        Validation MapValidation = new Validation();
        boolean flag1 = MapValidation.emptyLinkCountryValidation(gameMapModel);

        boolean flag3 = MapValidation.emptyContinentValidation(gameMapModel);
        boolean flag2 = MapValidation.checkInterlinkedContinent(gameMapModel);
        System.out.println(flag1 + " " + flag2 + " " + flag3);
        if (!(MapValidation.nonContinentValidation(gameMapModel))) {
            if (!(MapValidation.emptyLinkCountryValidation(gameMapModel))) {
                if (!(MapValidation.emptyContinentValidation(gameMapModel))) {
                    gamePlayModel.setGameMap(gameMapModel);
                    this.theTournamentModel.getGamePlay().add(gamePlayModel);
                    System.out.println(" All the map validations are correct");
                    try {
                        theTournamentDetailView.showMessageDialog(
                                "File Loaded Successfully! Click Next to Play!",
                                "Map Loaded");
                    } catch (Exception e) {
                        validMap = false;
                        e.printStackTrace();
                    }

                } else {
                    validMap = false;
                    System.out.println("Empty link country validation failed");
                    theTournamentDetailView.showOptionDialog(
                            "Empty continent validation failed",
                            "Invalid");
                }
            } else {
                validMap = false;
                System.out.println("Empty continent validation failed");
                theTournamentDetailView.showOptionDialog(
                        "Empty link country validation failed",
                        "Invalid");
            }
        } else {
            validMap = false;
            System.out.println("One of the continent is invalid");
            theTournamentDetailView.showOptionDialog(
                    "Map is not linked properly",
                    "Invalid");

        }
        return validMap;
    }

    /**
     * Check for the player validation.
     *
     * @throws ParseException the parse exception
     */
    private void playerValidation() throws ParseException {
        int a = 1, b = 1, r = 1, c = 1;

        /* The list of players. */
        ArrayList<PlayerModel> listOfPlayers = new ArrayList<>();
        for (int i = 0; i < noOfPlayers; i++) {
            if (i == 0) {
                PlayerType = theTournamentDetailView.getPlayer1Name();
            } else if (i == 1) {
                PlayerType = theTournamentDetailView.getPlayer2Name();
            } else if (i == 2) {
                PlayerType = theTournamentDetailView.getPlayer3Name();
            } else if (i == 3) {
                PlayerType = theTournamentDetailView.getPlayer4Name();
            }

            if ("Aggressive".equals(PlayerType)) {
                PlayerName = "Aggressive " + a;
                a++;
            } else if ("Benevolent".equals(PlayerType)) {
                PlayerName = "Benevolent " + b;
                b++;
            } else if ("Random".equals(PlayerType)) {
                PlayerName = "Random " + r;
                r++;
            } else if ("Cheater".equals(PlayerType)) {
                PlayerName = "Cheater " + c;
                c++;
            }

            PlayerModel pm = new PlayerModel(PlayerName, PlayerType, 0, Color.WHITE, 0, new ArrayList<CountryModel>(),
                    new ArrayList<CardModel>());
            listOfPlayers.add(pm);
        }

        for (int i = 0; i < noOfMaps; i++) {
            this.theTournamentModel.getGamePlay().get(i).setPlayers(listOfPlayers);
            this.theTournamentModel.getGamePlay().get(i).setCards(gamePlayModel.getCardFromJSON());
        }

    }

}
