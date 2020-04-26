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
public class BranchParam {
	
	@NotNull
	private String branchName;
}
