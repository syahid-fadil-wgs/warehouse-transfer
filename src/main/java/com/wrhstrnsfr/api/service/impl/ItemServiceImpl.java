package com.wrhstrnsfr.api.service.impl;

import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wrhstrnsfr.api.model.ItemModel;
import com.wrhstrnsfr.api.param.ItemParam;
import com.wrhstrnsfr.api.repository.ItemRepository;
import com.wrhstrnsfr.api.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository repo;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(ItemParam param) throws Exception {
		validate(param, null);
		ItemModel entity = new ItemModel();
		entity.setItemName(param.getItemName());
		
		repo.save(entity);
	}

	private void validate(ItemParam param, Long id) throws Exception {
		ItemModel entity = repo.findByItemName(param.getItemName());
		if (entity != null && (id == null || id.equals(entity.getItemId()))) {
			throw new Exception("Item name already exist");
		}
	}

	@Override
	public Page<ItemModel> findAll(int page, int limit, String q) {
		Specification<ItemModel> spec = null;
		if (!q.equals("")) {
			spec = filter(q);
		}
		Pageable pageable = PageRequest.of(page - 1, limit);
		return repo.findAll(spec, pageable);
	}
	
	private Specification<ItemModel> filter(String q) {
		return (root, query, cb) ->{
			String qword = "%" + q + "%";
			Predicate where = cb.like(root.<String> get("ItemName"), qword);
			
			return where;
		};
	}
	
	public ItemModel findById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public void update(Long id, @Valid ItemParam param) throws Exception {
		validate(param, id);
		ItemModel entity = findById(id);
		if (entity == null) {
			throw new Exception("Data not found.");
		}
		entity.setItemName(param.getItemName());
		
		repo.save(entity);
	}
	
	@Override
	public void delete(Long id) throws Exception {
		setIsDeleted(id, true);
	}
	
	private void setIsDeleted(Long id, boolean isDelete) throws Exception {
		ItemModel entity = findById(id);
		if (entity == null) {
			throw new Exception("Data not found.");
		}
		entity.setDeleted(true);
		repo.save(entity);
	}
	
	@Override
	public void undelete(Long id) throws Exception {
		setIsDeleted(id, false);
	}

	@Override
	public List<ItemModel> findSearch(String q) {
		Specification<ItemModel> spec = null;
		if (!q.equals("")) {
			spec = filter(q);
		}
		return repo.findAllOption(spec);
	}
}
