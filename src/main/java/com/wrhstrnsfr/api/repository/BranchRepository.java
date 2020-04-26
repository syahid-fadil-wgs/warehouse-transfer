package com.wrhstrnsfr.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wrhstrnsfr.api.model.BranchModel;

public interface BranchRepository extends JpaRepository<BranchModel, Long> {

	BranchModel findByBranchName(String branchName);

	Page<BranchModel> findAll(Specification<BranchModel> spec, Pageable pageable);
	
	List<BranchModel> findAll(Specification<BranchModel> spec);
	
	@Query("select b from BranchModel b where b.isDeleted = false order by b.branchName asc")
	List<BranchModel> findAllOption(Specification<BranchModel> spec);
}
