package com.xpeppers.phonedirectory.controller;

import com.xpeppers.phonedirectory.repositories.PhoneDirectory;
import com.xpeppers.phonedirectory.services.PhoneDirectoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class PhoneDirectoryController {

	private final PhoneDirectoryService service;

	public PhoneDirectoryController(PhoneDirectoryService service) {
		this.service = service;
	}

	@RequestMapping("/test")
	@ResponseBody
	public ResponseEntity<PhoneDirectory> test(@RequestParam("id") Long id) {
		final Optional<PhoneDirectory> entry = service.findEntryById(id);
		if (entry.isPresent()) {
			return new ResponseEntity<>(entry.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

}
