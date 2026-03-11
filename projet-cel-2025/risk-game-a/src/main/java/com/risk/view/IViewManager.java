package com.risk.view;

import com.risk.model.ContinentsModel;
import com.risk.model.GameMapModel;
import com.risk.model.GamePlayModel;
import com.risk.model.PlayerModel;

import java.util.List;

public interface IViewManager {

    IWelcomeScreenView newWelcomeScreenView();

    IGameModeView newGameModeView();

    INewGameView newNewGameView();

    ITournamentDetailView newTournamentDetailView();

    IStartUpView newStartUpView(GamePlayModel gamePlayModel, PlayerModel playerModel);

    IAttackView newAttackView(GamePlayModel gamePlayModel);

    IFortificationView newFortificationView(GamePlayModel gamePlayModel);

    IReinforcementView newReinforcementView(GamePlayModel gamePlayModel);

    ICreateContinentView newCreateContinentView();

    ICreateCountryView newCreateCountryView(List<ContinentsModel> listOfContinents);

    IConnectCountryView newConnectCountryView(GameMapModel gameMapModel);

    IEditContinentView newEditContinentView(List<ContinentsModel> listOfContinents);
}
