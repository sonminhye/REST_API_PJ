package com.example.rest.user;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value= {"password"})
//@JsonFilter("UserInfo")
@ApiModel(description="사용자 상세정보를 위한 도메인 객체")
public class User {
	
	@NotEmpty
	private Integer id;
	
	@Size(min=2, message="Name은 2글자 이상 입력해주세요.")
	@ApiModelProperty(notes = "사용자 이름을 입력해주세요.")
	private String name;
	
	@Past
	private Date joinDate;
	
	//@JsonIgnore
	private String password;
	
	//@JsonIgnore
	private String ssn;
	
}
