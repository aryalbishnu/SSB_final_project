package com.example.demo.bishnu.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.bishnu.EntityNotFoundException;
import com.example.demo.bishnu.entity.ProductEntity;
import com.example.demo.bishnu.model.AddProduct;
import com.example.demo.bishnu.model.UpdateProduct;
import com.example.demo.bishnu.repo.ProductRepo;
import com.example.demo.bishnu.service.ImageCloudinaryService;
import com.example.demo.bishnu.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
  
  @Autowired 
  private ModelMapper modelMapper;
  
  @Autowired
  private ProductRepo productRepo;
  
  @Autowired
  private ImageCloudinaryService imageCloudinaryService;
  
  //add product
  @SuppressWarnings("rawtypes")
  public void addProductMethod(AddProduct addProduct, MultipartFile file, Integer productUserId) throws IOException {
    /*
     file save to local enviroment
     
    File saveFile = new ClassPathResource("static/img/bishnu/product/").getFile();
    UUID uuid = UUID.randomUUID();
    StringBuffer sb = new StringBuffer();
    sb.append(uuid.toString());
    sb.append(file.getOriginalFilename()); 
    String fileName = sb.toString();
    Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+fileName);
    Files.copy(file.getInputStream(),path , StandardCopyOption.REPLACE_EXISTING);
    addProduct.setProductImage(fileName);
    */
    
    // image save to cloudinary cloud
    String folderName = "product";
    Map imagePath = this.imageCloudinaryService.upload(file, folderName);
    
    addProduct.setProductImage((String)imagePath.get("secure_url"));
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String dateTimeString = dateTime.format(formatter);
    addProduct.setProductCreateDate(dateTimeString);
    
    ProductEntity productEntity = new ProductEntity();
    modelMapper.map(addProduct, productEntity);
    productEntity.setProductUserId(productUserId);
    productEntity.setPublicId((String)imagePath.get("public_id"));
    productRepo.save(productEntity);
  }
  
  //delete product by id
  public void deleteByProduct(Integer id) throws IOException {
    ProductEntity productEntity = this.productRepo.findById(id).get();
    /*
    file delete to local enviroment 
    String imageName = productEntity.getProductImage();
    File deleteFile=   new ClassPathResource("static/img/bishnu/product").getFile();
    File file1=new File(deleteFile, imageName);
    file1.delete();
    */
    
    // file delete in cloudinary 
    this.imageCloudinaryService.destroy(productEntity.getPublicId());
    this.productRepo.deleteById(id);
  }

  @Override
  public ProductEntity findByProductId(int productid) {
    ProductEntity productEntity = this.productRepo.findById(productid).orElseThrow(() -> new EntityNotFoundException("product id is not vallid"));
    return productEntity;
  }

//update producst by id
  @Override
  public void updateProductByProductId(int productId, UpdateProduct updateProduct, ProductEntity productEntity) {
   ProductEntity product = this.productRepo.findById(productId).orElseThrow(() -> new EntityNotFoundException("product id is not vallid"));
    product.setProductPrice(updateProduct.getAmount());
    product.setProductQuantity(updateProduct.getQuantity());
    LocalDateTime dateTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String dateTimeString = dateTime.format(formatter);
    product.setProductUpdateDate(dateTimeString);
    this.productRepo.save(product);
  }

}
