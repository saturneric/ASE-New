package com.codesdream.ase.repository;

import com.codesdream.ase.model.permission.ScopePermissionContainer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ScopePermissionRepository extends CrudRepository<ScopePermissionContainer, Integer> {
    Optional<ScopePermissionContainer> findByName(String name);
}
