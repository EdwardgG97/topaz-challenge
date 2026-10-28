package com.topaz.test.service;

import com.topaz.test.entity.Transaccion;
import com.topaz.test.exception.UnprocessableException;
import com.topaz.test.repository.TransaccionRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.topaz.test.util.EstadoTransaccion.APROBADA;
import static com.topaz.test.util.EstadoTransaccion.REVERSADA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TransaccionServiceTest {

    private final TransaccionRepository transaccionRepository = mock(TransaccionRepository.class);
    private final TransaccionService transaccionService = new TransaccionService(transaccionRepository);

    @Test
    void reversar_rechazaTransaccionConMasDe24Horas() {
        Transaccion transaccion = transaccionCreadaHace(25);
        when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccion));

        assertThrows(UnprocessableException.class, () -> transaccionService.reversar(1L));

        verify(transaccionRepository, never()).saveAndFlush(any());
    }

    @Test
    void reversar_persisteTransaccionDentroDeLas24Horas() {
        Transaccion transaccion = transaccionCreadaHace(23);
        when(transaccionRepository.findById(1L)).thenReturn(Optional.of(transaccion));
        when(transaccionRepository.saveAndFlush(transaccion)).thenReturn(transaccion);

        transaccionService.reversar(1L);

        assertEquals(REVERSADA, transaccion.getEstado());
        verify(transaccionRepository).saveAndFlush(transaccion);
    }

    private Transaccion transaccionCreadaHace(long horas) {
        return new Transaccion(
                1L,
                BigDecimal.TEN,
                APROBADA,
                LocalDateTime.now().minusHours(horas),
                0L
        );
    }
}
