package com.coffee.machine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

/**
 * Сущность, представляющая напиток в кофемашине.
 */
@Entity
@Table(name = "drinks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drink {

    /**
     * Уникальный идентификатор напитка.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название напитка.
     */
    @Column(nullable = false, unique = true)
    private String name; // Название напитка

    /**
     * Рецепт напитка в виде списка ингредиентов и их количества.
     */
    @ElementCollection
    @CollectionTable(name = "drink_ingredients", joinColumns = @JoinColumn(name = "drink_id"))
    @MapKeyColumn(name = "ingredient")
    @Column(name = "amount")
    private java.util.Map<String, Integer> recipe; // Рецепт (ингредиенты + их количество)

    /**
     * Получить рецепт напитка.
     *
     * @return карта ингредиентов с их количеством
     */
    public Map<String, Integer> getRecipe() {
        return recipe;
    }

    /**
     * Получить название напитка.
     *
     * @return название напитка
     */
    public String getName() {
        return name;
    }

    /**
     * Установить название напитка.
     *
     * @param name новое название напитка
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Установить рецепт напитка.
     *
     * @param recipe карта ингредиентов с их количеством
     */
    public void setRecipe(Map<String, Integer> recipe) {
        this.recipe = recipe;
    }
}
