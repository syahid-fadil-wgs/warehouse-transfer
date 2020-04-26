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

import com.wrhstrnsfr.api.model.BranchModel;
import com.wrhstrnsfr.api.model.WarehouseModel;
import com.wrhstrnsfr.api.param.WarehouseParam;
import com.wrhstrnsfr.api.repository.BranchRepository;
import com.wrhstrnsfr.api.repository.WarehouseRepository;
import com.wrhstrnsfr.api.service.WarehouseService;

@Service
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseRepository repo;
	
	@Autowired
	private BranchRepository branchRepo;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(WarehouseParam param) throws Exception {
		validate(param, null);
		WarehouseModel entity = new WarehouseModel();
		entity.setWarehouseName(param.getWarehouseName());
		BranchModel branch = branchRepo.findById(param.getBranchId()).orElse(null);
		if (branch == null) {
			throw new Exception("Branch not found");
		}
		entity.setBranch(branch);
		
		repo.save(entity);
	}

	private void validate(WarehouseParam param, Long id) throws Exception {
		WarehouseModel entity = repo.findByWarehouseName(param.getWarehouseName());
		if (entity != null && (id == null || id.equals(entity.getWarehouseId()))) {
			throw new Exception("Warehouse name already exist");
		}
	}

	@Override
	public Page<WarehouseModel> findAll(int page, int limit, String q) {
		Specification<WarehouseModel> spec = null;
		if (!q.equals("")) {
			spec = filter(q);
		}
		Pageable pageable = PageRequest.of(page - 1, limit);
		return repo.findAll(spec, pageable);
	}
	
	private Specification<WarehouseModel> filter(String q) {
		return (root, query, cb) ->{
			String qword = "%" + q + "%";
			Predicate where = cb.like(root.<String> get("warehouseName"), qword);
			
			return where;
		};
	}
	
	public WarehouseModel findById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public void update(Long id, @Valid WarehouseParam param) throws Exception {
		validate(param, id);
		WarehouseModel entity = findById(id);
		if (entity == null) {
			throw new Exception("Data not found.");
		}
		entity.setWarehouseName(param.getWarehouseName());
		BranchModel branch = branchRepo.findById(param.getBranchId()).orElse(null);
		if (branch == null) {
			throw new Exception("Branch not found");
		}
		entity.setBranch(branch);
		
		repo.save(entity);
	}
	
	@Override
	public void delete(Long id) throws Exception {
		setIsDeleted(id, true);
	}
	
	private void setIsDeleted(Long id, boolean isDelete) throws Exception {
		WarehouseModel entity = findById(id);
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
	public List<WarehouseModel> findByBranch(Long branchId) {
		return repo.findByBranch(branchId);
	}
}
