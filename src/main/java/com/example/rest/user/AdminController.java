package com.example.rest.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private UserDaoService service;
	
	public AdminController(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public MappingJacksonValue retrieveAllUsers(){
		List<User> users = service.findAll();
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "password");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
		MappingJacksonValue maping = new MappingJacksonValue(users);
		maping.setFilters(filters);
		
		return maping;
	}
	
	
	//@GetMapping("v1/users/{id}")
	//@GetMapping(value = "users/{id}/", params = "version=1")
	//@GetMapping(value = "users/{id}", headers = "X-API-VERSION=1")
	@GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv1+json")
	public MappingJacksonValue retrieveUserV1(@Valid @PathVariable int id) {
		
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "password");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);
		MappingJacksonValue maping = new MappingJacksonValue(user);
		maping.setFilters(filters);
		
		return maping;
	}
	
	//@GetMapping("v2/users/{id}")
	//@GetMapping(value = "users/{id}/", params = "version=2")
	//@GetMapping(value = "users/{id}", headers = "X-API-VERSION=2")
	@GetMapping(value = "/users/{id}", produces = "application/vnd.company.appv2+json")
	public MappingJacksonValue retrieveUserV2(@Valid @PathVariable int id) {
		
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
		
		UserV2 userV2 = new UserV2();
		BeanUtils.copyProperties(user, userV2);
		userV2.setGrade("vip");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);
		MappingJacksonValue maping = new MappingJacksonValue(userV2);
		maping.setFilters(filters);
		
		return maping;
	}
	
	@PostMapping("/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
								.path("/{id}")
								.buildAndExpand(savedUser.getId())
								.toUri();  // 사용자는새로 생성된 user의 id 를 모르므로, 이렇게 다시 서버에 물어보고, 그 id 를 핸들링해주는 것.
										   // 201로 적절히 처리해주는 것이 좋다. 작업용도에 맞춰 http status code 를 제어할 수 있는 것이 
										   // 좋은 설계임.
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = service.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException(String.format("ID[%s] not found", id));
		}
	}
}
