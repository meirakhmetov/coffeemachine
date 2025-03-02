package com.coffee.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.coffee.machine.entity.Drink;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    Drink findByName(String name);
}
