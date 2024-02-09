package com.example.demo.bishnu.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.example.demo.bishnu.service.ImageCloudinaryService;

@Service
public class ImageCloudinaryServiceImpl implements ImageCloudinaryService  {
  
  @Autowired
  private Cloudinary cloudinary;


  @SuppressWarnings({ "unchecked" })
  @Override
  public Map<String, Object> upload(MultipartFile file, String folderName) {
    try {
      Map<String, Object> data =  this.cloudinary.uploader().upload(file.getBytes(), Map.of("folder", folderName));

      return data;
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Image upload fail.....");
    }
  }


  @Override
  public void destroy(String publicId) {
    try {
      this.cloudinary.uploader().destroy(publicId, Map.of());
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Image delete fail.....");
    }
    
  }


  @SuppressWarnings("unchecked")
  @Override
  public Map<String, Object> updateFile(MultipartFile file, String folderName, String publicId) {
    try {
      this.cloudinary.uploader().destroy(publicId, Map.of());
      Map<String, Object> data = this.cloudinary.uploader().upload(file.getBytes(), Map.of("folder", folderName));
      return data;
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("Image upload fail.....");
    }
  }

}
