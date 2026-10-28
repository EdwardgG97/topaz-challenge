package com.topaz.test.service;

import com.topaz.test.entity.Transaccion;
import com.topaz.test.exception.ConflictException;
import com.topaz.test.exception.TransaccionNotFoundException;
import com.topaz.test.exception.UnprocessableException;
import com.topaz.test.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.topaz.test.util.EstadoTransaccion.APROBADA;
import static com.topaz.test.util.EstadoTransaccion.REVERSADA;

@Service
public class TransaccionService {

    private final TransaccionRepository transaccionRepository;

    public TransaccionService(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    @Transactional
    public void reversar(Long id) {
        Transaccion transaccion = getTransaccionById(id);
        if (transaccion.getEstado() != APROBADA) {
            throw new ConflictException();
        }

        if (!validateDate(transaccion.getFechaCreacion())) {
            throw new UnprocessableException();
        }

        transaccion.setEstado(REVERSADA);
        transaccionRepository.saveAndFlush(transaccion);
    }

    private Transaccion getTransaccionById(Long id) {
        return transaccionRepository.findById(id)
                .orElseThrow(TransaccionNotFoundException::new);

    }

    private boolean validateDate(LocalDateTime fechaCreacion) {
        LocalDateTime now = LocalDateTime.now();
        return !fechaCreacion.isBefore(now.minusHours(24)) && !fechaCreacion.isAfter(now);
    }
}
