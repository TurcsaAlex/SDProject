package com.example.project1.controlers;

import com.example.project1.model.Costumer;
import com.example.project1.model.dto.Costumer.CostumerDTO;
import com.example.project1.services.CostumerService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@AllArgsConstructor
public class CostumerController {
    @Autowired
    private CostumerService costumerService;
    @GetMapping("/costumer")
    public @ResponseBody Costumer getCostumers(@RequestBody CostumerDTO costumerDTO) {
        return costumerService.getCostumer(costumerDTO);
    }


    @PostMapping("/costumer")
    public @ResponseBody Costumer createCostumer(@RequestBody CostumerDTO costumerCreateDTO) {
        return costumerService.createCostumer(costumerCreateDTO);
    }

    @PutMapping("/costumer")
    public @ResponseBody Costumer updateCostumer(@RequestBody CostumerDTO costumerUpdateDTO) {
        return costumerService.updateCostumer(costumerUpdateDTO);
    }

    @DeleteMapping("/costumer")
    public @ResponseBody ResponseEntity<?> deleteCostumer(@RequestBody CostumerDTO costumerDeleteDTO){
        costumerService.deleteCostumer(costumerDeleteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
