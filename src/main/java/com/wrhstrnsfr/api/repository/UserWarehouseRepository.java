package com.wrhstrnsfr.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wrhstrnsfr.api.model.UserModel;
import com.wrhstrnsfr.api.model.UserWarehouseIdModel;
import com.wrhstrnsfr.api.model.UserWarehouseModel;

public interface UserWarehouseRepository extends JpaRepository<UserWarehouseModel, UserWarehouseIdModel> {
	
	@Query("select uw from UserWarehouseModel uw where uw.eid.user = :user")
	UserWarehouseModel findByUser(UserModel user);
}
