package com.wrhstrnsfr.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wrhstrnsfr.api.model.ItemWarehouseIdModel;
import com.wrhstrnsfr.api.model.ItemWarehouseModel;

public interface ItemWarehouseRepository extends JpaRepository<ItemWarehouseModel, ItemWarehouseIdModel> {
	
}
