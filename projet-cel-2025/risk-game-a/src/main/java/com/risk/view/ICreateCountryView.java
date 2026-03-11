package com.risk.view;

import com.risk.model.ContinentsModel;

public interface ICreateCountryView extends IView {

    String ACTION_ADD = "create-country-view-action-add";
    String ACTION_NEXT = "create-country-view-action-next";

    String getCountryValue();

    ContinentsModel getContinentModel();
}
