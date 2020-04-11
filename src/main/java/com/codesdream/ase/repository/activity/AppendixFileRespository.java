package com.codesdream.ase.repository.activity;

import com.codesdream.ase.model.activity.AppendixFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppendixFileRespository extends CrudRepository<AppendixFile, Integer> {

}
