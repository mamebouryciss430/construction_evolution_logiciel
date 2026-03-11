package com.risk.view;

import com.risk.model.GameMapModel;

public interface INewGameView extends IView {

    String ACTION_BROWSE_MAP = "action-browse-map";
    String ACTION_NEXT = "action-next";
    String ACTION_CANCEL = "action-cancel";

    int getNumOfPlayers();
    GameMapModel loadGameMapModel();

    String getPlayer1Type();
    String getPlayer1Name();

    String getPlayer2Type();
    String getPlayer2Name();

    String getPlayer3Type();
    String getPlayer3Name();

    String getPlayer4Type();
    String getPlayer4Name();

    String getPlayer5Type();
    String getPlayer5Name();
}
