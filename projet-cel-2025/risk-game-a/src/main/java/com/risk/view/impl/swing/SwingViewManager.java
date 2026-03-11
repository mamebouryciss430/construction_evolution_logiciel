package com.risk.view.impl.swing;

import com.risk.model.ContinentsModel;
import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;
import com.risk.view.*;

import java.util.List;

public class SwingViewManager implements IViewManager {

    @Override
    public IWelcomeScreenView newWelcomeScreenView() {
        return new SwingWelcomeScreenView();
    }

    @Override
    public IGameModeView newGameModeView() {
        return new SwingGameModeView();
    }

    @Override
    public INewGameView newNewGameView() {
        return new SwingNewGameView();
    }

    @Override
    public ITournamentDetailView newTournamentDetailView() {
        return new SwingTournamentDetailView();
    }

    @Override
    public IStartUpView newStartUpView(GamePlayModel gamePlayModel, PlayerModel playerModel) {
        return new SwingStartUpView(gamePlayModel, playerModel);
    }

    @Override
    public IAttackView newAttackView(GamePlayModel gamePlayModel) {
        return new SwingAttackView(gamePlayModel);
    }

    @Override
    public IFortificationView newFortificationView(GamePlayModel gamePlayModel) {
        return new SwingFortificationView(gamePlayModel);
    }

    @Override
    public IReinforcementView newReinforcementView(GamePlayModel gamePlayModel) {
        return new SwingReinforcementView(gamePlayModel);
    }

    @Override
    public ICreateContinentView newCreateContinentView() {
        return new SwingCreateContinentView();
    }

    @Override
    public ICreateCountryView newCreateCountryView(List<ContinentsModel> listOfContinents) {
        return new SwingCreateCountryView(listOfContinents);
    }

    @Override
    public IConnectCountryView newConnectCountryView(GameMapModel gameMapModel) {
        return new SwingConnectCountryView(gameMapModel);
    }

    @Override
    public IEditContinentView newEditContinentView(List<ContinentsModel> listOfContinents) {
        return new SwingEditContinentView(listOfContinents);
    }
}
