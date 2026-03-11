package com.risk.view.impl.swing;

import com.risk.view.IView;
import com.risk.view.events.ViewActionEvent;
import com.risk.view.events.ViewActionListener;
import com.risk.view.events.ViewListSelectionEvent;
import com.risk.view.events.ViewListSelectionListener;

import javax.swing.*;
import java.util.LinkedHashSet;
import java.util.Set;

public abstract class AbstractSwingView extends JFrame implements IView {

    private Set<ViewActionListener> listeners = new LinkedHashSet<>();
    private Set<ViewListSelectionListener> selectionListeners = new LinkedHashSet<>();

    @Override
    public void addActionListener(final ViewActionListener actionListener) {
        listeners.add(actionListener);
    }

    void fireViewEvent(final String source) {
        for (final ViewActionListener listener : listeners) {
            listener.actionPerformed(new ViewActionEvent(source));
        }
    }

    @Override
    public void addListSelectionListener(ViewListSelectionListener selectionListener) {
        selectionListeners.add(selectionListener);
    }

    void fireViewSelectionEvent(String source,
                                ListSelectionModel listSelectionModel,
                                int firstIndex,
                                int lastIndex,
                                boolean isAdjusting) {
        for (final ViewListSelectionListener listener : selectionListeners) {
            listener.valueChanged(
                    new ViewListSelectionEvent(
                            source, listSelectionModel, firstIndex, lastIndex, isAdjusting));
        }
    }

    @Override
    public void showView() {
        setVisible(true);
    }

    @Override
    public void hideView() {
        dispose();
    }

    @Override
    public void showMessageDialog(final String message, final String title) {
        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void showOptionDialog(final String message, final String title) {
        JOptionPane.showOptionDialog(
                null,
                message,
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] {},
                null);
    }
}
