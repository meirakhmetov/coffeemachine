package com.coffee.machine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Map;

@Entity
@Table(name = "drinks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Название напитка

    @ElementCollection
    @CollectionTable(name = "drink_ingredients", joinColumns = @JoinColumn(name = "drink_id"))
    @MapKeyColumn(name = "ingredient")
    @Column(name = "amount")
    private java.util.Map<String, Integer> recipe; // Рецепт (ингредиенты + их количество)

    public Map<String, Integer> getRecipe() {
        return recipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecipe(Map<String, Integer> recipe) {
        this.recipe = recipe;
    }
}
