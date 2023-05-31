package com.example.project1.controlers;

import com.example.project1.model.Invoice;
import com.example.project1.model.dto.Cashier.CashierSignInDTO;
import com.example.project1.model.dto.Sale.SaleDTO;
import com.example.project1.services.SaleService;
import com.example.project1.services.UserService;
import com.example.project1.services.handlers.PromotionHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@RestController
@RequestMapping("/cashier")
public class CashierController {
    @Autowired
    private UserService userService;
    @Autowired
    private PromotionHandler promotionHandler;

    @PostMapping("/sign-up")
    public @ResponseBody Map<String, String> signUp(@RequestBody CashierSignInDTO studentSignInDTO) {
        return buildJWTResponse(userService.signInStudent(studentSignInDTO));
    }

    @PostMapping("/sale")
    public @ResponseBody <O extends Invoice> O makeSale(@RequestBody SaleDTO saleDTO){
        return promotionHandler.processRequest(saleDTO);
    }
    private Map<String, String> buildJWTResponse(String token) {
        HashMap<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
