package com.codesdream.ase.repository.information;

import com.codesdream.ase.model.information.BasePoliticalStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BasePoliticalStatusRepository extends CrudRepository<BasePoliticalStatus, Integer> {
    Optional<BasePoliticalStatus> findByName(String name);
}
