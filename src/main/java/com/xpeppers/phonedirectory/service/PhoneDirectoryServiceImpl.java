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
package com.xpeppers.phonedirectory.service;

import com.xpeppers.phonedirectory.model.PhoneDirectory;
import com.xpeppers.phonedirectory.repository.PhoneDirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneDirectoryServiceImpl implements PhoneDirectoryService {

    private final MongoTemplate mongoTemplate;

    private final PhoneDirectoryRepository repository;

    @Autowired
    public PhoneDirectoryServiceImpl(MongoTemplate mongoTemplate, PhoneDirectoryRepository repository) {
        this.mongoTemplate = mongoTemplate;
        this.repository = repository;
    }

    @Override
    public PhoneDirectory save(PhoneDirectory doc) {
        return repository.save(doc);
    }

    @Override
    public Optional<PhoneDirectory> delete(String id) {
        Query query = Query.query(Criteria.where("_id").is(id));
        return Optional.ofNullable(mongoTemplate.findAndRemove(query, PhoneDirectory.class));
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
    public Optional<PhoneDirectory> findById(String id) {
        return Optional.ofNullable(repository.findOne(id));
    }

    @Override
    public Page<PhoneDirectory> search(String term, Pageable pageable) {
        if (StringUtils.isEmpty(term)) {
            return this.findAll(pageable);
        } else {
            Query query = Query.query(new Criteria().orOperator(
                    getRegex("firstName", term),
                    getRegex("lastName", term),
                    getRegex("phoneNumber", term)
            ));
            List<PhoneDirectory> content = mongoTemplate.find(query, PhoneDirectory.class);
            long total = mongoTemplate.count(query, PhoneDirectory.class);
            return new PageImpl<>(content, pageable, total);
        }
    }

    private Criteria getRegex(String field, String term) {
        return Criteria.where(field).regex(term, "ig");
    }

}
