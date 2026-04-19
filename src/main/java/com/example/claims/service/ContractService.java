package com.example.claims.service;

import com.example.claims.entity.Contract;
import com.example.claims.repository.ContractRepository;
import jakarta.inject.Inject;
import java.util.List;

public class ContractService {

    @Inject
    private ContractRepository repository;

    public List<Contract> getAllContracts() {
        return repository.findAll();
    }
}