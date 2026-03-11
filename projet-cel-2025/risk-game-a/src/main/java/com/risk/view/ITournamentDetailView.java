package com.risk.view;

import java.io.File;

public interface ITournamentDetailView extends IView {

    String ACTION_SAVE_AND_PLAY = "action-save-and-play";
    String ACTION_EXIT = "action-exit";

    String ACTION_BROWSE_MAP_1 = "action-browse-map-1";
    String ACTION_BROWSE_MAP_2 = "action-browse-map-2";
    String ACTION_BROWSE_MAP_3 = "action-browse-map-3";
    String ACTION_BROWSE_MAP_4 = "action-browse-map-4";
    String ACTION_BROWSE_MAP_5 = "action-browse-map-5";

    String ACTION_VALIDATE_MAP = "action-validate-map";


    int getNoOfGames();

    int getNoOfPlayers();

    int getNoOfTurnsText();

    int getNoOfMaps();

    File getMap1();
    File getMap2();
    File getMap3();
    File getMap4();
    File getMap5();

    String getPlayer1Name();
    String getPlayer2Name();
    String getPlayer3Name();
    String getPlayer4Name();
}
