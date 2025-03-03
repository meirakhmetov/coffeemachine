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

/**
 * Сервисный слой для управления кофемашинами, напитками и ингредиентами.
 */
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


    /**
     * Удаляет напиток по ID.
     *
     * @param id идентификатор напитка
     * @return сообщение о результате удаления
     */
    @Transactional
    public String deleteDrink(Long id) {
        if (!drinkRepository.existsById(id)) {
            return "Напиток с ID " + id + " не найден!";
        }
        drinkRepository.deleteById(id);
        return "Напиток с ID " + id + " удалён!";
    }

    /**
     * Удаляет ингредиент по ID.
     *
     * @param id идентификатор ингредиента
     * @return сообщение о результате удаления
     */
    @Transactional
    public String deleteIngredient(Long id) {
        if (!ingredientRepository.existsById(id)) {
            return "Ингредиент с ID " + id + " не найден!";
        }
        ingredientRepository.deleteById(id);
        return "Ингредиент с ID " + id + " удалён!";
    }

    /**
     * Получает список всех кофемашин.
     *
     * @return список DTO кофемашин
     */
    public List<CoffeeMachineDTO> getAllMachines() {
        return repository.findAll().stream()
                .map(machine -> new CoffeeMachineDTO(machine.getModel(), machine.getIsWorking()))
                .collect(Collectors.toList());
    }

    /**
     * Добавляет новую кофемашину.
     *
     * @param dto объект DTO кофемашины
     * @return созданная кофемашина
     */
    public CoffeeMachineDTO addMachine(CoffeeMachineDTO dto) {
        CoffeeMachine machine = new CoffeeMachine();
        machine.setModel(dto.getModel());
        machine.setIsWorking(dto.getIsWorking());

        CoffeeMachine saved = repository.save(machine);
        return new CoffeeMachineDTO(saved.getModel(), saved.getIsWorking());
    }

    /**
     * Приготовление напитка.
     *
     * @param drinkName название напитка
     * @return результат приготовления
     */
    @Transactional
    public String brewDrink(String drinkName) {
        if (drinkName == null || drinkName.trim().isEmpty()) {
            return "Название напитка не может быть пустым!";
        }

        // Приводим название напитка к нижнему регистру перед поиском
        Drink drink = drinkRepository.findAll()
                .stream()
                .filter(d -> d.getName().equalsIgnoreCase(drinkName.trim()))
                .findFirst()
                .orElse(null);

        if (drink == null) {
            return "Напиток \"" + drinkName + "\" не найден!";
        }

        // Проверяем наличие ингредиентов
        for (Map.Entry<String, Integer> entry : drink.getRecipe().entrySet()) {
            String ingredientName = entry.getKey();
            int requiredAmount = entry.getValue();
            Ingredient ingredient = ingredientRepository.findByName(ingredientName);

            if (ingredient == null || ingredient.getQuantity() < requiredAmount) {
                return "Недостаточно ингредиентов для \"" + drinkName + "\"!";
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
        drinkStats.put(drink.getName(), drinkStats.getOrDefault(drink.getName(), 0) + 1);

        return "Напиток \"" + drink.getName() + "\" готов!";
    }

    /**
     * Добавляет новый рецепт напитка.
     *
     * @param name   название напитка
     * @param recipe карта ингредиентов с их количеством
     * @return сообщение о результате добавления
     */
    @Transactional
    public String addRecipe(String name, Map<String, Integer> recipe) {
        if (drinkRepository.findByName(name) != null) {
            return "Напиток с таким названием уже существует!";
        }

        // Создаём новый напиток
        Drink newDrink = new Drink();
        newDrink.setName(name);
        newDrink.setRecipe(recipe);
        drinkRepository.save(newDrink);

        // Проверяем ингредиенты и добавляем их в `ingredients`, если их нет
        for (String ingredientName : recipe.keySet()) {
            Ingredient existingIngredient = ingredientRepository.findByName(ingredientName);
            if (existingIngredient == null) {
                Ingredient newIngredient = new Ingredient();
                newIngredient.setName(ingredientName);
                newIngredient.setQuantity(500); // Начальное количество
                ingredientRepository.save(newIngredient);
            }
        }

        return "Новый рецепт " + name + " добавлен!";
    }

    /**
     * Получает список всех напитков.
     *
     * @return список напитков
     */
    public List<Drink> getAllDrinks() {
        return drinkRepository.findAll();
    }

    /**
     * Получает список всех ингредиентов.
     *
     * @return список ингредиентов
     */
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    /**
     * Получает самый популярный напиток.
     *
     * @return название и количество приготовлений самого популярного напитка
     */
    public String getMostPopularDrink() {
        return drinkStats.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(entry -> "Самый популярный напиток: " + entry.getKey() + " (заказан " + entry.getValue() + " раз)")
                .orElse("Статистика пока недоступна.");
    }

    /**
     * Обновляет количество ингредиента.
     *
     * @param id          идентификатор ингредиента
     * @param newQuantity новое количество
     * @return сообщение о результате обновления
     */
    @Transactional
    public String updateIngredient(Long id, Integer newQuantity) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        if (ingredient == null) {
            return "Ингредиент с ID " + id + " не найден!";
        }

        if (newQuantity < 0) {
            return "Количество ингредиента не может быть отрицательным!";
        }

        ingredient.setQuantity(newQuantity);
        ingredientRepository.save(ingredient);
        return "Ингредиент с ID " + id + " обновлён! Новое количество: " + newQuantity;
    }
}
