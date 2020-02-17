package com.codesdream.ase.repository.information;

import com.codesdream.ase.model.information.BaseMajor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseMajorRepository extends CrudRepository<BaseMajor, Integer> {
    Optional<BaseMajor> findByName(String name);
}
