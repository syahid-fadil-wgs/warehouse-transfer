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
import com.wrhstrnsfr.api.param.BranchParam;
import com.wrhstrnsfr.api.repository.BranchRepository;
import com.wrhstrnsfr.api.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchRepository repo;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(BranchParam param) throws Exception {
		validate(param, null);
		BranchModel entity = new BranchModel();
		entity.setBranchName(param.getBranchName());
		
		repo.save(entity);
	}

	private void validate(BranchParam param, Long id) throws Exception {
		BranchModel entity = repo.findByBranchName(param.getBranchName());
		if (entity != null && (id == null || id.equals(entity.getBranchId()))) {
			throw new Exception("Branch name already exist");
		}
	}

	@Override
	public Page<BranchModel> findAll(int page, int limit, String q) {
		Specification<BranchModel> spec = null;
		if (!q.equals("")) {
			spec = filter(q);
		}
		Pageable pageable = PageRequest.of(page - 1, limit);
		return repo.findAll(spec, pageable);
	}
	
	private Specification<BranchModel> filter(String q) {
		return (root, query, cb) ->{
			String qword = "%" + q + "%";
			Predicate where = cb.like(root.<String> get("branchName"), qword);
			
			return where;
		};
	}
	
	public BranchModel findById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	public void update(Long id, @Valid BranchParam param) throws Exception {
		validate(param, id);
		BranchModel entity = findById(id);
		if (entity == null) {
			throw new Exception("Data not found.");
		}
		entity.setBranchName(param.getBranchName());
		
		repo.save(entity);
	}
	
	@Override
	public void delete(Long id) throws Exception {
		setIsDeleted(id, true);
	}
	
	private void setIsDeleted(Long id, boolean isDelete) throws Exception {
		BranchModel entity = findById(id);
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
	public List<BranchModel> findSearch(String q) {
		Specification<BranchModel> spec = null;
		if (!q.equals("")) {
			spec = filter(q);
		}
		return repo.findAllOption(spec);
	}
}
