package com.coffee.machine.controller;

import com.coffee.machine.dto.CoffeeMachineDTO;
import com.coffee.machine.service.CoffeeMachineService;
import io.swagger.v3.oas.annotations.Operation;
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
    public List<CoffeeMachineDTO> getAllMachines() {
        return service.getAllMachines();
    }

    @Operation(summary = "Добавить новую кофемашину")
    @PostMapping
    public CoffeeMachineDTO addMachine(@RequestBody CoffeeMachineDTO dto) {
        return service.addMachine(dto);
    }
}