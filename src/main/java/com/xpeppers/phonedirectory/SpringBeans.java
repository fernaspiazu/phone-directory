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
package com.xpeppers.phonedirectory;

import com.xpeppers.phonedirectory.controller.IndexController;
import com.xpeppers.phonedirectory.controller.PhoneDirectoryController;
import com.xpeppers.phonedirectory.repositories.PhoneDirectoryRepository;
import com.xpeppers.phonedirectory.services.PhoneDirectoryService;
import com.xpeppers.phonedirectory.services.PhoneDirectoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBeans {

	@Autowired
	private PhoneDirectoryRepository repository;

	@Bean
	public PhoneDirectoryService phoneDirectoryService() {
		return new PhoneDirectoryServiceImpl(repository);
	}

	@Bean
	public PhoneDirectoryController phoneDirectoryController() {
		return new PhoneDirectoryController(phoneDirectoryService());
	}

	@Bean
	public IndexController indexController() {
		return new IndexController();
	}

}
