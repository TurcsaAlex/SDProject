package com.example.project1.model.dto.Costumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CostumerDTO {
    private Integer id;
    private String name;
    private String email;
    private String phoneNumber;
}
