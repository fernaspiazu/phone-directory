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
package com.xpeppers.phonedirectory.test;

import com.xpeppers.phonedirectory.domain.PhoneDirectory;
import com.xpeppers.phonedirectory.utils.ValidationResponse;
import com.xpeppers.phonedirectory.validator.PhoneDirectoryValidator;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PhoneDirectoryValidatorTest {

  private PhoneDirectoryValidator phoneDirectoryValidator;

  @Before
  public void setUp() {
    phoneDirectoryValidator = new PhoneDirectoryValidator();
  }

  @Test
  public void shouldNotBeWhitespaces() {
    PhoneDirectory phoneDirectory = new PhoneDirectory();
    ValidationResponse result = phoneDirectoryValidator.validate(phoneDirectory);
    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  public void telephoneNumberFormatMustBeCorrect() {
    ValidationResponse result = phoneDirectoryValidator.validate(entryWithPhoneNumber("+39 02 1234567"));
    assertThat(result.hasErrors()).isFalse();
  }

  @Test
  public void telephoneNumberFormatMustNotBeCorrectWithoutWhitespaces() {
    ValidationResponse result = phoneDirectoryValidator.validate(entryWithPhoneNumber("+39021234567"));
    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  public void telephoneNumberFormatMustNotBeCorrectWithOneWhitespace() {
    ValidationResponse result = phoneDirectoryValidator.validate(entryWithPhoneNumber("+39 021234567"));
    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  public void telephoneNumberFormatMustNotBeCorrectWithMoreThanOneWhitespace() {
    ValidationResponse result = phoneDirectoryValidator.validate(entryWithPhoneNumber("+39  02 1234567"));
    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  public void telephoneNumberFormatMustNotBeCorrectWithLetters() {
    ValidationResponse result = phoneDirectoryValidator.validate(entryWithPhoneNumber("+39 A2 1234567"));
    assertThat(result.hasErrors()).isTrue();
  }

  private static PhoneDirectory entryWithPhoneNumber(String phoneNumber) {
    PhoneDirectory phoneDirectory = new PhoneDirectory();
    phoneDirectory.setId(1000L);
    phoneDirectory.setFirstName("Kent");
    phoneDirectory.setLastName("Beck");
    phoneDirectory.setPhoneNumber(phoneNumber);
    return phoneDirectory;
  }

}
