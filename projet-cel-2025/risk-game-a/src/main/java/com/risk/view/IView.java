package com.risk.view;

import com.risk.view.events.ViewActionListener;
import com.risk.view.events.ViewListSelectionListener;

import java.util.Observer;

/**
 * Rule to build a view
 *
 * @author KaranbirPannu
 *
 */
public interface IView extends Observer {

    void showView();

    void hideView();

    void showMessageDialog(final String message, final String title);

    void showOptionDialog(final String message, final String title);

    /**
     * Sets Action Listener
     *
     * @param actionListener action listener
     */
    void addActionListener(final ViewActionListener actionListener);

    void addListSelectionListener(final ViewListSelectionListener selectionListener);
}
