package com.instagram.backend;

import com.instagram.backend.config.JwtService;
import com.jpomykala.springhoc.cors.EnableCORS;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {


		//SpringApplication.run(BackendApplication.class, args);


		var app=new SpringApplication(BackendApplication.class);

		//app.setDefaultProperties(Collections.singletonMap("spring.profiles.active", "dev"));

		var ctx=app.run(args);
		JwtService jwtService=ctx.getBean(JwtService.class);
		jwtService.printKey();


	}


}
