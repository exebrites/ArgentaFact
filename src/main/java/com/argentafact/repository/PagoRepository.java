package com.argentafact.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.argentafact.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByFacturaIdFactura(Long idFactura);
}
