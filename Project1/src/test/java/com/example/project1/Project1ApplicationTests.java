package com.example.project1;

import com.example.project1.model.*;
import com.example.project1.model.dto.Product.ProductDTO;
import com.example.project1.model.dto.Sale.SaleDTO;
import com.example.project1.model.dto.Sale.SaleProductDAO;
import com.example.project1.model.repos.ProductRepository;
import com.example.project1.services.ProductService;
import com.example.project1.services.SaleService;
import com.example.project1.services.handlers.PromotionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class Project1ApplicationTests {
	@Mock
	private ProductRepository productRepository;
	@Mock
	private SaleService saleService;
	@InjectMocks
	private PromotionHandler promotionHandler;

	@InjectMocks
	private ProductService productService;

	@Test
	void loadProduct() {
		Product product=new Product(1,"Mock","wawa",0.0,2);
		when(productRepository.findById(2)).thenReturn(Optional.of(product));
		ProductDTO productDTO=new ProductDTO();
		productDTO.setId(2);
		Product result=(productService.getProduct(productDTO));
		verify(productRepository).findById(2);
		assertEquals(product,result);

	}

	@Test
	void promotionWithout(){
		Product product=new Product(1,"Mock","wawa",4.0,2);
		Product product1=new Product(1,"Mock","wawa",4.0,1);
		Costumer costumer=new Costumer(1,"MockCostumer","mock@mock.com","+010000000101", Set.of());
		Sale sale=new Sale(1, List.of(product),4.0,null);
		Invoice invoice =new Invoice(1,costumer,sale);
		SaleDTO saleDTO=new SaleDTO(List.of(new SaleProductDAO(1,1)),1);
		Sale sale1=new Sale(1, List.of(product1),4.0,null);
		Invoice invoice1 =new Invoice(1,costumer,sale1);
		when(saleService.makeSale(saleDTO)).thenReturn(invoice1);
		Invoice result=promotionHandler.handle(saleDTO);
		assertEquals(invoice1,result);
	}

	@Test
	void promotionWith(){
		Product product=new Product(1,"Mock","wawa",4.0,2);
		Product product1=new Product(1,"Mock","wawa",4.0,1);
		Costumer costumer=new Costumer(1,"MockCostumer","mock@mock.com","+010000000101", Set.of());
		Sale sale=new Sale(1, List.of(product),4.0,null);
		Invoice invoice =new Invoice(1,costumer,sale);
		SaleDTO saleDTO=new SaleDTO(List.of(new SaleProductDAO(1,1)),1);
		Sale sale1=new Sale(1, List.of(product1),4.0,null);
		Invoice invoice1 =new Invoice(1,costumer,sale1);
		when(saleService.makeSale(saleDTO)).thenReturn(invoice1);
		Invoice result=promotionHandler.handle(saleDTO);
		result=promotionHandler.handle(saleDTO);
		result=promotionHandler.handle(saleDTO);
		result=promotionHandler.handle(saleDTO);
		Sale sale2=new Sale(1, List.of(product1),2.0,null);
		Invoice invoice2 =new Invoice(1,costumer,sale2);
		PromotionInvoice promotionInvoice=new PromotionInvoice(invoice2);
		assertEquals(promotionInvoice,result);
	}

}
