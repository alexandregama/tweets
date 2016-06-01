package com.hello.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class HelloController {

	@RequestMapping("/hello")
	public String home(@RequestParam(name = "name", required = false, defaultValue = "Hello Spring") String message, Model model) {
		model.addAttribute("message", message);
		return "resultPage";
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloController.class, args);
	}

}
