package com.wrhstrnsfr.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wrhstrnsfr.api.model.UserRoleIdModel;
import com.wrhstrnsfr.api.model.UserRoleModel;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, UserRoleIdModel> {
	
	
	
}
