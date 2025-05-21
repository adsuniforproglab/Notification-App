package com.leonardo.notificationapp.listener;

import com.leonardo.notificationapp.constants.Messages;
import com.leonardo.notificationapp.domain.Proposal;
import com.leonardo.notificationapp.domain.User;
import com.leonardo.notificationapp.exception.NotificationException;
import com.leonardo.notificationapp.service.SnsNotificationService;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class PendingProposalListener {

    private final SnsNotificationService snsNotificationService;

    @RabbitListener(queues = "${rabbitmq.queue.pending.proposal}")
    public void pendingProposal(Proposal proposal) {
        try {
            log.info(Messages.LOG_PROCESSING_PENDING_PROPOSAL, proposal.getId());

            User user = proposal.getUser();
            if (user == null) {
                log.error("User information missing in proposal: {}", proposal.getId());
                throw new NotificationException("User information missing in proposal");
            }

            String message = String.format(Messages.PENDING_PROPOSAL, user.getName());
            log.debug("Preparing notification with message: {}", message);

            // Use user's phone number instead of hardcoded value
            String phoneNumber = user.getTellPhone();
            snsNotificationService.sendNotification(message, phoneNumber);

            log.info("Pending proposal notification sent for proposal ID: {}", proposal.getId());
        } catch (Exception e) {
            log.error("Error processing pending proposal: {}", e.getMessage(), e);
            // Don't rethrow - we don't want to lose the message or block the queue
        }
    }
}
