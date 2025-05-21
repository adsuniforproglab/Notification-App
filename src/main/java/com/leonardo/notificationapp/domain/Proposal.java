package com.leonardo.notificationapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Proposal {
    private Long id;
    private Double proposalValue;
    private int paymentTerm;
    private Boolean approved;
    private boolean integrated;
    private String observation;
    private User user;

    /**
     * Determines if the proposal requires immediate notification
     * 
     * @return true if notification is high priority
     */
    public boolean isPriorityNotification() {
        return proposalValue != null && proposalValue > 10000.0;
    }
}
