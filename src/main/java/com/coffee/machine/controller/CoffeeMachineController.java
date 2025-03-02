package com.coffee.machine.controller;

import com.coffee.machine.dto.CoffeeMachineDTO;
import com.coffee.machine.service.CoffeeMachineService;
import com.coffee.machine.util.WorkingHoursValidator;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
public class CoffeeMachineController {

    private final CoffeeMachineService service;

    public CoffeeMachineController(CoffeeMachineService service) {
        this.service = service;
    }

    @Operation(summary = "Получить все кофемашины")
    @GetMapping
    public ResponseEntity<List<CoffeeMachineDTO>>  getAllMachines() {
        if (!WorkingHoursValidator.isWorkingHours()) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(service.getAllMachines());
    }

    @Operation(summary = "Добавить новую кофемашину")
    @PostMapping
    public ResponseEntity<CoffeeMachineDTO>  addMachine(@RequestBody CoffeeMachineDTO dto) {
        if (!WorkingHoursValidator.isWorkingHours()) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(service.addMachine(dto));
    }
}