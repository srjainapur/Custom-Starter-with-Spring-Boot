package com.zensar.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zensar.service.HelloService;
import com.zensar.service.HelloServiceImpl;

@Configuration
@ConditionalOnClass(HelloService.class)
public class HelloServiceAutoConfiguration {
	
	// conditional bean creation
	@Bean
	@ConditionalOnMissingBean
	public HelloService helloService() {
		return new HelloServiceImpl();
	}	
}
