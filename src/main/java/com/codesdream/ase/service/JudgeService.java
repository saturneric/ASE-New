package com.codesdream.ase.service;
import com.codesdream.ase.component.student.SubjectScore;
import com.codesdream.ase.model.student.Course;
import com.codesdream.ase.model.student.Student;
import com.codesdream.ase.model.student.StudentCourse;
import com.codesdream.ase.repository.student.CourseRepository;
import com.codesdream.ase.repository.student.StudentCourseRepository;
import com.codesdream.ase.repository.student.StudentRepository;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class JudgeService {

    @Resource
    StudentCourseRepository scRepo;

    @Resource
    CourseRepository courseRepository;

    @Resource
    StudentRepository students;

    /**
     * 根据给定排序方式查询某学生的成绩情况
     * @see SubjectScore
     * @see StudentCourse
     * @param studentId 学生id
     * @param term 学期
     * @param flag 指定排序方式，0表示按学期排序，1表示按分数排序，2表示按照学分排序，否则表示不排序
     * @return
     */
    public List<SubjectScore> displayScore (int studentId, int term, int flag){

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
            studentCourseList = scRepo.findByStudentIdAndTerm(studentId,term);
        }else{
            studentCourseList = scRepo.findByStudentIdAndTerm(studentId,term,sort);
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
     * 判断某学生某学期是否有学业警告
     * @param studentId 学生id
     * @param term 学期
     * @return  是否警告
     */
    public boolean judgeWarning(int studentId,int term){
        List<SubjectScore> scores= displayScore(studentId,term,1);
        double sum=0;
        for(SubjectScore ss:scores){
            if(ss.getScore()>=60.0||ss.getIsFailed()==false){
                sum+=ss.getCredit();
            }
        }
        if(sum<15.0){
            return true;
        }
        else return false;
    }
    /**
     * 判断某学生某学期选课学分是否低于20分
     * @param studentId 学生id
     * @param term 学期
     * @return  是否低于20学分
     */

    public boolean judgeLowCredit(int studentId,int term){
        List<SubjectScore> scores= displayScore(studentId,term,1);
        double sum=0;
        for(SubjectScore ss:scores){
                sum+=ss.getCredit();
        }
        if(sum<20.0){
            return true;
        }
        else return false;
    }

//    /**
//     * 获取某个学期，所有有学业预警的学生
//     * @param term  学期
//     * @return  学生列表
//     */
//    public List<Student> getWarning(int term){
//
//        List<Student> result= new ArrayList<>();
//        for (Student s:students){
//            if(judgeWarning(s.getId(),term)){
//                result.add(s);
//            }
//        }
//        return result;
//    }
//    /**
//     * 获取某个学期，所有选课低于20分的学生
//     * @param term  学期
//     * @return  学生列表
//     */
//    public List<Student> getLowCredit(int term){
//
//        List<Student> result= new ArrayList<>();
//        for (Student s:students){
//            if(judgeLowCredit(s.getId(),term)){
//                result.add(s);
//            }
//        }
//        return result;
//    }



}
