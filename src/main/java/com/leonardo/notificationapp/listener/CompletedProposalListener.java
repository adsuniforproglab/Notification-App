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
public class CompletedProposalListener {

    private final SnsNotificationService snsNotificationService;

    @RabbitListener(queues = "${rabbitmq.queue.completed.proposal}")
    public void proposalDone(Proposal proposal) {
        try {
            log.info(Messages.LOG_PROCESSING_COMPLETED_PROPOSAL, proposal.getId());

            User user = proposal.getUser();
            if (user == null) {
                log.error("User information missing in completed proposal: {}", proposal.getId());
                throw new NotificationException("User information missing in completed proposal");
            }

            String message;
            if (proposal.getObservation() != null && !proposal.getObservation().isEmpty()) {
                message = proposal.getObservation();
            } else {
                // Use default message format if no observation provided
                message = String.format(Messages.PROPOSAL_DONE, user.getName());
            }

            log.debug("Preparing completed proposal notification with message: {}", message);

            // Use user's phone number instead of hardcoded value
            String phoneNumber = user.getTellPhone();
            snsNotificationService.sendNotification(message, phoneNumber);

            log.info("Completed proposal notification sent for proposal ID: {}", proposal.getId());
        } catch (Exception e) {
            log.error("Error processing completed proposal: {}", e.getMessage(), e);
            // Don't rethrow - we don't want to lose the message or block the queue
        }
    }
}
