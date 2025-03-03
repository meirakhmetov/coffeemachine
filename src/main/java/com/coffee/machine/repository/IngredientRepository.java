package com.coffee.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.coffee.machine.entity.Ingredient;

/**
 * Репозиторий для управления сущностями {@link Ingredient}.
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    /**
     * Найти ингредиент по названию.
     *
     * @param name название ингредиента
     * @return найденный ингредиент или {@code null}, если не найден
     */
    Ingredient findByName(String name);
}