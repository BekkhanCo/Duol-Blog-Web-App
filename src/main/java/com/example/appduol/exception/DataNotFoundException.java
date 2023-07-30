package com.example.appduol.exception;

public class DataNotFoundException extends RuntimeException{

  public DataNotFoundException(String message) {
    super(message);
  }

  public DataNotFoundException() {
  }
}
