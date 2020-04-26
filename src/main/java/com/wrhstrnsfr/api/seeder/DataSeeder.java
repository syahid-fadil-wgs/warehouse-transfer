package com.wrhstrnsfr.api.seeder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.wrhstrnsfr.api.model.BranchModel;
import com.wrhstrnsfr.api.model.UserModel;
import com.wrhstrnsfr.api.model.WarehouseModel;
import com.wrhstrnsfr.api.repository.BranchRepository;
import com.wrhstrnsfr.api.repository.UserRepository;
import com.wrhstrnsfr.api.repository.WarehouseRepository;

@Component
public class DataSeeder {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BranchRepository branchRepo;
	
	@Autowired
	WarehouseRepository warehouseRepo;
	
	@PostConstruct
	public void seed() {
		if (branchRepo.count() == 0) {
			BranchModel branch1 = new BranchModel(null, "Bandung 1", false);
			BranchModel branch2 = new BranchModel(null, "Bandung 2", false);
			branchRepo.saveAll(Arrays.asList(branch1, branch2));
		}
		
		if (warehouseRepo.count() == 0) {
			List<BranchModel> branchs = branchRepo.findAll();
			List<WarehouseModel> warehouses = new ArrayList<>();
			WarehouseModel warehouse;
			for (BranchModel row: branchs) {
				warehouse = new WarehouseModel(null, "Gudang 1 " + row.getBranchName(), row, false);
				warehouses.add(warehouse);
				
				warehouse = new WarehouseModel(null, "Gudang 2 " + row.getBranchName(), row, false);
				warehouses.add(warehouse);
			}
			warehouseRepo.saveAll(warehouses);
		}
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String pass = encoder.encode("pass1234");
		if (userRepo.count() == 0) {
			List<WarehouseModel> warehouses = warehouseRepo.findAll();
			List<UserModel> users = new ArrayList<>();
			UserModel user;
			for (WarehouseModel row: warehouses) {
				user = new UserModel(null, "admin." + String.valueOf(row.getWarehouseId()), "Admin " + row.getWarehouseName(), pass, false);
				users.add(user);
			}
			userRepo.saveAll(users);
		}
	}
}
