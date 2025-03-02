package com.coffee.machine.service;

import com.coffee.machine.dto.CoffeeMachineDTO;
import com.coffee.machine.entity.CoffeeMachine;
import com.coffee.machine.entity.Drink;
import com.coffee.machine.entity.Ingredient;
import com.coffee.machine.repository.CoffeeMachineRepository;
import com.coffee.machine.repository.DrinkRepository;
import com.coffee.machine.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoffeeMachineService {

    private final CoffeeMachineRepository repository;
    private final DrinkRepository drinkRepository;
    private final IngredientRepository ingredientRepository;
    private final Map<String, Integer> drinkStats = new HashMap<>();

    public CoffeeMachineService(CoffeeMachineRepository repository, DrinkRepository drinkRepository, IngredientRepository ingredientRepository) {
        this.repository = repository;
        this.drinkRepository = drinkRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<CoffeeMachineDTO> getAllMachines() {
        return repository.findAll().stream()
                .map(machine -> new CoffeeMachineDTO(machine.getModel(), machine.getIsWorking()))
                .collect(Collectors.toList());
    }

    public CoffeeMachineDTO addMachine(CoffeeMachineDTO dto) {
        CoffeeMachine machine = new CoffeeMachine();
        machine.setModel(dto.getModel());
        machine.setIsWorking(dto.getIsWorking());

        CoffeeMachine saved = repository.save(machine);
        return new CoffeeMachineDTO(saved.getModel(), saved.getIsWorking());
    }

    @Transactional
    public String brewDrink(String drinkName) {
        Drink drink = drinkRepository.findByName(drinkName);
        if (drink == null) {
            return "Напиток не найден!";
        }

        // Проверяем наличие ингредиентов
        for (Map.Entry<String, Integer> entry : drink.getRecipe().entrySet()) {
            String ingredientName = entry.getKey();
            int requiredAmount = entry.getValue();
            Ingredient ingredient = ingredientRepository.findByName(ingredientName);

            if (ingredient == null || ingredient.getQuantity() < requiredAmount) {
                return "Недостаточно ингредиентов для " + drinkName;
            }
        }

        // Уменьшаем запасы ингредиентов
        for (Map.Entry<String, Integer> entry : drink.getRecipe().entrySet()) {
            String ingredientName = entry.getKey();
            int requiredAmount = entry.getValue();
            Ingredient ingredient = ingredientRepository.findByName(ingredientName);
            ingredient.setQuantity(ingredient.getQuantity() - requiredAmount);
            ingredientRepository.save(ingredient);
        }

        // Обновляем статистику напитков
        drinkStats.put(drinkName, drinkStats.getOrDefault(drinkName, 0) + 1);

        return "Напиток " + drinkName + " готов!";
    }

    public String addRecipe(String name, Map<String, Integer> recipe) {
        if (drinkRepository.findByName(name) != null) {
            return "Напиток с таким названием уже существует!";
        }

        Drink newDrink = new Drink();
        newDrink.setName(name);
        newDrink.setRecipe(recipe);
        drinkRepository.save(newDrink);

        return "Новый рецепт " + name + " добавлен!";
    }

    public List<Drink> getAllDrinks() {
        return drinkRepository.findAll();
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public String getMostPopularDrink() {
        return drinkStats.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> "Самый популярный напиток: " + entry.getKey() + " (заказан " + entry.getValue() + " раз)")
                .orElse("Статистика пока недоступна.");
    }
}
