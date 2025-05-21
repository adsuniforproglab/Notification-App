package com.leonardo.notificationapp.listener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.leonardo.notificationapp.constants.Messages;
import com.leonardo.notificationapp.domain.Proposal;
import com.leonardo.notificationapp.domain.User;
import com.leonardo.notificationapp.exception.SnsNotificationFailedException;
import com.leonardo.notificationapp.service.SnsNotificationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CompletedProposalListenerTest {

    @Mock
    private SnsNotificationService snsNotificationService;

    @InjectMocks
    private CompletedProposalListener completedProposalListener;

    private Proposal proposal;
    private User user;
    private static final String OBSERVATION = "Proposal was approved successfully";

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("John")
                .lastName("Doe")
                .tellPhone("+5585989924491")
                .build();

        proposal = Proposal.builder()
                .id(1L)
                .proposalValue(5000.0)
                .approved(true)
                .integrated(true)
                .observation(OBSERVATION)
                .user(user)
                .build();
    }

    @Test
    @DisplayName("Should send notification for completed proposal with observation")
    void shouldSendNotificationWithObservation() {
        // Act
        completedProposalListener.proposalDone(proposal);

        // Assert
        verify(snsNotificationService).sendNotification(OBSERVATION, user.getTellPhone());
    }

    @Test
    @DisplayName("Should send default notification when no observation is provided")
    void shouldSendDefaultNotificationWhenNoObservation() {
        // Arrange
        proposal.setObservation(null);

        // Act
        completedProposalListener.proposalDone(proposal);

        // Assert
        String expectedMessage = String.format(Messages.PROPOSAL_DONE, user.getName());
        verify(snsNotificationService).sendNotification(expectedMessage, user.getTellPhone());
    }

    @Test
    @DisplayName("Should handle missing user gracefully")
    void shouldHandleMissingUserGracefully() {
        // Arrange
        proposal.setUser(null);

        // Act
        completedProposalListener.proposalDone(proposal);

        // Assert
        verifyNoInteractions(snsNotificationService);
    }

    @Test
    @DisplayName("Should handle notification service exceptions gracefully")
    void shouldHandleNotificationServiceExceptionsGracefully() {
        // Arrange
        doThrow(new SnsNotificationFailedException("Test exception"))
                .when(snsNotificationService).sendNotification(anyString(), anyString());

        // Act - Should not throw exception
        completedProposalListener.proposalDone(proposal);

        // Assert - Verify the method was called but the exception was handled
        verify(snsNotificationService).sendNotification(any(), any());
    }
}
