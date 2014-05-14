package com.xpeppers.phonedirectory.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationResponse implements Serializable {
  private static final long serialVersionUID = -179694203412278724L;

  private String status;
  private List<ErrorMessage> errorMessages;

  public ValidationResponse() {
    this.errorMessages = new ArrayList<ErrorMessage>();
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<ErrorMessage> getErrorMessages() {
    return errorMessages;
  }

  public void setErrorMessages(List<ErrorMessage> errorMessages) {
    this.errorMessages = errorMessages;
  }

  public boolean hasErrors() {
    return errorMessages == null || !errorMessages.isEmpty();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("ValidationResponse");
    sb.append("{errorMessages=").append(errorMessages);
    sb.append(", status='").append(status).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
