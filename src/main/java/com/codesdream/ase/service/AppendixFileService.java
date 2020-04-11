package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.AppendixFile;
import com.codesdream.ase.repository.activity.AppendixFileRespository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
public class AppendixFileService implements IAppendixFileService {

    @Resource
    private AppendixFileRespository appendixFileRespository;


    @Override
    public AppendixFile save(AppendixFile appendixFile) {
        return appendixFileRespository.save(appendixFile);
    }

    @Override
    public void delete(AppendixFile appendixFile) {
        appendixFileRespository.delete(appendixFile);
    }

    @Override
    public Optional<AppendixFile> findById(int id) {
        return appendixFileRespository.findById(id);
    }

    @Override
    public Iterable<AppendixFile> findAll() {
        return appendixFileRespository.findAll();
    }
}
