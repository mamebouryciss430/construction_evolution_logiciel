package com.risk.view;

import java.util.Collection;

public interface ICreateContinentView extends IView {

    String ACTION_ADD = "create-continent-view-action-add";
    String ACTION_NEXT = "create-continent-view-action-next";


    String getControlValue();

    String getContinentValue();
}
