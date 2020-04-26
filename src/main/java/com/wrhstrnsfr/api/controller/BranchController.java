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
import com.wrhstrnsfr.api.model.BranchModel;
import com.wrhstrnsfr.api.param.BranchParam;
import com.wrhstrnsfr.api.service.BranchService;

@RestController
@RequestMapping("/api/branch")
public class BranchController {
	
	@Autowired
	private BranchService service;
	
	@PostMapping("/")
	public ResponseEntity<BaseResponse<Object>> save(@RequestBody BranchParam param, BindingResult result) {
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
	public ResponseEntity<BaseResponse<Page<BranchModel>>> list(@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String q) {	
		Page<BranchModel> data = service.findAll(page, limit, q);
		return JsonResponseUtil.success(data);
	}
	
	@GetMapping("/all")
	public ResponseEntity<BaseResponse<List<BranchModel>>> all(@RequestParam(defaultValue = "") String q) {	
		List<BranchModel> data = service.findSearch(q);
		return JsonResponseUtil.success(data);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BaseResponse<BranchModel>> getDetail(@PathVariable Long id) {
		BranchModel data = service.findById(id);
		if (data == null) {
			return JsonResponseUtil.error(null, "Data not found.");
		}
		return JsonResponseUtil.success(data);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<BaseResponse<Object>> update(@PathVariable Long id, @Valid @RequestBody BranchParam param, BindingResult result) {
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
