package com.risk.view;

import com.risk.model.CountryModel;

public interface IStartUpView extends IView {

    String ACTION_ADD = "action-add";
    String ACTION_NEXT = "action-next";

    int getNumOfTroops();

    CountryModel getCountryModel();

    void setWelcomeLabel(String s);
}
