package com.codesdream.ase.service;

import com.codesdream.ase.model.Permit;
import com.codesdream.ase.repository.PermitRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PermitService implements IPermitService {

    @Autowired
    PermitRepository permitRepository;

    @Override
    public List<Permit> findAll() {
        return (List<Permit>) permitRepository.findAll();
    }
}
