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
@Table(name = "user_warehouses")
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWarehouseModel {

	@EmbeddedId
	private UserWarehouseIdModel eid;
	
}
