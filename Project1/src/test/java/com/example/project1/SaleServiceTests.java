package com.example.project1;

import com.example.project1.model.*;
import com.example.project1.model.dto.Product.ProductDTO;
import com.example.project1.model.dto.Sale.SaleDTO;
import com.example.project1.model.dto.Sale.SaleProductDAO;
import com.example.project1.model.repos.CostumerRepository;
import com.example.project1.model.repos.InvoiceRepository;
import com.example.project1.model.repos.ProductRepository;
import com.example.project1.model.repos.SaleRepository;
import com.example.project1.services.Observer.EmailObserver;
import com.example.project1.services.Observer.InvoiceEmitter;
import com.example.project1.services.Observer.LogObserver;
import com.example.project1.services.ProductService;
import com.example.project1.services.SaleService;
import com.example.project1.services.handlers.PromotionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SaleServiceTests {
    @Mock
    private InvoiceRepository invoiceRepository;
    @Mock
    private  SaleRepository saleRepository;
    @Mock private  ProductRepository productRepository;
    @Mock private  CostumerRepository costumerRepository;
    @Mock private  InvoiceEmitter invoiceEmitter;
    @Mock private  LogObserver logObserver;
    @Mock private  EmailObserver emailObserver;

    @InjectMocks
    private SaleService saleService;

    public SaleServiceTests() {
    }

    @Test
    void SaleServiceGood(){
        Product product=new Product(1,"Mock","wawa",4.0,2);
        Product product1=new Product(1,"Mock","wawa",4.0,1);
        Costumer costumer=new Costumer(1,"MockCostumer","mock@mock.com","+010000000101", Set.of());
        Sale sale=new Sale(1, List.of(product),4.0,null);
        Invoice invoice =new Invoice(1,costumer,sale);
        SaleDTO saleDTO=new SaleDTO(List.of(new SaleProductDAO(1,1)),1);
        Sale sale1=new Sale(1, List.of(product1),4.0,null);
        Invoice invoice1 =new Invoice(1,costumer,sale1);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(costumerRepository.findById(1)).thenReturn(Optional.of(costumer));
        when(saleRepository.save(ArgumentMatchers.any(Sale.class))).thenReturn(sale1);
        when(invoiceRepository.save(ArgumentMatchers.any(Invoice.class))).thenReturn(invoice1);
//        doNothing().when(invoiceEmitter).notifyAll(any());
        doNothing().when(logObserver).notify(any());
        //doNothing().when(emailObserver).notify(any());

        Invoice result=saleService.makeSale(saleDTO);
        verify(invoiceEmitter,times(1)).notifyAll(any());
        assertEquals(result,invoice1);
    }

}
