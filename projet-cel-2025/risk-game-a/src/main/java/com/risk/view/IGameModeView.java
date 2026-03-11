package com.risk.view;

import com.risk.model.GamePlayModel;

public interface IGameModeView extends IView {

    String ACTION_SINGLE_MODE = "action-single-mode";
    String ACTION_TOURNAMENT_MODE = "action-tournament-mode";
    String ACTION_LOAD_GAME = "action-load-game";

    GamePlayModel loadGamePlayModel();
}
