package com.wrhstrnsfr.api.service;

import org.springframework.data.domain.Page;

import com.wrhstrnsfr.api.model.ItemInModel;
import com.wrhstrnsfr.api.model.ItemMovementModel;
import com.wrhstrnsfr.api.model.ItemOutModel;
import com.wrhstrnsfr.api.model.ItemTransferModel;
import com.wrhstrnsfr.api.param.ItemInOutParam;
import com.wrhstrnsfr.api.param.ItemTransferParam;

public interface ItemTransferService {

	public void saveItemIn(ItemInOutParam param) throws Exception;
	
	public Page<ItemInModel> listItemIn(int page, int limit);
	
	public void saveItemOut(ItemInOutParam param) throws Exception;
	
	public Page<ItemOutModel> listItemOut(int page, int limit);
	
	public void saveItemTransfer(ItemTransferParam param) throws Exception;
	
	public Page<ItemTransferModel> listItemTransfer(int page, int limit);
	
	public void pickupItemTransfer(Long id) throws Exception;
	
	public Page<ItemMovementModel> listItemMove(int page, int limit);
}
