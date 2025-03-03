package com.coffee.machine.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность, представляющая ингредиент в кофемашине.
 */
@Entity
@Table(name = "ingredients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient {

    /**
     * Уникальный идентификатор ингредиента.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название ингредиента.
     */
    @Column(nullable = false, unique = true)
    private String name; // Название ингредиента

    /**
     * Количество ингредиента на складе.
     */
    @Column(nullable = false)
    private Integer quantity; // Количество на складе

    /**
     * Получить количество ингредиента.
     *
     * @return текущее количество на складе
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Установить количество ингредиента.
     *
     * @param quantity новое количество
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Получить название ингредиента.
     *
     * @return название ингредиента
     */
    public String getName() {
        return name;
    }

    /**
     * Установить название ингредиента.
     *
     * @param name новое название ингредиента
     */
    public void setName(String name) {
        this.name = name;
    }
}