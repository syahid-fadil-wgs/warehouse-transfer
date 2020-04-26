package com.wrhstrnsfr.api.service;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.wrhstrnsfr.api.model.UserModel;
import com.wrhstrnsfr.api.param.UserParam;

public interface UserService {

	public void save(UserParam param) throws Exception;

	public Page<UserModel> findAll(int page, int limit, String q);

	public UserModel findById(Long id);

	public void update(Long id, @Valid UserParam param) throws Exception;

	public void delete(Long id) throws Exception;

	public void undelete(Long id) throws Exception;
	
}
