package com.codesdream.ase.service;

import com.codesdream.ase.component.student.SubjectScore;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.message.Notification;
import com.codesdream.ase.model.parent.Exercise;
import com.codesdream.ase.model.permission.User;
import com.codesdream.ase.model.permission.UserDetail;
import com.codesdream.ase.model.student.Course;
import com.codesdream.ase.model.student.Honor;
import com.codesdream.ase.model.student.Student;
import com.codesdream.ase.model.student.StudentCourse;
import com.codesdream.ase.repository.message.NotificationRepository;
import com.codesdream.ase.repository.parent.ExerciseRepository;
import com.codesdream.ase.repository.permission.UserRepository;
import com.codesdream.ase.repository.student.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaticsService {

    @Resource
    StudentCourseRepository scRepo;

    @Resource
    UserRepository userRepository;

    @Resource
    CourseRepository courseRepository;

    @Resource
    StudentRepository studentRepository;

    @Resource
    HonorRepository honorRepository;

    @Resource
    NotificationRepository notificationRepository;

    @Resource
    ExerciseRepository exerciseRepository;


    /**
     * 根据给定排序方式查询某学生的成绩情况
     * @see SubjectScore
     * @see StudentCourse
     * @param studentId 学生id
     * @param flag 指定排序方式，0表示按学期排序，1表示按分数排序，2表示按照学分排序，否则表示不排序
     * @return
     */
    public List<SubjectScore> displayScore (int studentId, int flag){

        Sort sort;
        switch (flag){
            case 0:{
                sort = Sort.by(Sort.Direction.ASC, "term");
                break;
            }
            case 1:{
                sort = Sort.by(Sort.Direction.ASC, "score");
                break;
            }
            case 2:{
                sort = Sort.by(Sort.Direction.ASC, "credit");
                break;
            }
            default:
                sort = null;
        }
        List<StudentCourse> studentCourseList = new ArrayList<>();
        if(sort == null){
            studentCourseList = scRepo.findByStudentId(studentId);
        }else{
            studentCourseList = scRepo.findByStudentId(studentId, sort);
        }
        if(studentCourseList.isEmpty()){
            return null;
        }
        List<SubjectScore> subjectScores = new ArrayList<>();
        for(StudentCourse studentCourse : studentCourseList){

            Course course = courseRepository.findById(studentCourse.getCourseId()).get();
            SubjectScore sc = new SubjectScore();

            sc.setCredit(course.getCredit());
            sc.setFinishedDate(studentCourse.getFinishedDate());
            sc.setScore(studentCourse.getScore());
            sc.setStudentId(studentCourse.getStudentId());

            subjectScores.add(sc);
        }
        return subjectScores;
    }

    /**
     * 查询学生荣誉信息，按照荣誉创建时间排序
     * @param studentId 学生id
     * @return 荣誉列表
     */
    public List<Honor> displayHonor(int studentId){
        if(!checkStudentExistence(studentId)){
            throw new NotFoundException("No such student.");
        }
        return honorRepository.findByStudentId(studentId, Sort.by(Sort.Direction.DESC, "creationDate"));
    }

    /**
     * 显示所有公告，默认按照公告发布时间排序
     * @return 公告列表
     */
    public List<Notification> displayNotification(){
        return notificationRepository.findAllByOrderByAnnouncementDateDesc();
    }

    /**
     * 显示学生锻炼情况
     * @exception NotFoundException 如果学生id不存在则抛出此异常
     * @param studentId 学生id
     * @return 锻炼情况列表
     */
    public List<Exercise> displayExercise(int studentId){
        if(!checkStudentExistence(studentId)){
            throw new NotFoundException("No such student.");
        }
        return exerciseRepository.findByStudentId(studentId);
    }

    /**
     * 显示学生详细信息
     * @exception  NotFoundException 如果学生id不存在则抛出此异常
     * @param studentId 学生id
     * @return 学生详细信息
     */
    public UserDetail displayStudentInfo(int studentId){
        if(!checkStudentExistence(studentId)){
            throw new NotFoundException("No such student.");
        }
        User user = userRepository.findById(studentId).get();
        return user.getUserDetail();

    }

    /**
     * 私有方法查询学生id是否存在
     * @param studentId 学生id
     * @return id存在返回true，否则返回false
     */
    private boolean checkStudentExistence(int studentId){
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(!optionalStudent.isPresent()){
            return false;
        }
        return true;
    }
}