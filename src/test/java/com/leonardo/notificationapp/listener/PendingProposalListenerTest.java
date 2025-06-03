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
class PendingProposalListenerTest {

    @Mock
    private SnsNotificationService snsNotificationService;

    @InjectMocks
    private PendingProposalListener pendingProposalListener;

    private Proposal proposal;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("John")
                .lastName("Doe")
                .phoneNumber("+5585989924491")
                .build();

        proposal = Proposal.builder()
                .id(1L)
                .proposalValue(5000.0)
                .user(user)
                .build();
    }

    @Test
    @DisplayName("Should send notification for pending proposal")
    void shouldSendNotification() {
        // Act
        pendingProposalListener.pendingProposal(proposal);

        // Assert
        String expectedMessage = String.format(Messages.PENDING_PROPOSAL, user.getName());
        verify(snsNotificationService).sendNotification(expectedMessage, user.getPhoneNumber());
    }

    @Test
    @DisplayName("Should handle missing user gracefully")
    void shouldHandleMissingUserGracefully() {
        // Arrange
        proposal.setUser(null);

        // Act
        pendingProposalListener.pendingProposal(proposal);

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
        pendingProposalListener.pendingProposal(proposal);

        // Assert - Verify the method was called but the exception was handled
        verify(snsNotificationService).sendNotification(any(), any());
    }
}
