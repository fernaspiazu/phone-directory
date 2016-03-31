package com.xpeppers.phonedirectory.controller;

import com.xpeppers.phonedirectory.repositories.PhoneDirectory;
import com.xpeppers.phonedirectory.services.PhoneDirectoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonMap;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

@RestController
@RequestMapping("/api")
public class PhoneDirectoryController {

	private final PhoneDirectoryService service;

	public PhoneDirectoryController(PhoneDirectoryService service) {
		this.service = service;
	}

	@RequestMapping("/is-empty")
	public ResponseEntity<Map<String, Boolean>> isEmpty() {
		final boolean empty = service.count() == 0;
		return new ResponseEntity<>(singletonMap("result", empty), HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = DELETE)
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> delete(@RequestParam("id") Long id) {
		boolean deleted = service.delete(id);
		return new ResponseEntity<>(singletonMap("isDeleted", deleted), HttpStatus.OK);
	}

	@RequestMapping("/find")
	public ResponseEntity<Page<PhoneDirectory>> findAll(
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam(value = "sortCol", required = false) String sortCol,
			@RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {

		Optional<Sort> sort = getSort(sortCol, sortDir);
		Pageable pageable = sort.isPresent() ? new PageRequest(page, size, sort.get()) : new PageRequest(page, size);
		final Page<PhoneDirectory> all = service.findAll(pageable);
		return new ResponseEntity<>(all, HttpStatus.OK);
	}

	private Optional<Sort> getSort(String sortColumn, String sortDirection) {
		return (StringUtils.hasText(sortColumn) && StringUtils.hasText(sortDirection))
			? Optional.of(new Sort(getDirection(sortDirection), sortColumn))
			: Optional.empty();
	}

	private Sort.Direction getDirection(String sortDirection) {
		Optional<Sort.Direction> direction = Optional.ofNullable(Sort.Direction.fromStringOrNull(sortDirection));
		return direction.isPresent() ? direction.get() : Sort.DEFAULT_DIRECTION;
	}

}
