package com.risk.view;

import com.risk.model.CountryModel;

public interface IAttackView extends IView {


    String ACTION_NEXT = "attack-view-action-next";
    String ACTION_SINGLE = "attack-view-action-single";
    String ACTION_ALLOUT = "attack-view-action-allout";
    String ACTION_MOVE = "attack-view-action-move";
    String ACTION_SAVE = "attack-view-action-save";

    String ACTION_ATTACK_COUNTRY_CHANGED = "attack-view-action-attack-country-changed";
    String ACTION_DEFEND_COUNTRY_CHANGED = "attack-view-action-defend-country-changed";


    int getAttackCountryIndex();

    int getDefendCountryIndex();

    int getNumOfDiceAttack();

    int getNumOfDiceDefend();

    CountryModel getAttackCountryModel();

    CountryModel getDefendCountryModel();

    int getNumOfArmiesToBeMoved();
}
