package com.coffee.machine.repository;

import com.coffee.machine.entity.CoffeeMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для управления сущностями {@link CoffeeMachine}.
 */
@Repository
public interface CoffeeMachineRepository extends JpaRepository<CoffeeMachine, Long> {
}
