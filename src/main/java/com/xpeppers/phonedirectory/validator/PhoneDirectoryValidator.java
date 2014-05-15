/*
 * =============================================================================
 *
 *   Copyright (c) 2014, fumandito@gmail.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package com.xpeppers.phonedirectory.validator;

import com.googlecode.flyway.core.util.StringUtils;
import com.xpeppers.phonedirectory.domain.PhoneDirectory;
import com.xpeppers.phonedirectory.utils.ErrorMessage;
import com.xpeppers.phonedirectory.utils.ValidationResponse;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PhoneDirectoryValidator {
  private static final String REGEX = "^[+](\\d+)(\\s)(\\d+)(\\s)(\\d{7,})+$";

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
      Pattern pattern = Pattern.compile(REGEX);
      if (!pattern.matcher(phoneNumber).matches()) {
        validationResponse.setStatus(ValidationStatus.FAIL);
        validationResponse.getErrorMessages().add(new ErrorMessage("phoneNumber", "Incorrect format for Phone number. " +
          "Format must be: +39 02 1234567, but has been find: " + phoneNumber));
      }
    }
  }

}
