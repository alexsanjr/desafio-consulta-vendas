package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSallerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleSallerDTO> findByNameAndDate(String name, String minDate, String maxDate, Pageable pageable) {

		LocalDate minLocalDate = minDate.isEmpty()
				? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L)
				: LocalDate.parse(minDate);
		LocalDate maxLocalDate = maxDate.isEmpty() ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDate);

		return repository.searchByDateAndName(name, minLocalDate, maxLocalDate, pageable);
	}
}
