package com.wrhstrnsfr.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wrhstrnsfr.api.model.UserWarehouseIdModel;
import com.wrhstrnsfr.api.model.UserWarehouseModel;

public interface UserWarehouseRepository extends JpaRepository<UserWarehouseModel, UserWarehouseIdModel> {
	
}
