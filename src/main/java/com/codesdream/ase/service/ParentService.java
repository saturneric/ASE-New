package com.codesdream.ase.service;

import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.message.Notification;
import com.codesdream.ase.model.parent.Parent;
import com.codesdream.ase.model.student.Student;
import com.codesdream.ase.repository.message.MessageRepository;
import com.codesdream.ase.repository.parent.ParentRepository;
import com.codesdream.ase.repository.student.StudentRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ParentService {

    @Resource
    ParentRepository parentRepository;

    @Resource
    StudentRepository studentRepository;

    @Resource
    MessageRepository messageRepository;

    /**
     * 用于创建一个家长账号
     * @param studentId 学生ID
     * @param parentId 家长id，一般为家长的电话
     * @param parentName 家长姓名
     * @return 创建的家长实体
     */
    public Parent createParent(int studentId, int parentId, String parentName){

           Parent parent=new Parent();
           parent.setUsername(parentName);
           parent.setId(parentId);
           parent.setStudentId(studentId);
           return parent;
    }

    /**
     * 获取某个家长的孩子
     * @param parentId 家长ID
     * @param
     * @return 这个家长的孩子
     */
    public Student getStudent(int parentId){

        Optional<Student> s0=studentRepository.findByParentId(parentId);
        if(!s0.isPresent()){
            throw new NotFoundException("No such student.");
        }
        return s0.get();
    }
    /**
     * （用于绑定家长与学生账号）家长输入学生学号与姓名，若都正确，返回真。
     * @param studentId 学生ID
     * @param studentName 姓名
     * @return 学号与姓名是否一致
     */
    public boolean startIdentify(int studentId, String studentName){
        Optional<Student> s0=studentRepository.findById(studentId);
        if(!s0.isPresent()){
            throw new NotFoundException("No such student.");
        }
        Student student = s0.get();
        return student.getUsername().equals(studentName);
    }





}
