package com.codesdream.ase.repository.permission;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FunctionalPermissionContainerRepository extends CrudRepository<FunctionalPermissionContainer, Integer> {
    Optional<FunctionalPermissionContainer> findByName(String name);
}
