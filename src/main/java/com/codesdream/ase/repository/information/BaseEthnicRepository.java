package com.codesdream.ase.repository.information;

import com.codesdream.ase.model.information.BaseEthnic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseEthnicRepository extends CrudRepository<BaseEthnic, Integer> {
    Optional<BaseEthnic> findByName(String name);
}
