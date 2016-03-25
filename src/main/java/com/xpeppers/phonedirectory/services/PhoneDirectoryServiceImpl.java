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

import com.mysema.query.types.expr.BooleanExpression;
import com.xpeppers.phonedirectory.repositories.PhoneDirectory;
import com.xpeppers.phonedirectory.repositories.PhoneDirectoryRepository;
import com.xpeppers.phonedirectory.repositories.QPhoneDirectory;
import com.xpeppers.phonedirectory.utils.PageableFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PhoneDirectoryServiceImpl implements PhoneDirectoryService {

	private final PhoneDirectoryRepository repository;

	public PhoneDirectoryServiceImpl(PhoneDirectoryRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PhoneDirectory saveEntry(PhoneDirectory entry) {
		return repository.save(entry);
	}

	@Override
	public Page<PhoneDirectory> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Optional<PhoneDirectory> findEntryById(Long id) {
		return Optional.ofNullable(repository.findOne(id));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(Long id) {
		repository.delete(id);
	}

	@Override
	public Map<String, Object> searchTelephones(QueryParameters queryParameters) {
		Pageable pageable = PageableFactory.makePageable(queryParameters);
		Page queryResult = getQueryResult(pageable, queryParameters.getSearchCriteria());
		return datatableResponseAttributes(queryParameters, queryResult);
	}

	private Page getQueryResult(Pageable pageable, String searchCriteria) {
		if (StringUtils.hasText(searchCriteria)) {
			final BooleanExpression predicate = whereFirstNameOrPhoneNumberLikeCriteriaEntry(searchCriteria);
			return repository.findAll(predicate, pageable);
		}
		return repository.findAll(pageable);
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
		Map<String, Object> datatableAttributes = new HashMap<>();
		datatableAttributes.put("sEcho", parameters.getEcho());
		datatableAttributes.put("iTotalRecords", (hasContent) ? page.getTotalElements() : 0L);
		datatableAttributes.put("iTotalDisplayRecords", (hasContent) ? page.getTotalElements() : 0L);
		datatableAttributes.put("aaData", page.getContent());
		return datatableAttributes;
	}

}
