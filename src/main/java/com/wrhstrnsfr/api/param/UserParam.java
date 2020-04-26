package com.wrhstrnsfr.api.param;

import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserParam {
	
	@NotNull
	private String username;
	@NotNull
	private String name;
	
	private String password;
	
	private String password2;
	@NotNull
	private Long warehouseId;
}
