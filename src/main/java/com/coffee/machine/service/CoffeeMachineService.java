package com.coffee.machine.service;

import com.coffee.machine.dto.CoffeeMachineDTO;
import com.coffee.machine.entity.CoffeeMachine;
import com.coffee.machine.repository.CoffeeMachineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeMachineService {

    private final CoffeeMachineRepository repository;

    public CoffeeMachineService(CoffeeMachineRepository repository) {
        this.repository = repository;
    }

    public List<CoffeeMachineDTO> getAllMachines() {
        return repository.findAll().stream()
                .map(machine -> new CoffeeMachineDTO(machine.getModel(), machine.getIsWorking()))
                .collect(Collectors.toList());
    }

    public CoffeeMachineDTO addMachine(CoffeeMachineDTO dto) {
        CoffeeMachine machine = new CoffeeMachine();
        machine.setModel(dto.getModel());
        machine.setIsWorking(dto.getIsWorking());

        CoffeeMachine saved = repository.save(machine);
        return new CoffeeMachineDTO(saved.getModel(), saved.getIsWorking());
    }
}
