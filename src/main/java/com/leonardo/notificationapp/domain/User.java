package com.leonardo.notificationapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String phoneNumber;
    private Double financialIncome;

    /**
     * Returns the user's full name
     *
     * @return full name (first and last name)
     */
    public String getFullName() {
        return name + " " + lastName;
    }
}
