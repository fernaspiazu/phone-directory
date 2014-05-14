package com.xpeppers.phonedirectory.utils;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
  private static final long serialVersionUID = -600637407302494115L;

  private String fieldName;
  private String errorMessage;

  public ErrorMessage(String fieldName, String errorMessage) {
    this.fieldName = fieldName;
    this.errorMessage = errorMessage;
  }

  public String getFieldName() {
    return fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @Override
  public String toString() {
    return "ErrorMessage{" +
      "fieldName='" + fieldName + '\'' +
      ", errorMessage='" + errorMessage + '\'' +
      '}';
  }
}
