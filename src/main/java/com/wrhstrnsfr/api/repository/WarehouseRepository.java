package com.wrhstrnsfr.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wrhstrnsfr.api.model.WarehouseModel;

public interface WarehouseRepository extends JpaRepository<WarehouseModel, Long> {

	WarehouseModel findByWarehouseName(String warehouseName);

	Page<WarehouseModel> findAll(Specification<WarehouseModel> spec, Pageable pageable);

	List<WarehouseModel> findAll(Specification<WarehouseModel> spec);

	List<WarehouseModel> findByBranch_BranchId(Long branchId);

	@Query("select w from WarehouseModel w where w.branch.branchId = :branchId and w.isDeleted = false order by w.warehouseName asc")
	List<WarehouseModel> findByBranch(Long branchId);
	
}
