package com.example.demo.bishnu.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "session_Entity")
public class SessionEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int sessionId;
  
  private String sessionKey;
  
  private Date sessionCreatTime;
  
  private int userId;

}
