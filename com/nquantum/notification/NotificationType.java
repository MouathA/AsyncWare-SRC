package com.nquantum.notification;

public enum NotificationType
{
    private static final NotificationType[] $VALUES;
    
    INFO("INFO", 1), 
    ERROR("ERROR", 3), 
    SUCCESS("SUCCESS", 0), 
    WARNING("WARNING", 2);
    
    private NotificationType(final String s, final int n) {
    }
    
    static {
        $VALUES = new NotificationType[] { NotificationType.SUCCESS, NotificationType.INFO, NotificationType.WARNING, NotificationType.ERROR };
    }
}
