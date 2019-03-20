package com.zensar.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.zensar.custom.bean.CustomHelloService;
import com.zensar.service.HelloService;

@SpringBootApplication
public class CustomStarterDemoApplication implements CommandLineRunner {
	
	@Autowired
    HelloService service;
	
	@Bean
    public  HelloService helloService(){
        return new CustomHelloService();
    }
	
	public static void main(String[] args) {
		SpringApplication.run(CustomStarterDemoApplication.class, args);
	}
	
	@Override
    public void run(String... strings) throws Exception {
        service.hello();
    }
}
