package com.topaz.test.repository;

import com.topaz.test.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository  extends JpaRepository<Transaccion, Long> {


}
