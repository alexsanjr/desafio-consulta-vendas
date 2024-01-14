package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSallerDTO;
import com.devsuperior.dsmeta.dto.SaleSumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleSallerDTO>> getReport(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "minDate", defaultValue = "") String minDate,
			@RequestParam(name = "maxDate", defaultValue = "") String maxDate,
			Pageable pageable) {
		Page<SaleSallerDTO> dto = service.findByNameAndDate(name, minDate, maxDate, pageable);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSumDTO>> getSummary(
			@RequestParam(name = "minDate", defaultValue = "") String minDate,
			@RequestParam(name = "maxDate", defaultValue = "") String maxDate
	) {
		List<SaleSumDTO> dto = service.findSummary(minDate, maxDate);
		return ResponseEntity.ok(dto);
	}
}
