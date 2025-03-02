package com.coffee.machine.controller;

import com.coffee.machine.dto.CoffeeMachineDTO;
import com.coffee.machine.entity.Drink;
import com.coffee.machine.entity.Ingredient;
import com.coffee.machine.service.CoffeeMachineService;
import com.coffee.machine.service.WorkingHoursValidator;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/machines")
public class CoffeeMachineController {

    private final CoffeeMachineService service;
    private final WorkingHoursValidator workingHoursValidator;

    public CoffeeMachineController(CoffeeMachineService service, WorkingHoursValidator workingHoursValidator) {
        this.service = service;
        this.workingHoursValidator = workingHoursValidator;
    }

    @Operation(summary = "Получить все кофемашины")
    @GetMapping
    public ResponseEntity<List<CoffeeMachineDTO>> getAllMachines() {
        if (!workingHoursValidator.isWorkingHours()) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(service.getAllMachines());
    }

    @Operation(summary = "Добавить новую кофемашину")
    @PostMapping
    public ResponseEntity<CoffeeMachineDTO> addMachine(@RequestBody CoffeeMachineDTO dto) {
        if (!workingHoursValidator.isWorkingHours()) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(service.addMachine(dto));
    }

    @Operation(summary = "Приготовить напиток")
    @PostMapping("/brew")
    public ResponseEntity<String> brewDrink(@RequestParam String drinkName) {
        if (!workingHoursValidator.isWorkingHours()) {
            return ResponseEntity.status(403).body("Машина не работает в это время");
        }
        String result = service.brewDrink(drinkName);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Добавить новый рецепт напитка")
    @PostMapping("/add-recipe")
    public ResponseEntity<String> addRecipe(@RequestParam String name, @RequestBody Map<String, Integer> recipe) {
        return ResponseEntity.ok(service.addRecipe(name, recipe));
    }

    @Operation(summary = "Получить список всех напитков")
    @GetMapping("/drinks")
    public ResponseEntity<List<Drink>> getAllDrinks() {
        return ResponseEntity.ok(service.getAllDrinks());
    }

    @Operation(summary = "Получить список ингредиентов")
    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(service.getAllIngredients());
    }

    @Operation(summary = "Получить самый популярный напиток")
    @GetMapping("/stats")
    public ResponseEntity<String> getMostPopularDrink() {
        return ResponseEntity.ok(service.getMostPopularDrink());
    }
}
