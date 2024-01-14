package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleSallerDTO;
import com.devsuperior.dsmeta.dto.SaleSumDTO;
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

		List<LocalDate> dates = convertToLocalDate(minDate, maxDate);

		// minDate = 0, maxDate = 1
		return repository.searchByDateAndName(name, dates.get(0), dates.get(1), pageable);
	}

	public List<SaleSumDTO> findSummary(String minDate, String maxDate) {
		List<LocalDate> dates = convertToLocalDate(minDate, maxDate);

		// minDate = 0, maxDate = 1
		return repository.searchSummaryByDate(dates.get(0), dates.get(1));
	}

	private List<LocalDate> convertToLocalDate(String minDate, String maxDate) {
		List<LocalDate> list = new ArrayList<>();
		list.add(minDate.isEmpty()
				? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L)
				: LocalDate.parse(minDate));
		list.add(maxDate.isEmpty() ? LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault())
				: LocalDate.parse(maxDate));
		return list;
	}
}
