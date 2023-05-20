package com.chatlucid.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.chatlucid.handler.CustomExceptionHandler;
import com.chatlucid.filter.ExceptionHandlerFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.chatlucid.*"})
@PropertySource({"classpath:application.properties"})
@EntityScan(basePackages = {"com.chatlucid.*"})
@EnableJpaRepositories({"com.chatlucid.*"})
@EnableScheduling
@EnableWebMvc
public class ChatLucidApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ChatLucidApplication.class, args);
		log.debug("Listening for HTTP");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ChatLucidApplication.class);
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDefaultPropertyInclusion(JsonInclude.Value.construct(JsonInclude.Include.USE_DEFAULTS, JsonInclude.Include.NON_NULL));
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.registerModule(new JavaTimeModule());
		mapper.registerModule(new Jdk8Module());

		return mapper;
	}

	@Bean
	public FilterRegistrationBean<ExceptionHandlerFilter> exceptionHandlerFilterRegistration(CustomExceptionHandler customExceptionHandler) {
		FilterRegistrationBean<ExceptionHandlerFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new ExceptionHandlerFilter(customExceptionHandler));
		registration.addUrlPatterns("/*");
		registration.setEnabled(true);
		registration.setOrder(1);
		return registration;
	}
}
