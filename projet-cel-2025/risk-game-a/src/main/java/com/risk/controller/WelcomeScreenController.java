package com.risk.controller;

import com.risk.Environment;
import com.risk.view.IWelcomeScreenView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;


/**
 * "WelcomeScreenController" is the main class. It represents a welcome window
 * containing main buttons to edit/create map file and start playing game
 *
 * @author gursimransingh
 */
public class WelcomeScreenController implements ViewActionListener {
    private IWelcomeScreenView theView;

    /**
     * Constructor initializes values and sets the screen too visible
     */
    public WelcomeScreenController() {
        this.theView = Environment.getInstance().getViewManager().newWelcomeScreenView();
        this.theView.addActionListener(this);
        this.theView.showView();
    }

    /**
     * This method performs action, by Listening the action event set in view.
     *
     * @see ViewActionListener
     */
    @Override
    public void actionPerformed(ViewActionEvent event) {
        if (IWelcomeScreenView.ACTION_CREATE_MAP.equals(event.getSource())) {
            // open new game window
            this.showCreateMapWindow();
        } else if (IWelcomeScreenView.ACTION_EDIT_MAP.equals(event.getSource())) {
            // open load game window
            this.showEditGameWindow();
        } else if (IWelcomeScreenView.ACTION_PLAY_MAP.equals(event.getSource())) {
            // open create game window
            this.showPlayGameWindow();
        } else if (IWelcomeScreenView.ACTION_EXIT.equals(event.getSource())) {
            // exit game
            this.exitGame();
        }
    }

    /**
     * exit game
     */
    private void exitGame() {
        this.theView.hideView();
    }

    /**
     * show play game window
     */
    private void showPlayGameWindow() {
        new GameModeController();
        this.theView.hideView();
    }

    /**
     * show edit game window
     */
    private void showEditGameWindow() {
        new EditContinentController();
        this.theView.hideView();
    }

    /**
     * show create map window
     */
    private void showCreateMapWindow() {
        new CreateContinentController();
        this.theView.hideView();
    }

}
