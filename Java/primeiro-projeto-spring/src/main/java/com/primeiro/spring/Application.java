package com.primeiro.spring;

import java.util.Arrays;

import org.springframework.boot.*;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx){
		return args -> {
			System.out.println("Lets inspect the beans.");
			String[] beanNames = ctx.getBeanDefinitionNames();

			Arrays.sort(beanNames);
			for(String beanName : beanNames){
				System.out.println(beanName);	
			}
		};
	}
}
