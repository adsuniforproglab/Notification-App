package com.leonardo.notificationapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.leonardo.notificationapp.exception.SnsNotificationFailedException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class SnsNotificationServiceTest {

    @Mock
    private AmazonSNS amazonSNS;

    @InjectMocks
    private SnsNotificationService snsNotificationService;

    private static final String TEST_PHONE_NUMBER = "+5585989924491";
    private static final String TEST_MESSAGE = "Test message";
    private static final String TEST_MESSAGE_ID = "test-message-id";
    private static final String DEFAULT_PHONE = "+5585999999999";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(snsNotificationService, "defaultPhone", DEFAULT_PHONE);
    }

    @Test
    @DisplayName("Should send notification successfully")
    void shouldSendNotificationSuccessfully() {
        // Arrange
        PublishResult publishResult = new PublishResult().withMessageId(TEST_MESSAGE_ID);
        when(amazonSNS.publish(any(PublishRequest.class))).thenReturn(publishResult);

        // Act
        String result = snsNotificationService.sendNotification(TEST_MESSAGE, TEST_PHONE_NUMBER);

        // Assert
        assertEquals(TEST_MESSAGE_ID, result);
        verify(amazonSNS).publish(any(PublishRequest.class));
    }

    @Test
    @DisplayName("Should use default phone number when no phone provided")
    void shouldUseDefaultPhoneNumberWhenNoPhoneProvided() {
        // Arrange
        PublishResult publishResult = new PublishResult().withMessageId(TEST_MESSAGE_ID);
        when(amazonSNS.publish(any(PublishRequest.class))).thenReturn(publishResult);

        // Act
        String result = snsNotificationService.sendNotification(TEST_MESSAGE, null);

        // Assert
        assertEquals(TEST_MESSAGE_ID, result);
        verify(amazonSNS).publish(any(PublishRequest.class));
    }

    @Test
    @DisplayName("Should throw exception when no phone provided and no default phone configured")
    void shouldThrowExceptionWhenNoPhoneProvidedAndNoDefaultPhone() {
        // Arrange
        ReflectionTestUtils.setField(snsNotificationService, "defaultPhone", "");

        // Act & Assert
        assertThrows(SnsNotificationFailedException.class,
                () -> snsNotificationService.sendNotification(TEST_MESSAGE, null));
    }

    @Test
    @DisplayName("Should throw exception when AWS SNS throws exception")
    void shouldThrowExceptionWhenAwsSnsThrowsException() {
        // Arrange
        when(amazonSNS.publish(any(PublishRequest.class))).thenThrow(new RuntimeException("AWS Error"));

        // Act & Assert
        assertThrows(SnsNotificationFailedException.class,
                () -> snsNotificationService.sendNotification(TEST_MESSAGE, TEST_PHONE_NUMBER));
    }
}
