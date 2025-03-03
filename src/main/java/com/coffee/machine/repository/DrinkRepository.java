package com.coffee.machine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.coffee.machine.entity.Drink;

/**
 * Репозиторий для управления сущностями {@link Drink}.
 */
@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
    /**
     * Найти напиток по названию.
     *
     * @param name название напитка
     * @return найденный напиток или {@code null}, если не найден
     */
    Drink findByName(String name);
}
