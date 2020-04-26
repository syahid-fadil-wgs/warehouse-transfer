package com.wrhstrnsfr.api.service.impl;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wrhstrnsfr.api.model.RoleModel;
import com.wrhstrnsfr.api.model.UserModel;
import com.wrhstrnsfr.api.model.UserRoleIdModel;
import com.wrhstrnsfr.api.model.UserRoleModel;
import com.wrhstrnsfr.api.model.UserWarehouseIdModel;
import com.wrhstrnsfr.api.model.UserWarehouseModel;
import com.wrhstrnsfr.api.model.WarehouseModel;
import com.wrhstrnsfr.api.param.UserParam;
import com.wrhstrnsfr.api.repository.RoleRepository;
import com.wrhstrnsfr.api.repository.UserRepository;
import com.wrhstrnsfr.api.repository.UserRoleRepository;
import com.wrhstrnsfr.api.repository.UserWarehouseRepository;
import com.wrhstrnsfr.api.repository.WarehouseRepository;
import com.wrhstrnsfr.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private WarehouseRepository warehouseRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private UserRoleRepository userRoleRepo;
	
	@Autowired
	private UserWarehouseRepository userWarehouseRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(UserParam param) throws Exception {
		validate(param, null);
		UserModel entity = new UserModel();
		entity.setPassword(encoder.encode(param.getPassword()));
		entity = setEntity(entity, param);
		
		repo.save(entity);
		saveRole(entity);
		saveWarehouse(entity, param);
	}
	
	private void saveWarehouse(UserModel entity, UserParam param) throws Exception {
		WarehouseModel warehouse = warehouseRepo.findById(param.getWarehouseId()).orElse(null);
		if (warehouse == null) {
			throw new Exception("Invalid Warehouse");
		}
		UserWarehouseIdModel userWarehouseId = new UserWarehouseIdModel(entity, warehouse.getBranch(), warehouse);
		UserWarehouseModel userWarehouse = userWarehouseRepo.findById(userWarehouseId).orElse(null);
		if (userWarehouse == null) {
			userWarehouse = new UserWarehouseModel(userWarehouseId);
		}
		userWarehouseRepo.save(userWarehouse);
	}
	
	private void saveRole(UserModel entity) {
		RoleModel role = roleRepo.findByRoleName("warehouse");
		UserRoleIdModel userRoleId = new UserRoleIdModel(entity, role);
		UserRoleModel userRole = userRoleRepo.findById(userRoleId).orElse(null);
		if (userRole == null) {
			userRole = new UserRoleModel(userRoleId);
		}
		userRoleRepo.save(userRole);
	}
	
	private UserModel setEntity(UserModel entity, UserParam param) throws Exception {
		entity.setUsername(param.getUsername());
		entity.setName(param.getName());
		
		return entity;
	}

	private void validate(UserParam param, Long id) throws Exception {
		
		if (id == null) {
			if (param.getPassword() == null) {
				throw new Exception("Password is required");
			}
			if (param.getPassword2() == null) {
				throw new Exception("Retype Password is required");
			}
		}
		
		UserModel entity = repo.findByUsername(param.getUsername());
		if (entity != null && (id == null || id.equals(entity.getId()))) {
			throw new Exception("Username not available");
		}
	}

	@Override
	public Page<UserModel> findAll(int page, int limit, String q) {
		Specification<UserModel> spec = null;
		if (!q.equals("")) {
			spec = filter(q);
		}
		Pageable pageable = PageRequest.of(page - 1, limit);
		return repo.findAll(spec, pageable);
	}
	
	private Specification<UserModel> filter(String q) {
		return (root, query, cb) ->{
			Predicate whereFinal = null;
			String qword = "%" + q + "%";
			Predicate where1 = cb.like(root.<String> get("username"), qword);
			Predicate where2 = cb.like(root.<String> get("name"), qword);
			
			whereFinal = cb.or(where1, where2);
			return whereFinal;
		};
	}
	
	public UserModel findById(Long id) {
		return repo.findById(id).orElse(null);
	}
	
	@Transactional(rollbackOn = Exception.class)
	public void update(Long id, @Valid UserParam param) throws Exception {
		validate(param, id);
		UserModel entity = findById(id);
		if (entity == null) {
			throw new Exception("Data not found.");
		}
		entity = setEntity(entity, param);
		
		repo.save(entity);
		saveRole(entity);
		saveWarehouse(entity, param);
	}
	
	@Override
	public void delete(Long id) throws Exception {
		setIsDeleted(id, true);
	}
	
	private void setIsDeleted(Long id, boolean isDelete) throws Exception {
		UserModel entity = findById(id);
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
}
