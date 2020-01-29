package com.codesdream.ase.repository;

import com.codesdream.ase.model.Permit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermitRepository extends CrudRepository<Permit, Long> {

}
