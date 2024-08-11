package com.nagra.curd.collection.service;

import com.nagra.curd.collection.model.Product;
import com.nagra.curd.collection.model.ProductResult;
import com.nagra.curd.collection.repository.ProductRepository;
import com.nagra.curd.collection.util.ProductConstants;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  @Autowired private ProductRepository productRepository;

  public String saveProduct(Product product) {
    ProductResult create = productRepository.save(product);
    if (create.isCreate())
      return String.format(ProductConstants.PRODUCT_CREATE_MESSAGE, product.getProductType());
    else return create.getStatus();
  }

  public List<Product> getAllProducts() {
    return productRepository.getProducts();
  }

  public Product findByProductId(String productId) {
    return productRepository.findById(productId);
  }

  public Product findByProductType(String productType) {
    return productRepository.findByType(productType);
  }
}
