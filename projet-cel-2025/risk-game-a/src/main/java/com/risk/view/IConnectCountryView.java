package com.risk.view;

import com.risk.model.CountryModel;

public interface IConnectCountryView extends IView {

    String ACTION_ADD = "connect-country-view-action-add";
    String ACTION_SAVE = "connect-country-view-action-save";
    String ACTION_REMOVE = "connect-country-view-action-remove";

    String SELECTED_CHANGED_LEFT = "connect-country-view-selected-changed-left";
    String SELECTED_CHANGED_RIGHT = "connect-country-view-selected-changed-RIGHT";

    CountryModel getCountryParentLeft();

    CountryModel getCountryParentRight();

    boolean isSelectedLeftModelIndex(int i);
}