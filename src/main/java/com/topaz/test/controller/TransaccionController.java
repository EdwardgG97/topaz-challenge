package com.topaz.test.controller;


import com.topaz.test.service.TransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/transacciones")
public class TransaccionController {

    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @PatchMapping("/{id}/reversar")
    public ResponseEntity<Void> reversar(@PathVariable Long id) {
        transaccionService.reversar(id);
        return ResponseEntity.noContent().build();
    }
}
