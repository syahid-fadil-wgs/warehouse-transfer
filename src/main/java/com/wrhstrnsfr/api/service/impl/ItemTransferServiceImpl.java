package com.wrhstrnsfr.api.service.impl;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.wrhstrnsfr.api.model.ItemInModel;
import com.wrhstrnsfr.api.model.ItemModel;
import com.wrhstrnsfr.api.model.ItemMovementModel;
import com.wrhstrnsfr.api.model.ItemOutModel;
import com.wrhstrnsfr.api.model.ItemTransferModel;
import com.wrhstrnsfr.api.model.ItemWarehouseIdModel;
import com.wrhstrnsfr.api.model.ItemWarehouseModel;
import com.wrhstrnsfr.api.model.UserModel;
import com.wrhstrnsfr.api.model.UserWarehouseIdModel;
import com.wrhstrnsfr.api.model.UserWarehouseModel;
import com.wrhstrnsfr.api.model.WarehouseModel;
import com.wrhstrnsfr.api.param.ItemInOutParam;
import com.wrhstrnsfr.api.param.ItemTransferParam;
import com.wrhstrnsfr.api.repository.ItemInRepository;
import com.wrhstrnsfr.api.repository.ItemMovementRepository;
import com.wrhstrnsfr.api.repository.ItemOutRepository;
import com.wrhstrnsfr.api.repository.ItemRepository;
import com.wrhstrnsfr.api.repository.ItemTransferRepository;
import com.wrhstrnsfr.api.repository.ItemWarehouseRepository;
import com.wrhstrnsfr.api.repository.UserRepository;
import com.wrhstrnsfr.api.repository.UserWarehouseRepository;
import com.wrhstrnsfr.api.repository.WarehouseRepository;
import com.wrhstrnsfr.api.service.ItemTransferService;

@Service
public class ItemTransferServiceImpl implements ItemTransferService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserWarehouseRepository userWarehouseRepo;
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private WarehouseRepository warehouseRepo;
	
	@Autowired
	private ItemInRepository itemInRepo;
	
	@Autowired
	private ItemWarehouseRepository itemWarehouseRepo;
	
	@Autowired
	private ItemMovementRepository itemMoveRepo;
	
	@Autowired
	private ItemOutRepository itemOutRepo;
	
	@Autowired
	private ItemTransferRepository itemTransferRepo;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveItemIn(ItemInOutParam param) throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = userRepo.findByUsername(username);
		
		ItemModel item = itemRepo.findById(param.getItemId()).orElse(null);
		if (item == null) {
			throw new Exception("Item not found.");
		}
		
		UserWarehouseModel userWarehouse = userWarehouseRepo.findByUser(user);
		WarehouseModel warehouse = userWarehouse.getEid().getWarehouse();
		if (warehouse == null) {
			throw new Exception("Warehouse not found.");
		}
		
		ItemInModel itemIn = new ItemInModel();
		itemIn.setUser(user);
		itemIn.setItem(item);
		itemIn.setWarehouse(warehouse);
		itemIn.setQty(param.getQty());
		itemIn.setDescription(param.getDescription());
		
		itemInRepo.save(itemIn);
		
		updateQty(item, warehouse, param.getQty(), param.getDescription(), "in");
	}
	
	private void updateQty(ItemModel item, WarehouseModel warehouse, int qty, String description, String moveType) throws Exception {
		ItemWarehouseIdModel itemWarehouseId = new ItemWarehouseIdModel(item, warehouse);
		ItemWarehouseModel itemWarehouse = itemWarehouseRepo.findById(itemWarehouseId).orElse(null);
		if (itemWarehouse == null) {
			itemWarehouse = new ItemWarehouseModel();
			itemWarehouse.setEid(itemWarehouseId);
		}
		if (qty < 0) {
			if (itemWarehouse.getQty() + qty < 0) {
				throw new Exception("Qty invalid, available qty is " + itemWarehouse.getQty());
			}
		}
		final int prevQty = itemWarehouse.getQty();
		itemWarehouse.setQty(itemWarehouse.getQty() + qty);
		
		itemWarehouseRepo.save(itemWarehouse);
		
		ItemMovementModel itemMove = new ItemMovementModel();
		itemMove.setItem(item);
		itemMove.setWarehouse(warehouse);
		itemMove.setPrevQty(prevQty);
		if (qty < 0) {
			qty = Math.abs(qty);
			itemMove.setInQty(0);
			itemMove.setOutQty(qty);
		} else {
			itemMove.setInQty(qty);
			itemMove.setOutQty(0);
		}
		itemMove.setDescription(description);
		itemMove.setMovementType(moveType);
		
		itemMoveRepo.save(itemMove);
	}

	@Override
	public Page<ItemInModel> listItemIn(int page, int limit) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = userRepo.findByUsername(username);
		Pageable pageable = PageRequest.of(page - 1, limit);
		return itemInRepo.findAll(user.getId(), pageable);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveItemOut(ItemInOutParam param) throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = userRepo.findByUsername(username);
		
		ItemModel item = itemRepo.findById(param.getItemId()).orElse(null);
		if (item == null) {
			throw new Exception("Item not found.");
		}

		UserWarehouseModel userWarehouse = userWarehouseRepo.findByUser(user);
		WarehouseModel warehouse = userWarehouse.getEid().getWarehouse();
		if (warehouse == null) {
			throw new Exception("Warehouse not found.");
		}
		
		ItemOutModel itemOut = new ItemOutModel();
		itemOut.setUser(user);
		itemOut.setItem(item);
		itemOut.setWarehouse(warehouse);
		itemOut.setQty(param.getQty());
		itemOut.setDescription(param.getDescription());
		
		itemOutRepo.save(itemOut);
		
		updateQty(item, warehouse, -param.getQty(), param.getDescription(), "out");
	}

	@Override
	public Page<ItemOutModel> listItemOut(int page, int limit) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = userRepo.findByUsername(username);
		Pageable pageable = PageRequest.of(page - 1, limit);
		return itemOutRepo.findAll(user.getId(), pageable);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveItemTransfer(ItemTransferParam param) throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = userRepo.findByUsername(username);

		UserWarehouseModel userWarehouse = userWarehouseRepo.findByUser(user);
		WarehouseModel warehouse = userWarehouse.getEid().getWarehouse();
		if (warehouse == null) {
			throw new Exception("Warehouse not found.");
		}
		
		if (warehouse.getWarehouseId() == param.getWarehouseIdTo()) {
			throw new Exception("Invalid warehouse, warehouse origin same with warehouse destination.");
		}
		
		ItemModel item = itemRepo.findById(param.getItemId()).orElse(null);
		if (item == null) {
			throw new Exception("Item not found.");
		}
		
		WarehouseModel warehouseTo = warehouseRepo.findById(param.getWarehouseIdTo()).orElse(null);
		if (warehouseTo == null) {
			throw new Exception("Warehouse not found.");
		}
		
		ItemTransferModel itemTransfer = new ItemTransferModel();
		itemTransfer.setInputBy(user);
		itemTransfer.setItem(item);
		itemTransfer.setFrom(warehouse);
		itemTransfer.setTo(warehouse);
		itemTransfer.setQty(param.getQty());
		itemTransfer.setDescription(param.getDescription());
		
		itemTransferRepo.save(itemTransfer);
		
		updateQty(item, warehouse, -param.getQty(), param.getDescription(), "transfer");
	}

	@Override
	public Page<ItemTransferModel> listItemTransfer(int page, int limit) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = userRepo.findByUsername(username);
		Pageable pageable = PageRequest.of(page - 1, limit);
		return itemTransferRepo.findAll(user.getId(), pageable);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void pickupItemTransfer(Long id) throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = userRepo.findByUsername(username);
		
		ItemTransferModel transfer = itemTransferRepo.findById(id).orElse(null);
		if (transfer == null) {
			throw new Exception("Data not found.");
		}
		transfer.setPickupTime(new Date());
		transfer.setPickupBy(user);
		
		itemTransferRepo.save(transfer);
		updateQty(transfer.getItem(), transfer.getTo(), transfer.getQty(), transfer.getDescription(), "transfer");
	}

	@Override
	public Page<ItemMovementModel> listItemMove(int page, int limit) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserModel user = userRepo.findByUsername(username);
		Pageable pageable = PageRequest.of(page - 1, limit);
		return itemMoveRepo.findAll(user.getId(), pageable);
	}
	
}
