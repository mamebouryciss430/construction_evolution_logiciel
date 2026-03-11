package com.risk.model;

import java.util.Observable;

public final class NotificationManager {

    private NotificationManager() {}

    /**
     * Envoie une notification et logge en console
     */
    public static void notify(Observable notifier, String message) {
        System.out.println(message);
        notifier.hasChanged();
        notifier.notifyObservers(message);
    }
}
