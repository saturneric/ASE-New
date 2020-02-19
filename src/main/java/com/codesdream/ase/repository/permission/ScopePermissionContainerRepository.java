package com.codesdream.ase.repository.permission;

import com.codesdream.ase.model.permission.ScopePermissionContainer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ScopePermissionContainerRepository extends CrudRepository<ScopePermissionContainer, Integer> {
    Optional<ScopePermissionContainer> findByName(String name);
}
