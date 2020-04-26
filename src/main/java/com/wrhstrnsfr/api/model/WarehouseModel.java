package com.wrhstrnsfr.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long warehouseId;
	
	@Column(unique = true)
	private String warehouseName;
	
	@ManyToOne(targetEntity = BranchModel.class)
	@JoinColumn(name = "branchId")
	private BranchModel branch;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isDeleted;
}
