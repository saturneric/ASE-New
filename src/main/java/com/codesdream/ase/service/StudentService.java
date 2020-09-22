package com.codesdream.ase.service;

import com.codesdream.ase.exception.innerservererror.DataInvalidFormatException;
import com.codesdream.ase.exception.innerservererror.InvalidDataException;
import com.codesdream.ase.exception.notfound.NotFoundException;
import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.file.Image;
import com.codesdream.ase.model.permission.UserDetail;
import com.codesdream.ase.model.student.Honor;
import com.codesdream.ase.model.student.Student;
import com.codesdream.ase.repository.student.HonorRepository;
import com.codesdream.ase.repository.student.StudentRepository;
import com.codesdream.ase.validator.GeneralValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    @Resource
    StudentRepository studentRepository;

    @Resource
    ActivityService activityService;

    @Resource
    HonorRepository honorRepository;




    /**
     * 指定学生加入指定活动
     * @see ActivityService#addMember(int, int, boolean)
     * @param studentId 加入的学生的id
     * @param activityId 加入的学生的活动
     * @return 加入学生后、持久化了的活动
     */
    public Activity attendActivity(int studentId, int activityId){
        return activityService.addMember(activityId, studentId, true);
    }

    /**
     * 创建并持久化一个荣誉
     * @see Image
     * @see Honor
     * @param studentId 荣誉所对应的学生id
     * @param description 荣誉的描述
     * @param images 荣誉的证明材料（图片）
     * @return 持久化好的荣誉
     */
    public Honor createHonor(int studentId, String description, List<Image> images){

        Honor honor = new Honor();
        honor.setDescription(description);
        honor.setImages(images);
        honor.setLastModification(honor.getCreationDate());
        honor.setStudentId(studentId);

        return honorRepository.save(honor);
    }

    /**
     * 更新一个荣誉
     * @param honorId 荣誉id
     * @param description 荣誉描述，若为空则不做修改
     * @param images 需要更新的附件，若为空则不做修改，否则会覆盖对应荣誉所有附件
     * @return 更新后的荣誉，若id不存在则返回null
     */
    public Honor updateHonor(int honorId, String description, List<Image> images){

        Optional<Honor> optionalHonor = honorRepository.findById(honorId);
        if(!optionalHonor.isPresent()){
            throw new NotFoundException("No such honor.");
        }
        Honor honor = optionalHonor.get();
        if(!description.isEmpty()){
            honor.setDescription(description);
            honor.setLastModification(new Date());
        }
        if(!images.isEmpty()){
            honor.setImages(images);
            honor.setLastModification(new Date());
        }
        return honorRepository.save(honor);
    }

    /**
     * 更新指定学生的隐私政策
     * @param studentId 学生id
     * @param privacyList 一串字符，用以标明可公开的隐私有哪些，需要保证此字符序列被["score","attendance",
     *                    "step","honor"]这一标准序列完全包含。序列有待更新@Todo
     * @return 是否更新成功。若privacyList不符合规范则更新失败，若studentId无实体与之对应则更新失败
     */
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
            throw new NotFoundException("No such student.");
        }
    }

    /**
     * 用于学生对个人信息的编辑
     * @see GeneralValidator#isTelNumber(String)
     * @param studentId 指定学生id
     * @param telNum 电话，若为0，则表示无需更改；否则需要满足电话号码的合法性
     * @param profilePic 头像，若为null，则表示无需更改
     * @param email 电子邮箱，若为空，则表示无需修改
     * @return 编辑好个人信息的学生
     */
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
