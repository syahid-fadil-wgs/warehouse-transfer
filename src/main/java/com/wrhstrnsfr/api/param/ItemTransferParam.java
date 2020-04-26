package com.wrhstrnsfr.api.param;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemTransferParam {

	@NotNull
	private Long itemId;
	@NotNull
	private Long warehouseIdTo;
	@NotNull
	@Min((long)1)
	private int qty;
	
	private String description;
}
