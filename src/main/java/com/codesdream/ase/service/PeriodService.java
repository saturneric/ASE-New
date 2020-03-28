package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.Period;
import com.codesdream.ase.repository.activity.PeriodRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PeriodService {

    @Resource
    PeriodRepository periodRepository;

    public Period save(Period period) {
        return periodRepository.save(period);
    }

    public void delete(Period period) {
        periodRepository.delete(period);
    }
}
