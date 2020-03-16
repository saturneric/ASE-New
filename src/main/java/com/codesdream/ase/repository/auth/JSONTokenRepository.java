package com.codesdream.ase.repository.auth;

import com.codesdream.ase.model.auth.JSONToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JSONTokenRepository extends CrudRepository<JSONToken, Integer> {
    Optional<JSONToken> findByUsername(String username);
}
