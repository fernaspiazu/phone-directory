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

import com.xpeppers.phonedirectory.services.pagination.QueryParameters;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class HttpRequestDatatableParameters extends QueryParameters {

	private HttpServletRequest request;

	public HttpRequestDatatableParameters(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getSearchCriteria() {
		return request.getParameter("sSearch");
	}

	@Override
	public int getDisplayStart() {
		return getParameterAsPrimitiveInt("iDisplayStart");
	}

	@Override
	public int getDisplayLength() {
		return getParameterAsPrimitiveInt("iDisplayLength");
	}

	@Override
	public String getColumnName(int columnIndex) {
		return request.getParameter("mDataProp_" + columnIndex);
	}

	@Override
	public int getSortColumnIndex() {
		return getParameterAsPrimitiveInt("iSortCol_0");
	}

	@Override
	public String getSortDirection() {
		return request.getParameter("sSortDir_0");
	}

	@Override
	public String getEcho() {
		return request.getParameter("sEcho");
	}

	private int getParameterAsPrimitiveInt(String parameter) {
		String intParameter = request.getParameter(parameter);
		if (StringUtils.hasText(intParameter)) {
			return Integer.parseInt(intParameter);
		}
		return 0;
	}

}
