package com.wrhstrnsfr.api.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.wrhstrnsfr.api.model.BranchModel;
import com.wrhstrnsfr.api.param.BranchParam;

public interface BranchService {

	public void save(BranchParam param) throws Exception;

	public Page<BranchModel> findAll(int page, int limit, String q);

	public BranchModel findById(Long id);

	public void update(Long id, @Valid BranchParam param) throws Exception;

	public void delete(Long id) throws Exception;

	public void undelete(Long id) throws Exception;

	public List<BranchModel> findSearch(String q);
	
}
