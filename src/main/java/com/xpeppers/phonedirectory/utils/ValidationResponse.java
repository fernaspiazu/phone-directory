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
package com.xpeppers.phonedirectory.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationResponse implements Serializable {
	private static final long serialVersionUID = -179694203412278724L;

	private String status;
	private List<ErrorMessage> errorMessages;

	public ValidationResponse() {
		this.errorMessages = new ArrayList<>();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ErrorMessage> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<ErrorMessage> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public boolean hasErrors() {
		return errorMessages == null || !errorMessages.isEmpty();
	}

	@Override
	public String toString() {
		return "ValidationResponse" +
			"{errorMessages=" + errorMessages +
			", status='" + status + '\'' +
			'}';
	}
}
