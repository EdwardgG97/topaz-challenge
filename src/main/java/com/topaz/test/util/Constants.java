package com.topaz.test.util;

import lombok.Getter;

@Getter
public enum Constants {

    TRANSACTION_NOT_APPROVED("Solo se pueden reversar transacciones en estado APROBADA"),
    TRANSACTION_ALREADY_MODIFIED("La transacción fue modificada concurrentemente"),
    TRANSACTION_REVERSAL_WINDOW_EXPIRED("La transacción supera el plazo de 24 horas para ser reversada"),
    TRANSACTION_NOT_FOUND("Transaccion no encontrada");

    private final String msg;

    Constants(String msg) {
        this.msg = msg;
    }
}
