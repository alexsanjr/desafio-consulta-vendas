package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

import java.time.LocalDate;

public record SaleSallerDTO(Long id, LocalDate date, Double amount, String sellerName) {

    public SaleSallerDTO(Sale sale) {
        this(sale.getId(), sale.getDate(), sale.getAmount(), sale.getSeller().getName());
    }
}
