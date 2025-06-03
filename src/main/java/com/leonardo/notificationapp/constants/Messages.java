package com.leonardo.notificationapp.constants;

/**
 * Constants for notification messages
 */
public final class Messages {
    // User notification messages
    public static final String PENDING_PROPOSAL = "Prezado(a) %s, sua proposta está pendente de aprovação.";
    public static final String PROPOSAL_DONE =
            "Prezado(a) %s, sua proposta está concluida. O valor da proposta é: R$ %.2f.";

    // Error messages
    public static final String ERROR_SENDING_NOTIFICATION = "Error sending notification: %s";
    public static final String MISSING_PHONE_NUMBER = "Missing phone number for user ID: %d";

    // Log messages
    public static final String LOG_NOTIFICATION_SENT = "Notification sent successfully to: {}";
    public static final String LOG_PROCESSING_PENDING_PROPOSAL = "Processing pending proposal with ID: {}";
    public static final String LOG_PROCESSING_COMPLETED_PROPOSAL = "Processing completed proposal with ID: {}";

    // Private constructor to prevent instantiation
    private Messages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
