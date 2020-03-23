package com.codesdream.ase.repository.activity;

import com.codesdream.ase.model.activity.Period;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends CrudRepository<Period, Integer> {
}
