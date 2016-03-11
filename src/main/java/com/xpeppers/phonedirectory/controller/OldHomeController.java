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
package com.xpeppers.phonedirectory.controller;

import com.xpeppers.phonedirectory.repositories.PhoneDirectory;
import com.xpeppers.phonedirectory.services.PhoneDirectoryService;
import com.xpeppers.phonedirectory.utils.HttpRequestDatatableParameters;
import com.xpeppers.phonedirectory.utils.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@Deprecated
public class OldHomeController {
	public static final String JSON_UTF_8 = "application/json;charset=utf-8";

	@Autowired
	private PhoneDirectoryService phoneDirectoryService;

	@Autowired
	private PhoneDirectoryValidator phoneDirectoryValidator;

	@RequestMapping("/")
	public String home() {
		return "home";
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = JSON_UTF_8)
	@ResponseBody
	public Map<String, Object> search() {
		return phoneDirectoryService.searchTelephones(new HttpRequestDatatableParameters(getHttpServletRequest()));
	}

	@RequestMapping(value = "/new-entry", method = RequestMethod.GET)
	public String newEntryPage(Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("entry", new PhoneDirectory());
		return "new-entry";
	}

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String entryDetails(@RequestParam("id") Long id, Model model) {
		Optional<PhoneDirectory> entry = phoneDirectoryService.findEntryById(id);
		if (entry.isPresent()) {
			model.addAttribute("edit", true);
			model.addAttribute("entry", entry.get());
			return "new-entry";
		}
		return "redirect:/404";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveEntry(@ModelAttribute("entry") PhoneDirectory phoneDirectory) {
		phoneDirectoryService.saveEntry(phoneDirectory);
		return "redirect:/home";
	}

	@RequestMapping(value = "/validate-entry", method = RequestMethod.POST, produces = JSON_UTF_8)
	@ResponseBody
	public ValidationResponse validateEntry(@ModelAttribute("entry") PhoneDirectory phoneDirectory) {
		return phoneDirectoryValidator.validate(phoneDirectory);
	}

	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String pageNotFound() {
		return "404";
	}

	private static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

}
