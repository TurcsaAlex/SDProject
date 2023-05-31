package com.example.project1.services;

import com.example.project1.model.Product;
import com.example.project1.model.Report;
import com.example.project1.model.dto.Product.ProductDTO;
import com.example.project1.model.repos.ProductRepository;
import com.example.project1.model.repos.ReportRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private XMLReportWriter xmlReportWriter;

    public Product createProduct(ProductDTO productDTO){
         String name=productDTO.getName();
         String description=productDTO.getDescription();
         Double price=productDTO.getPrice();
         Integer stock=productDTO.getStock();
         Product product=new Product(1,name,description,price,stock);
         product=productRepository.save(product);
         Report report=new Report(1,product,0);
         report=reportRepository.save(report);
         return product;
    }

    public Product updateProduct(ProductDTO productDTO){
        Integer id=productDTO.getId();
        Product product=productRepository.findById(id).orElseThrow();
        String name=productDTO.getName();
        String description=productDTO.getDescription();
        Double price=productDTO.getPrice();
        Integer stock=productDTO.getStock();

        if(description!=null)
            product.setDescription(description);

        if(name!=null)
            product.setName(name);

        if(price!=null)
            product.setPrice(price);

        if (stock!=null)
            product.setStock(stock);

        return productRepository.save(product);
    }

    public void deleteProduct(ProductDTO productDTO){
        Integer id= productDTO.getId();
        Product product=productRepository.findById(id).orElseThrow();
        productRepository.delete(product);
    }


    public void getReports(){
        List<Report> reports=reportRepository.findAllByOrderBySold();
        Collections.reverse(reports);
        xmlReportWriter.writeXml(reports);
    }

    public Product getProduct(ProductDTO productDTO){
        Integer id=productDTO.getId();
        String name=productDTO.getName();
        Product product;
        if(id!=null)
             product=productRepository.findById(id).orElseThrow();
        else
            product=productRepository.findByName(name).orElseThrow();
        return product;
    }
    public List<Product> getProducts(){
        return productRepository.findAll();
    }
}
