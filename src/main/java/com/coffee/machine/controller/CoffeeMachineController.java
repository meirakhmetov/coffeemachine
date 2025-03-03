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

/**
 * REST-контроллер для управления кофемашинами и напитками.
 */
@RestController
@RequestMapping("/api/machines")
public class CoffeeMachineController {

    private final CoffeeMachineService service;
    private final WorkingHoursValidator workingHoursValidator;

    public CoffeeMachineController(CoffeeMachineService service, WorkingHoursValidator workingHoursValidator) {
        this.service = service;
        this.workingHoursValidator = workingHoursValidator;
    }

    /**
     * Получить список всех кофемашин.
     *
     * @return список кофемашин
     */
    @Operation(summary = "Получить все кофемашины")
    @GetMapping
    public ResponseEntity<List<CoffeeMachineDTO>> getAllMachines() {
        if (!workingHoursValidator.isWorkingHours()) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(service.getAllMachines());
    }

    /**
     * Добавить новую кофемашину.
     *
     * @param dto данные кофемашины
     * @return созданная кофемашина
     */
    @Operation(summary = "Добавить новую кофемашину")
    @PostMapping
    public ResponseEntity<CoffeeMachineDTO> addMachine(@RequestBody CoffeeMachineDTO dto) {
        if (!workingHoursValidator.isWorkingHours()) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(service.addMachine(dto));
    }

    /**
     * Приготовить напиток.
     *
     * @param drinkName название напитка
     * @return результат приготовления напитка
     */
    @Operation(summary = "Приготовить напиток")
    @PostMapping("/brew")
    public ResponseEntity<String> brewDrink(@RequestParam String drinkName) {
        if (!workingHoursValidator.isWorkingHours()) {
            return ResponseEntity.status(403).body("Машина не работает в это время");
        }
        String result = service.brewDrink(drinkName);
        return ResponseEntity.ok(result);
    }

    /**
     * Добавить новый рецепт напитка.
     *
     * @param name   название напитка
     * @param recipe ингредиенты и их количество
     * @return результат добавления рецепта
     */
    @Operation(summary = "Добавить новый рецепт напитка")
    @PostMapping("/add-recipe")
    public ResponseEntity<String> addRecipe(@RequestParam String name, @RequestBody Map<String, Integer> recipe) {
        return ResponseEntity.ok(service.addRecipe(name, recipe));
    }

    /**
     * Получить список всех напитков.
     *
     * @return список напитков
     */
    @Operation(summary = "Получить список всех напитков")
    @GetMapping("/drinks")
    public ResponseEntity<List<Drink>> getAllDrinks() {
        return ResponseEntity.ok(service.getAllDrinks());
    }

    /**
     * Получить список всех ингредиентов.
     *
     * @return список ингредиентов
     */
    @Operation(summary = "Получить список ингредиентов")
    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ResponseEntity.ok(service.getAllIngredients());
    }

    /**
     * Получить самый популярный напиток.
     *
     * @return название самого популярного напитка
     */
    @Operation(summary = "Получить самый популярный напиток")
    @GetMapping("/stats")
    public ResponseEntity<String> getMostPopularDrink() {
        return ResponseEntity.ok(service.getMostPopularDrink());
    }

    /**
     * Удалить напиток по ID.
     *
     * @param id идентификатор напитка
     * @return результат удаления
     */
    @Operation(summary = "Удалить напиток по ID")
    @DeleteMapping("/drink/{id}")
    public ResponseEntity<String> deleteDrink(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteDrink(id));
    }

    /**
     * Удалить ингредиент по ID.
     *
     * @param id идентификатор ингредиента
     * @return результат удаления
     */
    @Operation(summary = "Удалить ингредиент по ID")
    @DeleteMapping("/ingredient/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteIngredient(id));
    }

    /**
     * Обновить количество ингредиента по ID.
     *
     * @param id       идентификатор ингредиента
     * @param quantity новое количество
     * @return результат обновления
     */
    @Operation(summary = "Обновить количество ингредиента по ID")
    @PutMapping("/ingredient/{id}")
    public ResponseEntity<String> updateIngredient(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(service.updateIngredient(id, quantity));
    }
}
