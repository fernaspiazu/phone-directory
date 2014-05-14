package com.xpeppers.phonedirectory.services;

import com.xpeppers.phonedirectory.domain.PhoneDirectory;

import javax.servlet.http.HttpServletRequest;

public interface PhoneDirectoryService {

  PhoneDirectory saveEntry(PhoneDirectory entry);

  PhoneDirectory findEntryById(Long id);

  String searchTelephones(HttpServletRequest request);

}
