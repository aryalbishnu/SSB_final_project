package com.example.demo.bishnu.convertor;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Convertor {
  public static Date stringToDate(String value) throws ParseException {

      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     // Date date = sdf.parse(value);
        return sdf.parse(value);
    } 
  
  // Number formated 
  public static String numberFormat(Integer value){
    // Format the number using NumberFormat
    NumberFormat numberFormated = NumberFormat.getInstance(Locale.US);
    String formatedNumber = numberFormated.format(value);
    return formatedNumber;
  }
}


