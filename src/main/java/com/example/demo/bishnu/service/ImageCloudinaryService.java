package com.example.demo.bishnu.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ImageCloudinaryService {
  
  // upload file
  public Map<String, Object> upload(MultipartFile file, String folderName);
  
  // delete file
  public void destroy(String publicId);
  
  //upload file
  public Map<String, Object> updateFile(MultipartFile file, String folderName, String publicId);

}
