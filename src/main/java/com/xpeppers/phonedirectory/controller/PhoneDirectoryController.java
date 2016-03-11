package com.xpeppers.phonedirectory.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class PhoneDirectoryController {

	@RequestMapping("/test")
	public Map<String, ?> test() {
		return Collections.singletonMap("name", "Fernando Aspiazu");
	}

}
