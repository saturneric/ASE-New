package com.codesdream.ase.repository.information;

import com.codesdream.ase.model.information.BaseCandidateCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseCandidateCategoryRepository extends CrudRepository<BaseCandidateCategory, Integer> {
    Optional<BaseCandidateCategory> findByName(String name);
}
