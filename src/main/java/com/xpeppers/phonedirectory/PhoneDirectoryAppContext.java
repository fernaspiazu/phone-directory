package com.xpeppers.phonedirectory;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.xpeppers.phonedirectory")
@Import(WebMVCAppContext.class)
public class PhoneDirectoryAppContext {
}
