package com.risk.view;

import java.awt.event.ActionListener;
import java.util.Observer;

/**
 * Rule to build a view
 *
 * @author Karanbir Pannu
 *
 */
public interface View extends Observer {

    /**
     * Sets Action Listener
     *
     * @param actionListener
     */
    void setActionListener(ActionListener actionListener);
}
