package com.wrhstrnsfr.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wrhstrnsfr.api.model.ItemOutModel;
import com.wrhstrnsfr.api.model.UserWarehouseModel;
import com.wrhstrnsfr.api.model.BranchModel;

public interface ItemOutRepository extends JpaRepository<ItemOutModel, Long> {

	@Query("select i from ItemOutModel i "
		+ " join UserWarehouseModel uw on uw.eid.user.id = i.user.id "
		+ " and uw.eid.branch.branchId = i.warehouse.branch.branchId "
		+ " where i.user.id = :userId order by i.inputTime desc")
	public Page<ItemOutModel> findAll(Long userId, Pageable pageable);
}
