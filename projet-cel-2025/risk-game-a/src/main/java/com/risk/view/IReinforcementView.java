package com.risk.view;

import com.risk.model.CountryModel;

public interface IReinforcementView extends IView {

    String ACTION_ADD = "reinforcement-view-action-add";
    String ACTION_ADD_MORE = "reinforcement-view-action-add-more";
    String ACTION_EXIT_CARD = "reinforcement-view-action-exit-card";
    String ACTION_SAVE = "reinforcement-view-action-save";


    int getNumOfTroops();

    CountryModel getCountryModel();

    int getCardId();
}
