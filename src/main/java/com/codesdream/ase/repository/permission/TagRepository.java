package com.codesdream.ase.repository.permission;

import com.codesdream.ase.model.permission.FunctionalPermissionContainer;
import com.codesdream.ase.model.permission.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);
}
