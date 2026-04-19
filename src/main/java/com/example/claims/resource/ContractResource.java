package com.example.claims.resource;

import com.example.claims.entity.Contract;
import com.example.claims.repository.ContractRepository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/contracts")
@Produces(MediaType.APPLICATION_JSON)
public class ContractResource {

    @GET
    public List<Contract> getAll() {
        ContractRepository repository = new ContractRepository();
        return repository.findAll();
    }
}