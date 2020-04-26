package com.wrhstrnsfr.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "branchs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchId;
	
	@Column(unique = true)
	private String branchName;
	
	@Column(columnDefinition = "boolean default false")
	private boolean isDeleted;
	
}
