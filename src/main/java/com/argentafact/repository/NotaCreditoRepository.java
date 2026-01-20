package com.argentafact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argentafact.model.NotaCredito;

@Repository
public interface NotaCreditoRepository extends JpaRepository<NotaCredito, Long> {
    
}
