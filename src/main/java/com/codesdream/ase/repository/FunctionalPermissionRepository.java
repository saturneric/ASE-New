package com.codesdream.ase.repository;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FunctionalPermissionRepository extends CrudRepository<FunctionalPermissionContainer, Integer> {
    Optional<FunctionalPermissionContainer> findByName(String name);
}
