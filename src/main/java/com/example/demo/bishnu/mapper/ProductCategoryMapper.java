package com.example.demo.bishnu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.bishnu.dto.ProductCategory;

@Mapper
public interface ProductCategoryMapper {
  
  // get all productCategory
  List<ProductCategory> selectAllProductCategory();
  
  // get productCategoryName by categoryId
  String selectProductCategoryById(String categoryId);
  
  // get productCaegoryId by categoryName
  List<String> selectProductCategoryByCategoryName(String categoryName);
}
