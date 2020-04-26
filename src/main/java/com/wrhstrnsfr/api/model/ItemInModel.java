package com.wrhstrnsfr.api.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "item_in")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemInModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	private ItemModel item;
	
	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	private WarehouseModel warehouse;
	
	private int qty;
	
	@Column(columnDefinition = "text")
	private String description;
	
	@CreationTimestamp
    @Column(nullable = false, updatable = false)
    @Temporal (TemporalType.TIMESTAMP)
    @CreatedDate
    private Date inputTime;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserModel user;
}
