package com.example.rest.helloworld;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	// get
	// /hello-world(end-point)
	// @RequestMapping 도 가능. 최근에는 GetMapping 선언 시켜 줌으로써 어떤 http 의 메소드를 사용할 것인지를
	// 바로 알 수 있음.
	@GetMapping(path = "/hello-world")
	public String helloworld() {
		return "Hello World";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping(path="/hello-world-bean/path-variable/{name}")
	public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name) {
		return new HelloWorldBean(String.format("Hello World, %s" , name));
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required=false) Locale locale) {
		return messageSource.getMessage("greeting.message", null, locale);
	}
}
