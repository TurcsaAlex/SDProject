package com.example.project1.services;

import com.example.project1.model.*;
import com.example.project1.model.dto.Sale.SaleDTO;
import com.example.project1.model.dto.Sale.SaleProductDAO;
import com.example.project1.model.exceptions.service.MyError;
import com.example.project1.model.repos.*;
import com.example.project1.services.Observer.EmailObserver;
import com.example.project1.services.Observer.InvoiceEmitter;
import com.example.project1.services.Observer.LogObserver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {
    private final InvoiceRepository invoiceRepository;
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final CostumerRepository costumerRepository;
    private final ReportRepository reportRepository;
    private final InvoiceEmitter invoiceEmitter;
    private final LogObserver logObserver;
    private final EmailObserver emailObserver;

    public Invoice makeSale(SaleDTO saleDTO){
        List<SaleProductDAO> productIds=saleDTO.getSaleProducts();
        Sale sale=new Sale(0,null,0.0,null);
        List<Product> products=new ArrayList<>();
        double total=0.0;
        for (SaleProductDAO p:productIds) {
            Product product=productRepository.findById(p.getProductId()).orElseThrow();
            if(p.getQuantity()>product.getStock())
                throw new MyError("Not enough stock");
            products.add(product);
            total+=product.getPrice()*p.getQuantity();
            product.setStock(product.getStock()-p.getQuantity());
            productRepository.save(product);
            Report report=reportRepository.findByProduct(product).orElseThrow();
            Integer sold=report.getSold();
            report.setSold(sold+p.getQuantity());
            reportRepository.save(report);
        }

        sale.setProducts(products);
        sale.setTotal(total);

        sale=saleRepository.save(sale);
        Costumer costumer=costumerRepository.findById(saleDTO.getCostumerId()).orElseThrow();
        Invoice invoice = new Invoice(0,costumer,sale);
        Invoice savedInvoice= invoiceRepository.save(invoice);

        invoiceEmitter.notifyAll(invoice);

        return savedInvoice;
    }


    public SaleService(InvoiceRepository invoiceRepository, SaleRepository saleRepository, ProductRepository productRepository, CostumerRepository costumerRepository, ReportRepository reportRepository, LogObserver logObserver, EmailObserver emailObserver) {
        this.invoiceRepository = invoiceRepository;
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.costumerRepository = costumerRepository;
        this.reportRepository = reportRepository;
        this.logObserver = logObserver;
        this.emailObserver = emailObserver;
        this.invoiceEmitter=new InvoiceEmitter();
        invoiceEmitter.addObserver(this.logObserver);
        invoiceEmitter.addObserver(this.emailObserver);
    }
}
