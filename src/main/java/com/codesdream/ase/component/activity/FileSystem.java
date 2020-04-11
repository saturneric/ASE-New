package com.codesdream.ase.component.activity;

import com.codesdream.ase.exception.conflict.FileNameConflict;
import com.codesdream.ase.exception.notfound.AppendixFileNotFoundException;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.AppendixFile;
import com.codesdream.ase.repository.activity.ActivityRepository;
import com.codesdream.ase.service.AppendixFileService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

@Component
public class FileSystem {

    @Resource
    private AppendixFileService appendixFileService;


    public static final int FAILED = -1;

    //默认文件系统根路径
    private static final String rootDir = "d:/temp";

    /**
     * 用于创建文件条目的辅助函数
     * @param fileName 文件名
     * @return 文件在数据库中的条目
     */
    private AppendixFile createFileTable(String fileName)
    {
        AppendixFile appendixFile = new AppendixFile();
        appendixFile.setFileName(fileName);
        appendixFile.setLastEditTime(LocalDateTime.now());
        String[] temp = fileName.split("\\.",2);
        for(String s : temp)
            System.out.println(s);
        if(temp.length == 1)
            appendixFile.setType("");
        else
            appendixFile.setType(temp[temp.length-1]);
        return appendixFile;

    }

    /**
     * 向磁盘中添加一个文件，并在数据库建立条目
     * @param data 文件的数据
     * @param fileName 文件名，包括拓展名
     * @return 成功时返回文件id，失败时返回FileSystem.FAILED
     */
    public int addFile(byte data[], String fileName) throws Exception
    {
        AppendixFile appendixFile = createFileTable(fileName);
        appendixFile = appendixFileService.save(appendixFile);
        File file = new File(rootDir,""+appendixFile.getId());
        FileOutputStream outputStream;
        if(file.exists())
            throw new FileNameConflict(
                    "file name conflict,there is a file in the directory, and is not created by this program",
                    file.getName());
        File parent = file.getParentFile();
        if(!parent.exists())
            parent.mkdirs();
        try{
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(data);
            outputStream.close();
            return appendixFile.getId();
        }
        catch (Exception e){
            appendixFileService.delete(appendixFile);
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    /**
     * 根据id获取一个磁盘中的文件
     * @param id 文件的id
     * @return 成功返回文件的InputStream，失败返回null
     */
    public InputStream getFile(int id)throws AppendixFileNotFoundException
    {
        Optional<AppendixFile> optionalAppendixFile = appendixFileService.findById(id);
        if(!optionalAppendixFile.isPresent())
            throw new AppendixFileNotFoundException(
                    "the required id does not exist in the database",id,
                    AppendixFileNotFoundException.ID_NOT_FOUND);

        AppendixFile appendixFile = appendixFileService.findById(id).get();
        File file = new File(rootDir,""+appendixFile.getId());

        if(file.exists())
        {
            try {
                InputStream inputStream = new FileInputStream(file);
                return inputStream;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new AppendixFileNotFoundException(
                        "the required id exists in the database, but the stream can not be opened",id,
                        AppendixFileNotFoundException.STREAM_FAILURE);
            }
        }
        else
            throw new AppendixFileNotFoundException(
                    "the required id exists in the database, but the file is missing",id,
                    AppendixFileNotFoundException.FILE_NOT_fOUND);
    }

    /**
     * 删除一个文件，如果该文件不存在，则不会发生操作
     * @param id 要删除文件的id
     */
    public void deleteFile(int id)
    {
        Optional<AppendixFile> optionalAppendixFile = appendixFileService.findById(id);
        if(!optionalAppendixFile.isPresent())
            return;
        AppendixFile appendixFile = appendixFileService.findById(id).get();
        File file = new File(rootDir,""+appendixFile.getId());
        if(file.exists()) {
            file.delete();
            appendixFileService.delete(appendixFile);
        }
    }

    /**
     * 根据id获取一个文件的条目，其中包含文件信息
     * @param id 要寻找条目的id
     */
    public AppendixFile getFileData(int id)
    {
        Optional<AppendixFile> optionalAppendixFile = appendixFileService.findById(id);
        if(!optionalAppendixFile.isPresent())
            throw new AppendixFileNotFoundException(
                    "the required id does not exist in the database",id,
                    AppendixFileNotFoundException.ID_NOT_FOUND);

        AppendixFile appendixFile = appendixFileService.findById(id).get();

        return appendixFile;
    }

    /**
     * 维护数据库，删去所有文件已经缺失的条目（仅用于在文件系统出现故障时的维护）
     */
   public void databaseRefresh()
   {
       for (AppendixFile appendixFile:
            appendixFileService.findAll()) {
           File file = new File(rootDir,""+appendixFile.getId());
           if(!file.exists())
               appendixFileService.delete(appendixFile);
       }
   }

    /**
     * 维护磁盘，删除指定根目录下所有不在数据库中的文件（仅用于文件系统出现故障时的维护）
     */
   public void diskRefresh()
   {
       File dir = new File(rootDir);

       if (dir.exists()) {
           if (null == dir.listFiles()) {
               return;
           }
           for(File file : dir.listFiles())
           {
               int id;
               try{
                   id = Integer.parseInt(file.getName());
                   if(!appendixFileService.findById(id).isPresent())
                       file.delete();
               }
               catch (Exception ex){
                   file.delete();
               }
           }
       }
   }

}
