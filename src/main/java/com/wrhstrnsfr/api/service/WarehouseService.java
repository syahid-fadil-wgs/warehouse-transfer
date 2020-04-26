package com.wrhstrnsfr.api.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.wrhstrnsfr.api.model.WarehouseModel;
import com.wrhstrnsfr.api.param.WarehouseParam;

public interface WarehouseService {

	public void save(WarehouseParam param) throws Exception;

	public Page<WarehouseModel> findAll(int page, int limit, String q);

	public WarehouseModel findById(Long id);

	public void update(Long id, @Valid WarehouseParam param) throws Exception;

	public void delete(Long id) throws Exception;

	public void undelete(Long id) throws Exception;

	public List<WarehouseModel> findByBranch(Long branchId);
	
}
