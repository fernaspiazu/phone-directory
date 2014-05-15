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
package com.xpeppers.phonedirectory.services;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mysema.query.types.expr.BooleanExpression;
import com.xpeppers.phonedirectory.domain.PhoneDirectory;
import com.xpeppers.phonedirectory.domain.QPhoneDirectory;
import com.xpeppers.phonedirectory.repositories.PhoneDirectoryRepository;
import com.xpeppers.phonedirectory.services.pagination.QueryParameters;
import com.xpeppers.phonedirectory.utils.PageableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@Transactional
public class PhoneDirectoryServiceImpl implements PhoneDirectoryService {

  @Autowired
  private Gson gson;

  @Autowired
  private PhoneDirectoryRepository phoneRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public PhoneDirectory saveEntry(PhoneDirectory entry) {
    return phoneRepository.save(entry);
  }

  @Override
  public Optional<PhoneDirectory> findEntryById(Long id) {
    return Optional.fromNullable(phoneRepository.findOne(id));
  }

  @Override
  public String searchTelephones(QueryParameters queryParameters) {
    Pageable pageable = PageableFactory.makePageable(queryParameters);
    Page queryResult = getQueryResult(pageable, queryParameters.getSearchCriteria());
    return gson.toJson(datatableResponseAttributes(queryParameters, queryResult));
  }

  private Page getQueryResult(Pageable pageable, String searchCriteria) {
    if (StringUtils.hasText(searchCriteria)) {
      return phoneRepository.findAll(whereFirstNameOrPhoneNumberLikeCriteriaEntry(searchCriteria), pageable);
    }
    return phoneRepository.findAll(pageable);
  }

  private BooleanExpression whereFirstNameOrPhoneNumberLikeCriteriaEntry(String searchCriteria) {
    QPhoneDirectory directory = QPhoneDirectory.phoneDirectory;
    return directory.firstName.toLowerCase().like(anywhere(searchCriteria))
      .or(directory.phoneNumber.toLowerCase().like(anywhere(searchCriteria)));
  }

  private static String anywhere(String value) {
    return "%" + value.toLowerCase() + "%";
  }

  private Map<String, Object> datatableResponseAttributes(QueryParameters parameters, Page page) {
    final boolean hasContent = page.hasContent();
    Map<String, Object> datatableAttributes = Maps.newHashMap();
    datatableAttributes.put("sEcho", parameters.getEcho());
    datatableAttributes.put("iTotalRecords", (hasContent) ? page.getTotalElements() : 0L);
    datatableAttributes.put("iTotalDisplayRecords", (hasContent) ? page.getTotalElements() : 0L);
    datatableAttributes.put("aaData", page.getContent());
    return datatableAttributes;
  }

}
