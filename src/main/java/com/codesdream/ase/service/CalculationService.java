package com.codesdream.ase.service;

import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.student.Course;
import com.codesdream.ase.model.student.Student;
import com.codesdream.ase.model.student.StudentCourse;
import com.codesdream.ase.repository.student.CourseRepository;
import com.codesdream.ase.repository.student.StudentCourseRepository;
import com.codesdream.ase.repository.student.StudentRepository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

public class CalculationService {
    @Resource
    StudentRepository studentRepository;

    @Resource
    StudentCourseRepository studentCourseRepository;

    @Resource
    CourseRepository courseRepository;

    /**
     * 用于计算指定学生的学分绩
     * @exception NotFoundException 课程或学生id无效
     * @param studentId 学生id
     * @return 计算好的学分绩
     */
    public float calculateGPA(int studentId){
        if(checkStudentExistence(studentId)){
            throw new NotFoundException("No such student.");
        }
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudentId(studentId);

        float sum = 0f;
        float credits = 0f;
        for (StudentCourse studentCourse : studentCourses){

            Optional<Course> optionalCourse = courseRepository.findById(studentCourse.getCourseId());
            if(!optionalCourse.isPresent()){
                throw new NotFoundException("No such course.");
            }
            Course course = optionalCourse.get();
            sum += studentCourse.getScore() * course.getCredit();
            credits += course.getCredit();
        }
        return sum / credits;
    }

    public float calculateAttendanceRate(int studentId, int term){
        //Todo
        return -1;
    }

    public float sumUpStudentAbsence (int studentId, int term){
        //Todo
        return -1;
    }

    public float calculateConcernRate(int parentId){
        //Todo
        return -1;
    }

    public float calculateGradeChange(int studentId){
        //Todo
        return -1;
    }

    /**
     * 私有方法用于判断学生id是否有效
     * @param studentId 学生id
     * @return 是否有效
     */
    private boolean checkStudentExistence(int studentId){
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(!optionalStudent.isPresent()){
            return false;
        }
        return true;
    }
}
