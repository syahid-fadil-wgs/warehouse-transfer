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
public class ItemWarehouseIdModel implements Serializable {

	@OneToOne
	@JoinColumn(name = "item_id")
	private ItemModel item;

	@ManyToOne(targetEntity = WarehouseModel.class)
	@JoinColumn(name = "warehouseId")
	private WarehouseModel warehouse;
	
}
