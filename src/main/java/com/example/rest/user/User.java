package com.example.rest.user;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
	
	@NotEmpty
	private Integer id;
	
	@Size(min=2, message="Name은 2글자 이상 입력해주세요.")
	private String name;
	
	@Past
	private Date joinDate;
}
