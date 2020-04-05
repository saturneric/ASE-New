package com.codesdream.ase.repository.permission;

import com.codesdream.ase.model.permission.Function;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FunctionRepository extends CrudRepository<Function, Integer> {
    Optional<Function> findByName(String name);
}
