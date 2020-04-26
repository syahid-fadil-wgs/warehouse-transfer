package com.wrhstrnsfr.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wrhstrnsfr.api.response.BaseResponse;
import com.wrhstrnsfr.api.response.JsonResponseUtil;
import com.wrhstrnsfr.api.model.WarehouseModel;
import com.wrhstrnsfr.api.param.WarehouseParam;
import com.wrhstrnsfr.api.service.WarehouseService;

@RestController
@RequestMapping("/api/Warehouse")
public class WarehouseController {
	
	@Autowired
	private WarehouseService service;
	
	@PostMapping("/")
	public ResponseEntity<BaseResponse<Object>> save(@RequestBody WarehouseParam param, BindingResult result) {
		if (result.hasErrors()) {
			return JsonResponseUtil.error(result.getFieldErrors());
		}
		try {
			service.save(param);
		} catch (Exception e) {
			return JsonResponseUtil.error(null, e.getMessage());
		}
		return JsonResponseUtil.success(null);
	}
	
	@GetMapping("/list")
	public ResponseEntity<BaseResponse<Page<WarehouseModel>>> list(@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String q) {	
		Page<WarehouseModel> data = service.findAll(page, limit, q);
		return JsonResponseUtil.success(data);
	}
	
	@GetMapping("/branch/{id}")
	public ResponseEntity<BaseResponse<List<WarehouseModel>>> byBranch(@PathVariable Long id) {	
		List<WarehouseModel> data = service.findByBranch(id);
		return JsonResponseUtil.success(data);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<WarehouseModel>> getDetail(@PathVariable Long id) {
		WarehouseModel data = service.findById(id);
		if (data == null) {
			return JsonResponseUtil.error(null, "Data not found.");
		}
		return JsonResponseUtil.success(data);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BaseResponse<Object>> update(@PathVariable Long id, @Valid @RequestBody WarehouseParam param, BindingResult result) {
		if (result.hasErrors()) {
			return JsonResponseUtil.error(result.getFieldErrors());
		}
		try {
			service.update(id, param);
		} catch (Exception e) {
			return JsonResponseUtil.error(null, e.getMessage());
		}
		return JsonResponseUtil.success(null);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BaseResponse<Object>> delete(@PathVariable Long id) {
		try {
			service.delete(id);
		} catch (Exception e) {
			return JsonResponseUtil.error(null, e.getMessage());
		}
		return JsonResponseUtil.success(null);
	}

	@DeleteMapping("/undelete/{id}")
	public ResponseEntity<BaseResponse<Object>> undelete(@PathVariable Long id) {
		try {
			service.undelete(id);
		} catch (Exception e) {
			return JsonResponseUtil.error(null, e.getMessage());
		}
		return JsonResponseUtil.success(null);
	}
	
}
