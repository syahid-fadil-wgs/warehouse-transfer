package com.wrhstrnsfr.api.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wrhstrnsfr.api.model.ItemModel;

public interface ItemRepository extends JpaRepository<ItemModel, Long> {

	ItemModel findByItemName(String itemName);

	Page<ItemModel> findAll(Specification<ItemModel> spec, Pageable pageable);
	
	List<ItemModel> findAll(Specification<ItemModel> spec);
	
	@Query("select b from ItemModel b where b.isDeleted = false order by b.itemName asc")
	List<ItemModel> findAllOption(Specification<ItemModel> spec);
}
