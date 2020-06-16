package com.bridgelabz.fundoonote.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;


public class EmailThymeleafConfig {

	@Autowired
	SpringTemplateEngine templateEngine;
	
	SpringResourceTemplateResolver emailTemplateResolver;
	
	@Bean
	public SpringTemplateEngine springTemplateEngine() {
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		return templateEngine;
	}

	private SpringResourceTemplateResolver htmlTemplateResolver() {
		emailTemplateResolver.setPrefix("classpath:/template/");
		emailTemplateResolver.setSuffix(".html");
		emailTemplateResolver.setTemplateMode(templateMode);
		return null;
	}
	
	
}
