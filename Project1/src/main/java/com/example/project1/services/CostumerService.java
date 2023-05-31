package com.example.project1.services;

import com.example.project1.model.Costumer;
import com.example.project1.model.dto.Costumer.CostumerDTO;
import com.example.project1.model.repos.CostumerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CostumerService {
    @Autowired
    private CostumerRepository costumerRepository;

    public Costumer createCostumer(CostumerDTO costumerDTO){
        String name=costumerDTO.getName();
        String email=costumerDTO.getEmail();
        String phoneNumber=costumerDTO.getPhoneNumber();
        
        
        Costumer costumer=new Costumer(0,name,email,phoneNumber, Set.of());
        return  costumerRepository.save(costumer);
    }

    public Costumer updateCostumer(CostumerDTO costumerDTO){
        Integer id=costumerDTO.getId();
        Costumer costumer= costumerRepository.findById(id).orElseThrow();
        String name=costumerDTO.getName();
        String email=costumerDTO.getEmail();
        String phoneNumber=costumerDTO.getPhoneNumber();

        if(email!=null)
            costumer.setEmail(email);

        if(name!=null)
            costumer.setName(name);

        if(phoneNumber!=null)
            costumer.setPhoneNumber(phoneNumber);

        return costumerRepository.save(costumer);
    }

    public void deleteCostumer(CostumerDTO costumerDTO){
        Integer id= costumerDTO.getId();
        Costumer costumer= costumerRepository.findById(id).orElseThrow();
        costumerRepository.delete(costumer);
    }

    public Costumer getCostumer(CostumerDTO costumerDTO){
        Integer id=costumerDTO.getId();
        String name=costumerDTO.getName();
        Costumer costumer;
        if(id!=null)
            costumer= costumerRepository.findById(id).orElseThrow();
        else
            costumer= costumerRepository.findByName(name).orElseThrow();
        return costumer;
    }
}
