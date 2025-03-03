package com.coffee.machine.dto;

import lombok.*;

/**
 * DTO (Data Transfer Object) для передачи данных о кофемашине.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CoffeeMachineDTO {
    /**
     * Модель кофемашины.
     */
    private String model;
    /**
     * Статус работы кофемашины (true - работает, false - не работает).
     */
    private Boolean isWorking;

    /**
     * Конструктор с параметрами.
     *
     * @param model    модель кофемашины
     * @param isWorking статус работы (true - работает, false - не работает)
     */
    public CoffeeMachineDTO(String model, Boolean isWorking) {
        this.model = model;
        this.isWorking = isWorking;
    }
    /**
     * Getter.
     */
    public String getModel() {
        return model;
    }
    /**
     * Getter.
     */
    public Boolean getIsWorking() {
        return isWorking;
    }


}

