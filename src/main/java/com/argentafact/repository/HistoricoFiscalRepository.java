package com.argentafact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argentafact.model.HistoricoFiscal;

@Repository
public interface HistoricoFiscalRepository extends JpaRepository<HistoricoFiscal, Long> {
}
