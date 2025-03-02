package com.coffee.machine.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CoffeeMachineDTO {
    private String model;
    private Boolean isWorking;

    public String getModel() {
        return model;
    }

    public Boolean getIsWorking() {
        return isWorking;
    }

    public CoffeeMachineDTO(String model, Boolean isWorking) {
        this.model = model;
        this.isWorking = isWorking;
    }
}

