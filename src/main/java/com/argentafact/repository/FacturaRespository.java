package com.argentafact.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.argentafact.model.Factura;

@Repository
public interface FacturaRespository extends JpaRepository <Factura,Long> {
    
}
