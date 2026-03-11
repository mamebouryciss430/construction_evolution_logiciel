

package com.risk.view.events;

import javax.swing.*;
import java.util.EventObject;


public class ViewListSelectionEvent {
    private final String source;
    private final ListSelectionModel listSelectionModel;
    private final int firstIndex;
    private final int lastIndex;
    private final boolean isAdjusting;


    public ViewListSelectionEvent(
            String source,
            ListSelectionModel listSelectionModel,
            int firstIndex,
            int lastIndex,
            boolean isAdjusting)
    {
        this.source = source;
        this.listSelectionModel = listSelectionModel;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        this.isAdjusting = isAdjusting;
    }

    public String getSource() {
        return source;
    }

    public ListSelectionModel getListSelectionModel() {
        return listSelectionModel;
    }

    public int getFirstIndex() { return firstIndex; }

    public int getLastIndex() { return lastIndex; }

    public boolean getValueIsAdjusting() { return isAdjusting; }
}
