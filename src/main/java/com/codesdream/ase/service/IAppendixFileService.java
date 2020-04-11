package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.AppendixFile;

import java.util.Optional;

public interface IAppendixFileService {

    //存储磁盘文件条目
    AppendixFile save(AppendixFile appendixFile);

    //删除磁盘文件条目
    void delete(AppendixFile appendixFile);

    //通过ID寻找文件条目
    Optional<AppendixFile> findById(int id);

    //找到所有文件条目
    Iterable<AppendixFile> findAll();

}
