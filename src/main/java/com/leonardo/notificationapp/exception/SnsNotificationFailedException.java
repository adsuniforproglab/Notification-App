package com.leonardo.notificationapp.exception;

/**
 * Exception thrown when SNS notification delivery fails
 */
public class SnsNotificationFailedException extends NotificationException {

    public SnsNotificationFailedException(String message) {
        super(message);
    }

    public SnsNotificationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
