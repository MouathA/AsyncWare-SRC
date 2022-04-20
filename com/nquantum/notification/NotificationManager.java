package com.nquantum.notification;

import java.util.concurrent.*;

public class NotificationManager
{
    private static Notification currentNotification;
    private static LinkedBlockingQueue pendingNotifications;
    
    static {
        NotificationManager.pendingNotifications = new LinkedBlockingQueue();
        NotificationManager.currentNotification = null;
    }
    
    public static void update() {
        if (NotificationManager.currentNotification != null && !NotificationManager.currentNotification.isShown()) {
            NotificationManager.currentNotification = null;
        }
        if (NotificationManager.currentNotification == null && !NotificationManager.pendingNotifications.isEmpty()) {
            (NotificationManager.currentNotification = NotificationManager.pendingNotifications.poll()).show();
        }
    }
    
    public static void render() {
        if (NotificationManager.currentNotification != null) {
            NotificationManager.currentNotification.render();
        }
    }
    
    public static void show(final Notification notification) {
        NotificationManager.pendingNotifications.add(notification);
    }
}
