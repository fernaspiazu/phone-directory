package com.xpeppers.phonedirectory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebAppConfig extends WebMvcConfigurerAdapter {

  public static final String LANGUAGE = "lang";
  public static final String GLOBAL_DATE_FORMAT = "dd-MM-yyyy";

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("/static/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("redirect:/home");
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable("default");
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName(LANGUAGE);
    return localeChangeInterceptor;
  }

  @Bean
  public BeanNameViewResolver beanNameViewResolver() {
    BeanNameViewResolver viewResolver = new BeanNameViewResolver();
    viewResolver.setOrder(1);
    return viewResolver;
  }

  @Bean
  public ThymeleafViewResolver thymeleafViewResolver() {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(thymeleafTemplateEngine());
    viewResolver.setCharacterEncoding("UTF-8");
    viewResolver.setOrder(2);
    return viewResolver;
  }

  @Bean
  public SpringTemplateEngine thymeleafTemplateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(thymeleafTemplateResolver());
    return templateEngine;
  }

  @Bean
  public ServletContextTemplateResolver thymeleafTemplateResolver() {
    ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
    templateResolver.setPrefix("/WEB-INF/views/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML5");
    templateResolver.setCharacterEncoding("UTF-8");
    // Uncomment these lines in order to use
    // cache when resolving templates.
    templateResolver.setCacheable(false);
    templateResolver.setCacheTTLMs(0L);
    // -------------------------------------
    return templateResolver;
  }

  @Bean
  public ReloadableResourceBundleMessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(basenames());
    messageSource.setDefaultEncoding("UTF-8");
    messageSource.setCacheSeconds(1);
    messageSource.setFallbackToSystemLocale(false);
    return messageSource;
  }

  private String[] basenames() {
    return new String[] { "/WEB-INF/i18n/global" };
  }

  @Bean
  public FormattingConversionService mvcConversionService() {
    DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);
    addFormattersForFieldAnnotation(conversionService);
    DateFormatterRegistrar registrar = new DateFormatterRegistrar();
    registrar.setFormatter(new DateFormatter(GLOBAL_DATE_FORMAT));
    registrar.registerFormatters(conversionService);
    return conversionService;
  }

  private void addFormattersForFieldAnnotation(DefaultFormattingConversionService conversionService) {
    conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());
    conversionService.addFormatterForFieldAnnotation(new DateTimeFormatAnnotationFormatterFactory());
  }

}
