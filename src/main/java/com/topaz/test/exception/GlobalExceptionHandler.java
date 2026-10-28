package com.topaz.test.exception;

import com.topaz.test.dto.ResponseDTO;
import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.topaz.test.util.Constants.*;

@ControllerAdvice
public class GlobalExceptionHandler {



    //Exception para responser si la transacción no cumple la condición del estado Aprobada.
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ResponseDTO> handleConflictException() {
        return new ResponseEntity<>(new ResponseDTO(TRANSACTION_NOT_APPROVED.getMsg()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({OptimisticLockException.class, OptimisticLockingFailureException.class})
    public ResponseEntity<ResponseDTO> handleOptimisticLockException() {
        return new ResponseEntity<>(new ResponseDTO(TRANSACTION_ALREADY_MODIFIED.getMsg()), HttpStatus.CONFLICT);
    }

    //Exception para responser si ya pasó el plazo de 24 horas.
    @ExceptionHandler(UnprocessableException.class)
    public ResponseEntity<ResponseDTO> handleUnprocessableException() {
        return new ResponseEntity<>(new ResponseDTO(TRANSACTION_REVERSAL_WINDOW_EXPIRED.getMsg()), HttpStatus.UNPROCESSABLE_CONTENT);
    }


    //Exception para responser cuando no se encuentra la transacción
    @ExceptionHandler(TransaccionNotFoundException.class)
    public ResponseEntity<ResponseDTO> handleTransaccionNotFoundException() {
        return new ResponseEntity<>(new ResponseDTO(TRANSACTION_NOT_FOUND.getMsg()), HttpStatus.NOT_FOUND);
    }
}
