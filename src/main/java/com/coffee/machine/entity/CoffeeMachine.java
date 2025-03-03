package com.coffee.machine.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность, представляющая кофемашину.
 */
@Entity
@Table(name = "coffee_machines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CoffeeMachine {

    /**
     * Уникальный идентификатор кофемашины.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Модель кофемашины.
     */
    @Column(nullable = false, unique = true)
    private String model;

    /**
     * Статус работы кофемашины (true - работает, false - не работает).
     */
    @Column(nullable = false)
    private Boolean isWorking;

    /**
     * Получить модель кофемашины.
     *
     * @return модель кофемашины
     */
    public String getModel() {
        return model;
    }

    /**
     * Получить статус работы кофемашины.
     *
     * @return true, если работает, иначе false
     */
    public Boolean getIsWorking() {
        return isWorking;
    }

    /**
     * Установить модель кофемашины.
     *
     * @param model новая модель кофемашины
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Установить статус работы кофемашины.
     *
     * @param working true, если работает, иначе false
     */
    public void setIsWorking(Boolean working) {
        isWorking = working;
    }
}
