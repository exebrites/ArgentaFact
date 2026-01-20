package com.argentafact.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.argentafact.model.Factura;
import com.argentafact.repository.FacturaRespository;

@Service
public class FacturaService {
    private final FacturaRespository facturaRespository; 

    public FacturaService (FacturaRespository facturaRespository){
        this.facturaRespository = facturaRespository;
    }
    public List<Factura> obtenerFacturas (){
        return facturaRespository.findAll();
    }
    public void guardarFactura(Factura factura){
        facturaRespository.save(factura);
    }
    public Factura obtenerFactura (Long id){
        return facturaRespository.findByIdFactura(id);
    }
}
