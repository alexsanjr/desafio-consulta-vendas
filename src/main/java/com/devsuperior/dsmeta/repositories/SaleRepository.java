package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleSallerDTO;
import com.devsuperior.dsmeta.dto.SaleSumDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSallerDTO(obj.id, obj.date, obj.amount, obj.seller.name) " +
            "FROM Sale obj " +
            "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%',:name, '%')) " +
            "AND obj.date BETWEEN :minDate AND :maxDate")
    Page<SaleSallerDTO> searchByDateAndName(String name, LocalDate minDate, LocalDate maxDate, Pageable pageable);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSumDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller.name")
    List<SaleSumDTO> searchSummaryByDate(LocalDate minDate, LocalDate maxDate);
}
