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

import com.xpeppers.phonedirectory.data.PhoneDirectory;
import com.xpeppers.phonedirectory.data.PhoneDirectoryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(Long id) {
		try {
			repository.delete(id);
			return true;
		} catch (EmptyResultDataAccessException | IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public long count() {
		return repository.count();
	}

	@Override
	public Page<PhoneDirectory> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	public Optional<PhoneDirectory> findEntryById(Long id) {
		return Optional.ofNullable(repository.findOne(id));
	}

}
