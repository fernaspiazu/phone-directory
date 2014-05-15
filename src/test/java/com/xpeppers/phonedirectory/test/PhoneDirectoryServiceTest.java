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

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xpeppers.phonedirectory.domain.PhoneDirectory;
import com.xpeppers.phonedirectory.repositories.PhoneDirectoryRepository;
import com.xpeppers.phonedirectory.services.PhoneDirectoryService;
import com.xpeppers.phonedirectory.services.PhoneDirectoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PhoneDirectoryServiceTest {

  @Mock
  private PhoneDirectoryRepository phoneRepository;

  @InjectMocks
  private PhoneDirectoryService phoneDirectoryService = new PhoneDirectoryServiceImpl();

  @Before
  public void init() {
    ReflectionTestUtils.setField(phoneDirectoryService, "gson", gson());
  }

  @Test
  public void verifyIfResultIsPresent() {
    Mockito.when(phoneRepository.findOne(25L)).thenReturn(entry());
    Optional<PhoneDirectory> phoneDirectory = phoneDirectoryService.findEntryById(25L);
    assertThat(phoneDirectory.isPresent()).isTrue();
    assertThat(phoneDirectory.get()).isEqualTo(entry());
  }

  @Test
  public void verifyIfResultIsAbsent() {
    Mockito.when(phoneRepository.findOne(25L)).thenReturn(null);
    Optional<PhoneDirectory> phoneDirectory = phoneDirectoryService.findEntryById(25L);
    assertThat(phoneDirectory.isPresent()).isFalse();
  }

  private static PhoneDirectory entry() {
    PhoneDirectory phoneDirectory = new PhoneDirectory();
    phoneDirectory.setId(1000L);
    phoneDirectory.setFirstName("Kent");
    phoneDirectory.setLastName("Beck");
    phoneDirectory.setPhoneNumber("+39 02 1234567");
    return phoneDirectory;
  }

  private static Gson gson() {
    return new GsonBuilder()
      .serializeNulls()
      .setPrettyPrinting()
      .serializeSpecialFloatingPointValues()
      .create();
  }

}
