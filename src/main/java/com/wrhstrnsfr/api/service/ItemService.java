package com.wrhstrnsfr.api.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;

import com.wrhstrnsfr.api.model.ItemModel;
import com.wrhstrnsfr.api.param.ItemParam;

public interface ItemService {

	public void save(ItemParam param) throws Exception;

	public Page<ItemModel> findAll(int page, int limit, String q);

	public ItemModel findById(Long id);

	public void update(Long id, @Valid ItemParam param) throws Exception;

	public void delete(Long id) throws Exception;

	public void undelete(Long id) throws Exception;

	public List<ItemModel> findSearch(String q);
	
}
