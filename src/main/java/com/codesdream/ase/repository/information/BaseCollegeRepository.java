package com.codesdream.ase.repository.information;

import com.codesdream.ase.model.information.BaseCollege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseCollegeRepository extends CrudRepository<BaseCollege, Integer> {
    Optional<BaseCollege> findByName(String name);
    Optional<BaseCollege> findByNumber(Integer number);
}
