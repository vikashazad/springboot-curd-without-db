package com.nagra.curd.collection.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  private String id;
  private String name;
  private String description;
  private double price;
  private String productType;
  private String premiumType;
  private LocalDate startDate;
  private LocalDate endDate;
  private boolean storageAllowed;
  private String remarks;
}
