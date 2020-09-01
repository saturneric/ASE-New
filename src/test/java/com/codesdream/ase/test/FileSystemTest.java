package com.codesdream.ase.test;

import com.codesdream.ase.component.activity.FileSystem;
import com.codesdream.ase.exception.notfound.AppendixFileNotFoundException;
import com.codesdream.ase.model.activity.AppendixFile;
import com.codesdream.ase.repository.activity.AppendixFileRespository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.Scanner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileSystemTest {

    @Resource
    FileSystem fileSystem;

    @Resource
    AppendixFileService appendixFileService;

    @Test
    public void getPresentFilesTest()
    {
        Iterable<AppendixFile> appendixFiles = appendixFileService.findAll();
        for (AppendixFile appendixFile:
             appendixFiles) {
            System.out.println(appendixFile.getFileName()+" "+appendixFile.getId()+" "+appendixFile.getType()+
                    " "+appendixFile.getLastEditTime().toString());

        }
    }

    @Test
    public void DeleteAllFilesTest()
    {
        Iterable<AppendixFile> appendixFiles = appendixFileService.findAll();
        for (AppendixFile appendixFile:
                appendixFiles) {
           fileSystem.deleteFile(appendixFile.getId());

        }
    }

    @Test
    public void wrongIDTest()
    {
        int id = 1;
        try {
            InputStream inputStream = fileSystem.getFile(id);
        } catch (AppendixFileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test
    public void fileNotExistErrorTest()
    {
        int id = 268;
        try {
            InputStream inputStream = fileSystem.getFile(id);
        } catch (AppendixFileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Test
    public void refreshDataBaseTest()
    {
        fileSystem.databaseRefresh();
    }
    @Test
    public void refreshDiskTest()
    {
        fileSystem.diskRefresh();
    }
    @Test
    public void createFile()
    {
        int id1 = 0;
        try {
            id1 = fileSystem.addFile("asfasefasgasgasg".getBytes(),"test1.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int id2 = fileSystem.addFile("aspgjparjgpoarjgpjpeg".getBytes(),"test2.jpeg");
        } catch (Exception e) {
            e.printStackTrace();
        }


        InputStream inputStream = null;
        try {
            inputStream = fileSystem.getFile(id1);
        } catch (AppendixFileNotFoundException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String text = scanner.useDelimiter("\\A").next();
        System.out.println(text);
    }

}
