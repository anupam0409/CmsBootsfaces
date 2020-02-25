package com.perseus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.*;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class CmsBootsfacesApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CmsBootsfacesApplication.class, args);
	}

	@Bean
	public ErrorPageRegistrar errorPageRegistrar() {
		return new ErrorPageRegistrar() {
			@Override
			public void registerErrorPages(ErrorPageRegistry registry) {
				registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.xhtml"));
				registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.xhtml"));
				registry.addErrorPages(new ErrorPage(HttpStatus.BAD_GATEWAY, "/error/502.xhtml"));
			}
		};
	}

}
