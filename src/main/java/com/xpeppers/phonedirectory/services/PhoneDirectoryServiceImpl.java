package com.xpeppers.phonedirectory.services;

import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mysema.query.types.expr.BooleanExpression;
import com.xpeppers.phonedirectory.domain.PhoneDirectory;
import com.xpeppers.phonedirectory.domain.QPhoneDirectory;
import com.xpeppers.phonedirectory.repositories.PhoneDirectoryRepository;
import com.xpeppers.phonedirectory.services.pagination.DatatableParameters;
import com.xpeppers.phonedirectory.utils.HttpRequestDatatableParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction;

@Service
public class PhoneDirectoryServiceImpl implements PhoneDirectoryService {
  private static final String SORT_DIRECTION_ASC = "asc";
  private static final String SORT_DIRECTION_DESC = "desc";

  @Autowired
  private Gson gson;

  @Autowired
  private PhoneDirectoryRepository phoneRepository;

  @Override
  public String searchTelephones(HttpServletRequest request) {
    DatatableParameters parameters = new HttpRequestDatatableParameters(request);
    Pageable pageable = getPageable(parameters);
    Page queryResult = getQueryResult(pageable, parameters.getSearchCriteria());
    return gson.toJson(createDatatableAttributes(parameters, queryResult));
  }

  private Map<String, Object> createDatatableAttributes(DatatableParameters parameters, Page page) {
    final boolean hasContent = page.hasContent();
    final long totalRecords = (hasContent) ? page.getTotalElements() : 0L;
    final long totalDisplayRecords = (hasContent) ? page.getTotalElements() : 0L;

    Map<String, Object> datatableAttributes = Maps.newHashMap();
    datatableAttributes.put("sEcho", parameters.getEcho());
    datatableAttributes.put("iTotalRecords", totalRecords);
    datatableAttributes.put("iTotalDisplayRecords", totalDisplayRecords);
    datatableAttributes.put("aaData", page.getContent());
    return datatableAttributes;
  }

  private Page getQueryResult(Pageable pageable, String searchCriteria) {
    if (StringUtils.hasText(searchCriteria)) {
      return phoneRepository.findAll(whereFirstNameOrPhoneNumberLikeCriteriaEntry(searchCriteria), pageable);
    }
    return phoneRepository.findAll(pageable);
  }

  private BooleanExpression whereFirstNameOrPhoneNumberLikeCriteriaEntry(String searchCriteria) {
    QPhoneDirectory directory = QPhoneDirectory.phoneDirectory;
    return directory.firstName.like(anyWhere(searchCriteria)).or(directory.phoneNumber.like(anyWhere(searchCriteria)));
  }

  private static String anyWhere(String value) {
    return "%" + value + "%";
  }

  private Pageable getPageable(DatatableParameters parameters) {
    int page = parameters.getPage(), size = parameters.getSize();
    Optional<Sort> sort = getSort(parameters);
    return sort.isPresent() ? new PageRequest(page, size, sort.get()) : new PageRequest(page, size);
  }

  private Optional<Sort> getSort(DatatableParameters parameters) {
    String sortColumn = parameters.getSortColumn();
    String sortDirection = parameters.getSortDirection();
    if (StringUtils.hasText(sortColumn) && StringUtils.hasText(sortDirection)) {
      return Optional.of(new Sort(getDirection(sortDirection), sortColumn));
    }
    return Optional.absent();
  }

  private Direction getDirection(String sortDirection) {
    if (SORT_DIRECTION_ASC.equals(sortDirection)) {
      return Direction.ASC;
    } else if (SORT_DIRECTION_DESC.equals(sortDirection)) {
      return Direction.DESC;
    }
    return Sort.DEFAULT_DIRECTION;
  }

}
