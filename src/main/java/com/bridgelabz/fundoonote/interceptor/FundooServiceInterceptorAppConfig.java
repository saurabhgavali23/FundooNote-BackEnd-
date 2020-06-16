package com.bridgelabz.fundoonote.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class FundooServiceInterceptorAppConfig implements WebMvcConfigurer{

	@Autowired
	FundooServiceInterceptor fundooServiceInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		System.out.println("ServiceAppConfig");
		registry.addInterceptor(fundooServiceInterceptor)
		.addPathPatterns("/fundoonote/*")
		.excludePathPatterns("/fundoonote/login");
	}
}
