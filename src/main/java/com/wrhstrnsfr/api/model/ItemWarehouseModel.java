package com.wrhstrnsfr.api.model;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_warehouses")
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemWarehouseModel {

	@EmbeddedId
	private ItemWarehouseIdModel eid;
	
	private int qty;
	
}
