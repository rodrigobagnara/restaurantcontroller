package com.restaurantcontroller.restaurantcontroller.controller;

import com.restaurantcontroller.restaurantcontroller.dto.PingDTO;
import com.restaurantcontroller.restaurantcontroller.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
@Tag(name = "1. Health Check", description = "Operações de verificação de saúde do serviço")
public class PingController {

    @Autowired
    private PingService pingService;
    
    @GetMapping
    @Operation(summary = "Ping", description = "Retorna o status do serviço")
    public ResponseEntity<PingDTO> ping() {
        try {
            PingDTO reponse = pingService.getPing();
            return ResponseEntity.status(HttpStatus.OK).body(reponse);
        } catch (RuntimeException e) {
            System.err.println("(PingController.ping) Error: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
