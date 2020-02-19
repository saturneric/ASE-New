package com.codesdream.ase.repository.information;

import com.codesdream.ase.model.information.BaseAdministrativeDivision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseAdministrativeDivisionRepository extends CrudRepository<BaseAdministrativeDivision, Integer> {
    Optional<BaseAdministrativeDivision> findByName(String name);
    Optional<BaseAdministrativeDivision> findByNameContainsAndParentId(String name, int parentId);
}
