package com.xpeppers.phonedirectory.controller;

import com.xpeppers.phonedirectory.data.PhoneDirectory;
import com.xpeppers.phonedirectory.services.PhoneDirectoryService;
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

	public PhoneDirectoryController(PhoneDirectoryService service) {
		this.service = service;
	}

	@RequestMapping(value = "/save", method = POST)
	public ResponseEntity<Map<String, ?>> save(@RequestBody PhoneDirectory entry) {
		PhoneDirectory justSaved = service.saveEntry(entry);
		return new ResponseEntity<>(singletonMap("entity", justSaved), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/delete", method = DELETE)
	public ResponseEntity<Map<String, Boolean>> delete(@RequestParam("id") Long id) {
		boolean deleted = service.delete(id);
		return new ResponseEntity<>(singletonMap("isDeleted", deleted), HttpStatus.OK);
	}

	@RequestMapping(value = "/is-empty", method = GET)
	public ResponseEntity<Map<String, Boolean>> isEmpty() {
		final boolean empty = service.count() == 0;
		return new ResponseEntity<>(singletonMap("result", empty), HttpStatus.OK);
	}

	@RequestMapping(value = "/find", method = GET)
	public ResponseEntity<Page<PhoneDirectory>> findAll(
			@RequestParam("page") int page,
			@RequestParam("size") int size) {

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
