package com.example.project1.controlers;

import com.example.project1.model.Product;
import com.example.project1.model.Report;
import com.example.project1.model.dto.Product.ProductDTO;
import com.example.project1.services.ProductService;
import com.example.project1.services.handlers.PromotionHandler;
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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private PromotionHandler promotionHandler;
    @GetMapping("/product")
    public @ResponseBody Product getProducts(@RequestBody ProductDTO productDTO) {
        return productService.getProduct(productDTO);
    }

    @GetMapping("/products")
    public @ResponseBody List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping("/product")
    public @ResponseBody Product createProduct(@RequestBody ProductDTO productCreateDTO) {
        return productService.createProduct(productCreateDTO);
    }

    @PutMapping("/product")
    public @ResponseBody Product updateProduct(@RequestBody ProductDTO productUpdateDTO) {
        return productService.updateProduct(productUpdateDTO);
    }



    @DeleteMapping("/product")
    public @ResponseBody ResponseEntity<?> deleteProduct(@RequestBody ProductDTO productDeleteDTO){
        productService.deleteProduct(productDeleteDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/promotion")
    public @ResponseBody ResponseEntity<?> togglePromotion(){
        promotionHandler.togglePromotion();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/report")
    public @ResponseBody ResponseEntity<?> reports(){
        productService.getReports();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
