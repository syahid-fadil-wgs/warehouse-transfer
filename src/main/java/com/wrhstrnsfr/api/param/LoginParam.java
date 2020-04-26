package com.wrhstrnsfr.api.param;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginParam {
	
	@NotNull
	private String username;
	@NotNull
	private String password;
}
