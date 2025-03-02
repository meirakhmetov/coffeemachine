package com.coffee.machine.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coffee_machines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CoffeeMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String model;

    @Column(nullable = false)
    private Boolean isWorking;

    public String getModel() {
        return model;
    }

    public Boolean getIsWorking() {
        return isWorking;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setIsWorking(Boolean working) {
        isWorking = working;
    }
}
