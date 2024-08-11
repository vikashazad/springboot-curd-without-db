package com.nagra.curd.collection.controller;

import com.nagra.curd.collection.model.Product;
import com.nagra.curd.collection.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  @Autowired private ProductService productService;

  @PostMapping("/create")
  public String createProduct(@RequestBody Product product) {
    return productService.saveProduct(product);
  }

  @GetMapping("/all")
  public List<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @GetMapping("/id/{productId}")
  public Product getProductById(@PathVariable String productId) {
    return productService.findByProductId(productId);
  }

  @GetMapping("/type/{productType}")
  public Product getProductByType(@PathVariable String productType) {
    return productService.findByProductType(productType);
  }
}
