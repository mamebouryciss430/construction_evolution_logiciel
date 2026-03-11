package com.risk.view.events;

public class ViewActionEvent {

    private final String source;

    public ViewActionEvent(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}
