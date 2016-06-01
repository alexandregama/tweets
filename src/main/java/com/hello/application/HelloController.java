package com.hello.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class HelloController {

	@RequestMapping("/hello")
	public String home() {
		return "resultPage";
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloController.class, args);
	}

}
