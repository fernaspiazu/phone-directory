package com.xpeppers.phonedirectory.validator;

import com.googlecode.flyway.core.util.StringUtils;
import com.xpeppers.phonedirectory.domain.PhoneDirectory;
import com.xpeppers.phonedirectory.utils.ErrorMessage;
import com.xpeppers.phonedirectory.utils.ValidationResponse;
import org.springframework.stereotype.Component;

@Component
public class PhoneDirectoryValidator {

  public ValidationResponse validate(PhoneDirectory model) {
    ValidationResponse validationResponse = new ValidationResponse();
    validationResponse.setStatus(ValidationStatus.SUCCESS);

    rejectIfEmptyOrWhitespace("firstName", model.getFirstName(), validationResponse);
    rejectIfEmptyOrWhitespace("lastName", model.getLastName(), validationResponse);
    rejectIfEmptyOrWhitespace("phoneNumber", model.getPhoneNumber(), validationResponse);
    validatePhoneNumberFormat(model.getPhoneNumber(), validationResponse);

    return validationResponse;
  }

  private static void rejectIfEmptyOrWhitespace(String field, String value, ValidationResponse validationResponse) {
    if (!StringUtils.hasText(value)) {
      validationResponse.setStatus(ValidationStatus.FAIL);
      validationResponse.getErrorMessages().add(new ErrorMessage(field, "'" + field + "' must not be empty"));
    }
  }

  private static void validatePhoneNumberFormat(String phoneNumber, ValidationResponse validationResponse) {
    if (!validationResponse.hasErrors()) {
      System.out.println("Has to be implemented at this point...!!!");
    }
  }

}
