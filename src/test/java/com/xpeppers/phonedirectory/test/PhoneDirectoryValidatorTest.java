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

import com.xpeppers.phonedirectory.repositories.PhoneDirectory;
import com.xpeppers.phonedirectory.utils.ValidationResponse;
import com.xpeppers.phonedirectory.controller.PhoneDirectoryValidator;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PhoneDirectoryValidatorTest {

  private PhoneDirectoryValidator validator;

  @Before
  public void setUp() {
    validator = new PhoneDirectoryValidator();
  }

  @Test
  public void shouldNotBeWhitespaces() {
    PhoneDirectory phoneDirectory = new PhoneDirectory();
    ValidationResponse result = validator.validate(phoneDirectory);
    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  public void telephoneNumberFormatMustBeCorrect() {
    final PhoneDirectory entry = entryWithPhoneNumber("+39 02 123456");
    ValidationResponse result = validator.validate(entry);
    assertThat(result.hasErrors()).isFalse();
  }

  @Test
  public void telephoneNumberFormatMustNotBeCorrectWithoutWhitespaces() {
    final PhoneDirectory entry = entryWithPhoneNumber("+39021234567");
    ValidationResponse result = validator.validate(entry);
    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  public void telephoneNumberFormatMustNotBeCorrectWithOneWhitespace() {
    final PhoneDirectory entry = entryWithPhoneNumber("+39 021234567");
    ValidationResponse result = validator.validate(entry);
    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  public void telephoneNumberFormatMustNotBeCorrectWithMoreThanOneWhitespace() {
    final PhoneDirectory entry = entryWithPhoneNumber("+39  02 1234567");
    ValidationResponse result = validator.validate(entry);
    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  public void telephoneNumberFormatMustNotBeCorrectWithLetters() {
    final PhoneDirectory entry = entryWithPhoneNumber("+39 A2 1234567");
    ValidationResponse result = validator.validate(entry);
    assertThat(result.hasErrors()).isTrue();
  }

  @Test
  public void telephoneNumberFormatMustNotBeCorrectWithLessThanSevenDigitsAtTheEnd() {
    final PhoneDirectory entry = entryWithPhoneNumber("+39 02 12345");
    ValidationResponse result = validator.validate(entry);
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
