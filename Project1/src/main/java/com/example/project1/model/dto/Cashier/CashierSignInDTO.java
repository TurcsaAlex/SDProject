package com.example.project1.model.dto.Cashier;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CashierSignInDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
