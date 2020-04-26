package com.wrhstrnsfr.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wrhstrnsfr.api.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {

	UserModel findByUsername(String username);

	Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);
	
}
