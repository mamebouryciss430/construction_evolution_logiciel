package com.risk.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;
import com.risk.model.strategy.*;
import com.risk.utilities.Validation;


/**
 * In GamePlayController, the data flow into model object and updates the view
 * whenever data changes.
 *
 * @version 1.0.0
 *
 */

public class GamePlayController implements ActionListener {

    /**
     * to solve duplication problem
     */
    private static final String VALID = "valid";
    public GamePlayModel gamePlayModel;
    /**
     * The val.
     */
    private Validation val = new Validation();
    private boolean displayFlag = false;

    /**
     * Constructor initializes values and sets the screen too visible
     *
     * @param gamePlayModel
     */
    public GamePlayController(GamePlayModel gamePlayModel) {
        this.gamePlayModel = gamePlayModel;
        gamePlay();
    }

    /**
     * Constructor initializes values and sets the screen too visible
     *
     * @param gamePlayModel
     */
    public GamePlayController(GamePlayModel gamePlayModel, int noOfTurn) {
        this.gamePlayModel = gamePlayModel;
        GameMapModel gameMap = gamePlayModel.getGameMap();
        gameMap.setPlayerTurn(gamePlayModel.getPlayers()
                .get(gamePlayModel.getGameMap().getPlayerIndex()));

        int tempNoOfTurn = 0;
        while (noOfTurn > tempNoOfTurn) {
            if (!val.endOfGame(this.gamePlayModel)) {
                PlayerModel currentPlayer = gameMap.getPlayerTurn();
                applyStrategyAndPlay();
                int index = gameMap.getPlayerIndex() + 1;
                if (index < gamePlayModel.getPlayers().size()) {
                    gameMap.setPlayerIndex(index);
                } else {
                    tempNoOfTurn++;
                    index = 0;
                    gameMap.setPlayerIndex(index);
                }
                gamePlayModel.getPlayers().get(gameMap.getPlayerIndex()).callObservers();
            } else {
                tempNoOfTurn = noOfTurn + 1;
                displayFlag = true;
                showWinner();
            }
        }
        if (!displayFlag) {
            showWinner();
        }
    }


        private void applyStrategyAndPlay () {
            String PlayerType = this.gamePlayModel.getGameMap().getPlayerTurn().getTypePlayer();
            if ("Aggressive".equals(PlayerType)) {
                this.gamePlayModel.getGameMap().getPlayerTurn()
                        .setStrategy(new AgressivePlayerStrategy(this.gamePlayModel));
            } else if ("Benevolent".equals(PlayerType)) {
                this.gamePlayModel.getGameMap().getPlayerTurn()
                        .setStrategy(new BenevolentPlayerStrategy(this.gamePlayModel));
            } else if ("Random".equals(PlayerType)) {
                this.gamePlayModel.getGameMap().getPlayerTurn()
                        .setStrategy(new RandomPlayerStrategy(this.gamePlayModel));
            } else if ("Cheater".equals(PlayerType)) {
                this.gamePlayModel.getGameMap().getPlayerTurn()
                        .setStrategy(new CheaterPlayerStrategy(this.gamePlayModel));
            }
            this.gamePlayModel.getGameMap().getPlayerTurn().executeReinforcement();
            this.gamePlayModel.getGameMap().getPlayerTurn().executeAttack();
            this.gamePlayModel.getGameMap().getPlayerTurn().executeFortification();

        }

        private void showWinner () {
            String nameOfWinner = val.determineWinner(this.gamePlayModel);
            if ("draw".equals(nameOfWinner)) {
                System.out.println(" Game is draw ");
                JOptionPane.showOptionDialog(null, "The game is draw", VALID, JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
            } else {
                System.out.println(nameOfWinner + " is winner ");
                JOptionPane.showOptionDialog(null,
                        "Bravo! You have won! Game is over!" + nameOfWinner + "is the winner", VALID,
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
            }
        }


        public  void gamePlay(){
            this.gamePlayModel.getGameMap().setPlayerTurn(this.gamePlayModel.getPlayers().get(this.gamePlayModel.getGameMap().getPlayerIndex()));
            new PlayerController(this.gamePlayModel);
        }
        /**
         * This method performs action, by Listening the action event set in view.
         *
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        @Override
        public void actionPerformed (ActionEvent actionEvent){

        }

}

