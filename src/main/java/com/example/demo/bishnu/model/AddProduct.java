package com.example.demo.bishnu.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data

public class AddProduct {

  @NotNull
  @NotBlank
  private String productName;
  
  @NotNull
  private int productPrice;
  
  @NotNull
  private int productQuantity;
  
  private String productImage;
  
  @NotNull
  @NotBlank
  private String productBrandName;
  
  @NotNull
  @NotBlank
  private String productCategory;
  
  private String productCreateDate;
  
  

  
}
