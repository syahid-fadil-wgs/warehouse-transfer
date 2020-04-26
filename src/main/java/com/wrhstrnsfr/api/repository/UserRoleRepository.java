package com.wrhstrnsfr.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wrhstrnsfr.api.model.UserModel;
import com.wrhstrnsfr.api.model.UserRoleIdModel;
import com.wrhstrnsfr.api.model.UserRoleModel;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, UserRoleIdModel> {
	
	@Query("select ur from UserRoleModel ur where ur.eid.user = :user")
	UserRoleModel findByUser(UserModel user);
	
}
