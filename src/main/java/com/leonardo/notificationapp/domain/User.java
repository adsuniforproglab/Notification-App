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
@ToString(exclude = {"cpf", "tellPhone"}) // Exclude sensitive fields from toString
public class User {
    private Long id;
    private String name;
    private String lastName;
    private String cpf;
    private String tellPhone;
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
