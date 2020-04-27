package com.wrhstrnsfr.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wrhstrnsfr.api.model.ItemInModel;
import com.wrhstrnsfr.api.model.ItemMovementModel;
import com.wrhstrnsfr.api.model.ItemOutModel;
import com.wrhstrnsfr.api.model.ItemTransferModel;
import com.wrhstrnsfr.api.param.ItemInOutParam;
import com.wrhstrnsfr.api.param.ItemTransferParam;
import com.wrhstrnsfr.api.response.BaseResponse;
import com.wrhstrnsfr.api.response.JsonResponseUtil;
import com.wrhstrnsfr.api.service.ItemTransferService;

@RestController
@RequestMapping("/api/warehousing")
public class ItemTransferController {

	@Autowired
	ItemTransferService service;
	
	@PostMapping("/in")
	public ResponseEntity<BaseResponse<Object>> saveItemIn(@RequestBody ItemInOutParam param, BindingResult result) {
		if (result.hasErrors()) {
			return JsonResponseUtil.error(result.getFieldErrors());
		}
		try {
			service.saveItemIn(param);
		} catch (Exception e) {
			return JsonResponseUtil.error(null, e.getMessage());
		}
		return JsonResponseUtil.success(null);
	}
	
	@GetMapping("/in")
	public ResponseEntity<BaseResponse<Page<ItemInModel>>> listItemIn(@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "20") int limit) {
		Page<ItemInModel> data = service.listItemIn(page, limit);
		return JsonResponseUtil.success(data);
	}
	
	@PostMapping("/out")
	public ResponseEntity<BaseResponse<Object>> saveItemOut(@RequestBody ItemInOutParam param, BindingResult result) {
		if (result.hasErrors()) {
			return JsonResponseUtil.error(result.getFieldErrors());
		}
		try {
			service.saveItemOut(param);
		} catch (Exception e) {
			return JsonResponseUtil.error(null, e.getMessage());
		}
		return JsonResponseUtil.success(null);
	}
	
	@GetMapping("/out")
	public ResponseEntity<BaseResponse<Page<ItemOutModel>>> listItemOut(@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "20") int limit) {
		Page<ItemOutModel> data = service.listItemOut(page, limit);
		return JsonResponseUtil.success(data);
	}
	
	@PostMapping("/transfer")
	public ResponseEntity<BaseResponse<Object>> saveItemTransfer(@RequestBody ItemTransferParam param, BindingResult result) {
		if (result.hasErrors()) {
			return JsonResponseUtil.error(result.getFieldErrors());
		}
		try {
			service.saveItemTransfer(param);
		} catch (Exception e) {
			return JsonResponseUtil.error(null, e.getMessage());
		}
		return JsonResponseUtil.success(null);
	}
	
	@GetMapping("/transfer")
	public ResponseEntity<BaseResponse<Page<ItemTransferModel>>> listItemTransfer(@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "20") int limit) {
		Page<ItemTransferModel> data = service.listItemTransfer(page, limit);
		return JsonResponseUtil.success(data);
	}
	
	@PostMapping("/transfer/pickup/{id}")
	public ResponseEntity<BaseResponse<Object>> pickupItemTransfer(@PathVariable Long id) {
		try {
			service.pickupItemTransfer(id);
		} catch (Exception e) {
			return JsonResponseUtil.error(null, e.getMessage());
		}
		return JsonResponseUtil.success(null);
	}
	
	@GetMapping("/movement")
	public ResponseEntity<BaseResponse<Page<ItemMovementModel>>> listItemMove(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "20") int limit) {
		Page<ItemMovementModel> data = service.listItemMove(page, limit);
		return JsonResponseUtil.success(data);
	}
}
