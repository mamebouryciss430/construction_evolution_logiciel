package com.risk.controller;

import com.risk.Environment;
import com.risk.model.GamePlayModel;
import com.risk.view.IGameModeView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;

/**
 * In GameModeController, the data flow into model object and updates the view
 * whenever data changes.
 */
public class GameModeController implements ViewActionListener {

    /** The view. */
    private IGameModeView theGameModeView;

    /**
     * Constructor initializes values and sets the screen too visible.
     */
    GameModeController() {
        this.theGameModeView =
                Environment.getInstance().getViewManager().newGameModeView();
        this.theGameModeView.addActionListener(this);
        this.theGameModeView.showView();
    }

    /**
     * This method performs action, by Listening the action event set in view.
     *
     * @param event the action event
     * @see ViewActionListener
     */
    @Override
    public void actionPerformed(ViewActionEvent event) {
        if (IGameModeView.ACTION_SINGLE_MODE.equals(event.getSource())) {
            new NewGameController();
            this.theGameModeView.hideView();
        } else if (IGameModeView.ACTION_TOURNAMENT_MODE.equals(event.getSource())) {
            new TournmentDetailController();
            this.theGameModeView.hideView();
        } else if (IGameModeView.ACTION_LOAD_GAME.equals(event.getSource())) {
            final GamePlayModel gamePlayModel = theGameModeView.loadGamePlayModel();
            if (gamePlayModel != null) {
                new GamePlayController(gamePlayModel);
            }

            this.theGameModeView.hideView();
        }
    }
}
