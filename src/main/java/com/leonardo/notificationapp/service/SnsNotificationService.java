package com.leonardo.notificationapp.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.leonardo.notificationapp.constants.Messages;
import com.leonardo.notificationapp.exception.SnsNotificationFailedException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnsNotificationService {

    private final AmazonSNS amazonSNS;

    @Value("${notification.default-phone:5585989924491}")
    private String defaultPhone;

    /**
     * Send SMS notification to the specified phone number
     *
     * @param message The message content to send
     * @param phoneNumber The recipient's phone number
     * @return The message ID if successfully sent
     * @throws SnsNotificationFailedException if sending fails
     */
    public String sendNotification(String message, String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            if (defaultPhone != null && !defaultPhone.isEmpty()) {
                log.warn("Phone number not provided. Using default phone: {}", defaultPhone);
                phoneNumber = defaultPhone;
            } else {
                throw new SnsNotificationFailedException("Missing phone number for notification");
            }
        }

        try {
            log.debug("Sending notification to: {}", phoneNumber);
            PublishRequest publishRequest = new PublishRequest()
                    .withMessage(message)
                    .withPhoneNumber(phoneNumber);

            PublishResult result = amazonSNS.publish(publishRequest);
            log.info(Messages.LOG_NOTIFICATION_SENT, phoneNumber);
            log.debug("Message ID: {}", result.getMessageId());

            return result.getMessageId();
        } catch (Exception e) {
            log.error(String.format(Messages.ERROR_SENDING_NOTIFICATION, e.getMessage()), e);
            throw new SnsNotificationFailedException("Failed to send notification", e);
        }
    }
}
