package com.wrhstrnsfr.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.wrhstrnsfr.api.model.UserWarehouseModel;
import com.wrhstrnsfr.api.model.BranchModel;

import com.wrhstrnsfr.api.model.ItemTransferModel;

public interface ItemTransferRepository extends JpaRepository<ItemTransferModel, Long> {

	@Query("select i from ItemTransferModel i "
		+ " join UserWarehouseModel uw on uw.eid.user.id = i.inputBy.id or uw.eid.user.id = i.pickupBy.id "
		+ " and (uw.eid.branch.branchId = i.from.branch.branchId or uw.eid.branch.branchId = i.to.branch.branchId) "
		+ " where i.inputBy.id = :userId or i.pickupBy.id = :userId order by i.inputTime desc")
	public Page<ItemTransferModel> findAll(Long userId, Pageable pageable);
}
