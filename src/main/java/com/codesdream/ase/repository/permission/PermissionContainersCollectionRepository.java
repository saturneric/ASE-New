package com.codesdream.ase.repository.permission;

import com.codesdream.ase.model.permission.PermissionContainersCollection;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionContainersCollectionRepository
        extends CrudRepository<PermissionContainersCollection, Integer> {
    Optional<PermissionContainersCollection> findByName(String name);
}
