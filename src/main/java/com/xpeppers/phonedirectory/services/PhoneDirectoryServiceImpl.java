package com.xpeppers.phonedirectory.services;

import com.google.common.base.Optional;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.data.domain.Sort.Direction;

@Service
@Transactional
public class PhoneDirectoryServiceImpl implements PhoneDirectoryService {

  @Autowired
  private Gson gson;

  @Autowired
  private PhoneDirectoryRepository phoneRepository;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public PhoneDirectory saveEntry(PhoneDirectory entry) {
    return phoneRepository.save(entry);
  }

  @Override
  public PhoneDirectory findEntryById(Long id) {
    return phoneRepository.findOne(id);
  }

  @Override
  public String searchTelephones(HttpServletRequest request) {
    DatatableParameters parameters = new HttpRequestDatatableParameters(request);
    Pageable pageable = getPageable(parameters);
    Page queryResult = getQueryResult(pageable, parameters.getSearchCriteria());
    return gson.toJson(datatableResponseAttributes(parameters, queryResult));
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
    Optional<Direction> direction = Optional.fromNullable(Direction.fromStringOrNull(sortDirection));
    return direction.isPresent() ? direction.get() : Sort.DEFAULT_DIRECTION;
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

  private Map<String, Object> datatableResponseAttributes(DatatableParameters parameters, Page page) {
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

}
