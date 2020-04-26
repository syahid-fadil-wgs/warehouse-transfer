package com.wrhstrnsfr.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wrhstrnsfr.api.model.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {

	RoleModel findByRoleName(String roleName);
}
