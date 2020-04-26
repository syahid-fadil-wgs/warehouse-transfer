package com.wrhstrnsfr.api.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWarehouseIdModel implements Serializable {

	@OneToOne
	@JoinColumn(name = "user_id")
	private UserModel user;

	@ManyToOne(targetEntity = BranchModel.class)
	@JoinColumn(name = "branchId")
	private BranchModel branch;

	@ManyToOne(targetEntity = WarehouseModel.class)
	@JoinColumn(name = "warehouseId")
	private WarehouseModel warehouse;
	
}
