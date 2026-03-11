package com.risk.view;

import com.risk.model.ContinentsModel;

public interface IEditContinentView extends IView {

    String ACTION_ADD = "edit-continent-view-action-add";
    String ACTION_SAVE = "edit-continent-view-action-save";

    String getControlValue();
    ContinentsModel getContinentsModel();
}
