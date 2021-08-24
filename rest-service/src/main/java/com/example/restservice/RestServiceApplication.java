package com.example.restservice;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);

//		ApplicationContext ctx = SpringApplication.run(Application.class, args);
//		System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}
	}

}
