package com.xpeppers.phonedirectory.controller;

import com.xpeppers.phonedirectory.services.PhoneDirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
  public static final String JSON_UTF_8 = "application/json;charset=UTF-8";

  @Autowired
  private PhoneDirectoryService phoneDirectoryService;

  @RequestMapping(value = "/home", method = RequestMethod.GET)
  public String home() {
    return "home";
  }

  @RequestMapping(value = "/search", method = RequestMethod.GET, produces = JSON_UTF_8)
  public @ResponseBody String search() {
    return phoneDirectoryService.searchTelephones(getHttpServletRequest());
  }

  private static HttpServletRequest getHttpServletRequest() {
    return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
  }

}
