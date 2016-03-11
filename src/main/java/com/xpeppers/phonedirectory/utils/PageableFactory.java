package com.xpeppers.phonedirectory.utils;

import com.xpeppers.phonedirectory.services.QueryParameters;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Optional;

public class PageableFactory {

	private PageableFactory() {
	}

	public static Pageable makePageable(QueryParameters parameters) {
		return new PageableFactory().getPageable(parameters);
	}

	private Pageable getPageable(QueryParameters parameters) {
		int page = parameters.getPage(), size = parameters.getSize();
		Optional<Sort> sort = getSort(parameters);
		return sort.isPresent() ? new PageRequest(page, size, sort.get()) : new PageRequest(page, size);
	}

	private Optional<Sort> getSort(QueryParameters parameters) {
		String sortColumn = parameters.getSortColumn();
		String sortDirection = parameters.getSortDirection();
		if (StringUtils.hasText(sortColumn) && StringUtils.hasText(sortDirection)) {
			return Optional.of(new Sort(getDirection(sortDirection), sortColumn));
		}
		return Optional.empty();
	}

	private Sort.Direction getDirection(String sortDirection) {
		Optional<Sort.Direction> direction = Optional.ofNullable(Sort.Direction.fromStringOrNull(sortDirection));
		return direction.isPresent() ? direction.get() : Sort.DEFAULT_DIRECTION;
	}

}
