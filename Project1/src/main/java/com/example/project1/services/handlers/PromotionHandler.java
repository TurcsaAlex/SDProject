package com.example.project1.services.handlers;

import com.example.project1.model.Invoice;
import com.example.project1.model.PromotionInvoice;
import com.example.project1.model.dto.Sale.SaleDTO;
import com.example.project1.services.SaleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class PromotionHandler implements Handler<Invoice> {
    private static Boolean active=true;
    private static Integer costumerNr=0;
    @Autowired
    private SaleService saleService;
    private Handler next;
    public Boolean togglePromotion(){
        active= !active;
        return active;
    }

    public <O extends Invoice> O processRequest(SaleDTO req){
        if(next==null)
        {
            return (O) proc(req);
        }else {
            Invoice invoice=proc(req);
            return (O) next.handle(invoice);
        }
    }
    private Invoice proc(SaleDTO saleDTO){
        System.out.println(costumerNr);
        if (active) {
            if (costumerNr < 3) {
                costumerNr++;
                 return saleService.makeSale(saleDTO);
            }
            else {
                System.out.println("\n\n\n\n\n\n\n\nPROMOTIE\n\n\n\n\n\n\n");
                costumerNr=0;
                Invoice invoice=saleService.makeSale(saleDTO);
                Double total=invoice.getSale().getTotal();
                invoice.getSale().setTotal(total/2);
                return new PromotionInvoice(invoice);
            }

        }
        return saleService.makeSale(saleDTO);
    }


    @Override
    public void setNext(Handler handler) {
        this.next =handler;
    }

    @Override
    public Invoice handle(Object req) {
        return processRequest((SaleDTO) req);
    }
}
