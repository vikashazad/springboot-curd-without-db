package com.nagra.curd.collection.repository;

import com.nagra.curd.collection.model.Product;
import com.nagra.curd.collection.model.ProductResult;
import com.nagra.curd.collection.util.CollectionUtils;
import com.nagra.curd.collection.util.ProductConstants;
import com.nagra.curd.collection.util.ProductPremiumType;
import com.nagra.curd.collection.util.ProductType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy");

  @Getter private final List<Product> products = new ArrayList<>();

  public Product findById(String productId) {
    if (!CollectionUtils.isEmpty(products)) {
      return products.stream()
          .filter(product -> productId.equals(product.getId()))
          .findFirst()
          .orElse(null);
    }
    return null;
  }

  public Product findByType(String productType) {
    if (!CollectionUtils.isEmpty(products)) {
      return products.stream()
          .filter(product -> productType.equals(product.getProductType()))
          .findFirst()
          .orElse(null);
    }
    return null;
  }

  public ProductResult save(Product product) {
    LocalDate currentDate = LocalDate.now();
    Product newProduct = new Product();
    ProductResult result = new ProductResult();

    newProduct.setId(product.getId());
    newProduct.setName(product.getName());
    newProduct.setProductType(product.getProductType());
    newProduct.setStartDate(currentDate);
    newProduct.setPrice(product.getPrice());

    if (product.getProductType() != null) {
      switch (product.getProductType()) {
        case "transactional":
          newProduct.setEndDate(currentDate.plusMonths(1));
          newProduct.setPremiumType(ProductPremiumType.monthly.name());
          newProduct.setStorageAllowed(Boolean.FALSE);
          result.setCreate(Boolean.TRUE);
          break;

        case "subscription":
          newProduct.setEndDate(currentDate.plusYears(1));
          newProduct.setPremiumType(ProductPremiumType.annual.name());
          newProduct.setStorageAllowed(Boolean.TRUE);
          result.setCreate(Boolean.TRUE);
          break;

        case "capability":
          newProduct.setEndDate(currentDate.plusMonths(3));
          newProduct.setPremiumType(ProductPremiumType.quarterly.name());
          newProduct.setStorageAllowed(Boolean.FALSE);
          result.setCreate(Boolean.TRUE);
          break;

        case "ppv":
          newProduct.setEndDate(currentDate.plusMonths(6));
          newProduct.setPremiumType(ProductPremiumType.half_yearly.name());
          newProduct.setStorageAllowed(Boolean.TRUE);
          result.setCreate(Boolean.TRUE);
          break;

        default:
          String productNotFoundMsg =
              String.format(
                  ProductConstants.PRODUCT_NOT_FOUND_MESSAGE,
                  product.getName(),
                  ProductType.transactional.name(),
                  ProductType.subscription.name(),
                  ProductType.ppv.name(),
                  ProductType.capability.name());
          result.setStatus(productNotFoundMsg);
          result.setCreate(Boolean.FALSE);
          break;
      }
    }

    if (result.isCreate()) {
      newProduct.setDescription(
          String.format(ProductConstants.PRODUCT_DESCRIPTION, newProduct.getPremiumType()));
      newProduct.setRemarks(
          String.format(
              ProductConstants.PRODUCT_REMARKS,
              newProduct.getPremiumType(),
              newProduct.getEndDate().format(formatter)));

      products.add(newProduct);
    }

    return result;
  }
}
