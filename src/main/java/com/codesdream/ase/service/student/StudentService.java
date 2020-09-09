package com.codesdream.ase.service.student;

import com.codesdream.ase.exception.innerservererror.DataInvalidFormatException;
import com.codesdream.ase.exception.innerservererror.InvalidDataException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.file.File;
import com.codesdream.ase.model.file.Image;
import com.codesdream.ase.model.permission.UserDetail;
import com.codesdream.ase.model.student.Honor;
import com.codesdream.ase.model.student.Notification;
import com.codesdream.ase.model.student.Student;
import com.codesdream.ase.repository.student.HonorRepository;
import com.codesdream.ase.repository.student.NotificationRepository;
import com.codesdream.ase.repository.student.StudentRepository;
import com.codesdream.ase.service.activity.ActivityService;
import com.codesdream.ase.validator.GeneralValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    @Resource
    StudentRepository studentRepository;

    @Resource
    NotificationRepository notificationRepository;

    @Resource
    ActivityService activityService;

    @Resource
    HonorRepository honorRepository;

    public Notification createNotification(String title, String description, List<File> files){
        Notification notification = new Notification();

        notification.setTitle(title);
        notification.setContext(description);
        notification.setFiles(files);

        return notificationRepository.save(notification);
    }

    public boolean cancelNotification(int notificationId){
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if(notification.isPresent()){
            notificationRepository.delete(notification.get());
            return true;
        }
        else{
            throw new NotFoundException("No such notification.");
        }
    }

    public Activity attendActivity(int studentId, int activityId){
        return activityService.addMember(activityId, studentId, true);
    }

    public Honor createHonor(int studentId, String description, List<Image> images){

        Honor honor = new Honor();
        honor.setDescription(description);
        honor.setImages(images);
        honor.setLastModification(honor.getCreationDate());
        honor.setStudentId(studentId);

        return honorRepository.save(honor);
    }

    public Honor updateHonor(int honorId, String description, List<Image> images){

        Optional<Honor> optionalHonor = honorRepository.findById(honorId);
        if(!optionalHonor.isPresent()){
            throw new NotFoundException("No such honor.");
        }
        Honor honor = optionalHonor.get();
        if(!description.isEmpty()){
            honor.setDescription(description);
        }
        if(!images.isEmpty()){
            honor.setImages(images);
        }
        return honorRepository.save(honor);
    }

    public boolean updatePrivacy(int studentId, List<String> privacyList){

        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            if(student.getPrivacy().keySet().containsAll(privacyList)){
                Map<String, Boolean> privacyMap = student.getPrivacy();
                for(String privacy : privacyList){
                    privacyMap.put(privacy, true);
                }
                return true;
            }
            else {
                throw new DataInvalidFormatException("Invalid privacy with illegal phrases.");
            }
        }
        else {
            throw new NotFoundException("No such privacy.");
        }
    }

    public Student editProfile(int studentId, String telNum, Image profilePic, String email){
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if(!optionalStudent.isPresent()){
            throw new NotFoundException("No such student.");
        }
        if(!GeneralValidator.isTelNumber(telNum) && !telNum.equals("0")){
            throw new InvalidDataException("Invalid telephone number.");
        }
        Student student = optionalStudent.get();
        UserDetail userDetail = student.getUserDetail();
        if(!telNum.equals("0")){
            userDetail.setTelNum(telNum);
        }
        if(profilePic!=null){
            userDetail.setProfilePic(profilePic);
        }
        if(!email.isEmpty()){
            userDetail.setEmail(email);
        }
        return studentRepository.save(student);
    }

}
