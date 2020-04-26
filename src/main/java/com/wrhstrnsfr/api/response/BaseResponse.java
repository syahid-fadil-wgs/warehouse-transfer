package com.wrhstrnsfr.api.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class BaseResponse<T> {
	
	public BaseResponse() {
		this.timestamp = new Date().toString();
	}

	private String timestamp;
	
	private int status;
	
	private String error;
	
	private String message;
	
	private T data;
	
}