package com.risk.view;

import com.risk.model.CountryModel;

public interface IFortificationView extends IView {

    String ACTION_MOVE = "fortification-view-action-move";
    String ACTION_SAVE = "fortification-view-action-save";
    String ACTION_NEXT = "fortification-view-action-next";

    String ACTION_FROM_COUNTRY_CHANGED = "fortification-view-action-from-country-changed";
    String ACTION_ITEM_FROM_COUNTRY_CHANGED = "fortification-view-action-item-from-country-changed";

    CountryModel getFromCountryModel();

    CountryModel getToCountryModel();

    int getNumOfTroops();

    int getFromCountryIndex();
}
