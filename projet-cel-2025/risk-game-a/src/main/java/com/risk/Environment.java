package com.risk;

import com.risk.view.IViewManager;
import com.risk.view.impl.swing.SwingViewManager;

public class Environment {

    private static Environment instance;

    public static Environment getInstance() {
        if (instance == null) {
            instance = new Environment(new SwingViewManager());
        }

        return instance;
    }

    private final IViewManager viewManager;

    private Environment(IViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public IViewManager getViewManager() {
        return viewManager;
    }
}
