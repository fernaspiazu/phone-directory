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
package com.xpeppers.phonedirectory.controllers;

import com.xpeppers.phonedirectory.model.PhoneDirectory;
import com.xpeppers.phonedirectory.service.PhoneDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/api")
public class PhoneDirectoryController {

    private final PhoneDirectoryService service;

    @Autowired
    public PhoneDirectoryController(PhoneDirectoryService service) {
        this.service = service;
    }

    @RequestMapping(value = "/save", method = POST)
    public ResponseEntity<Map<String, ?>> save(@RequestBody PhoneDirectory entry) {
        PhoneDirectory savedEntry = service.save(entry);
        return new ResponseEntity<>(singletonMap("entity", savedEntry), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete", method = DELETE)
    public ResponseEntity<Map<String, Boolean>> delete(@RequestParam("id") String id) {
        boolean isDeleted = service.delete(id).isPresent();
        return new ResponseEntity<>(singletonMap("isDeleted", isDeleted), HttpStatus.OK);
    }

    @RequestMapping(value = "/is-empty", method = GET)
    public ResponseEntity<Map<String, Boolean>> isEmpty() {
        return new ResponseEntity<>(singletonMap("result", service.count() == 0), HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = GET)
    public ResponseEntity<Page<PhoneDirectory>> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        final Page<PhoneDirectory> all = service.findAll(new PageRequest(page, size));
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = GET)
    public ResponseEntity<Page> search(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(name = "q", required = false) String query) {

        PageRequest pageRequest = new PageRequest(page, size);
        Page result = service.search(query, pageRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
