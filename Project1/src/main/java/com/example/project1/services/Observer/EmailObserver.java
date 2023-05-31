package com.example.project1.services.Observer;

import com.example.project1.model.Invoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailObserver implements Observer{
    @Autowired
    private EmailService emailService;
    @Override
    public void notify(Object o) {
        Invoice invoice=(Invoice) o;
        emailService.sendEmail(invoice.getCostumer().getEmail(), invoice.toString());
    }
}
