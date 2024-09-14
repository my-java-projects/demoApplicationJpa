package org.example.demoapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable{
    private Long id;
    private String nationalId;
    private String customerNumber;
    private String firstName;
    private String lastName;
    private String fathersName;
    private String cityOfBirth;
    private String phoneNumber;
    private List<DepositDTO> deposits;

    public CustomerDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
