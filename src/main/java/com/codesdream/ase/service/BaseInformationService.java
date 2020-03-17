package com.codesdream.ase.service;

import com.codesdream.ase.component.datamanager.DataTable;
import com.codesdream.ase.exception.BaseInformationAlreadyExistException;
import com.codesdream.ase.exception.BaseInformationIllegalException;
import com.codesdream.ase.exception.BaseInformationNotExistException;
import com.codesdream.ase.model.information.*;
import com.codesdream.ase.repository.information.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Vector;

@Slf4j
@Service
public class BaseInformationService implements IBaseInformationService {

    @Resource
    private BaseAdministrativeDivisionRepository administrativeDivisionRepository;

    @Resource
    private BaseCandidateCategoryRepository candidateCategoryRepository;

    @Resource
    private BaseCollegeRepository collegeRepository;

    @Resource
    private BaseEthnicRepository ethnicRepository;

    @Resource
    private BaseMajorRepository majorRepository;

    @Resource
    private BasePoliticalStatusRepository politicalStatusRepository;

    @Resource
    private BaseStudentInfoRepository studentInfoRepository;

    @Override
    public boolean checkAdministrativeDivision(String name) {
        Optional<BaseAdministrativeDivision> administrativeDivision =
                administrativeDivisionRepository.findByName(name);
        return administrativeDivision.isPresent();
    }

    @Override
    public boolean checkCollege(String name) {
        Optional<BaseCollege> college =
                collegeRepository.findByName(name);
        return college.isPresent();
    }

    @Override
    public boolean checkEthnic(String name) {
        Optional<BaseEthnic> ethnic =
                ethnicRepository.findByName(name);
        return ethnic.isPresent();
    }

    @Override
    public boolean checkMajor(String name) {
        Optional<BaseMajor> major =
                majorRepository.findByName(name);
        return major.isPresent();
    }

    @Override
    public boolean checkPoliticalStatus(String name) {
        Optional<BasePoliticalStatus> politicalStatus =
                politicalStatusRepository.findByName(name);
        return politicalStatus.isPresent();
    }

    @Override
    public boolean checkCandidateStatus(String name) {
        Optional<BaseCandidateCategory> candidateCategory =
                candidateCategoryRepository.findByName(name);

        return false;
    }

    @Override
    public boolean checkStudentInfo(String studentId) {
        Optional<BaseStudentInfo> studentInfo =
                studentInfoRepository.findByStudentId(studentId);
        return studentInfo.isPresent();
    }

    // 查找省级行政区域
    @Override
    public BaseAdministrativeDivision findAdministrativeDivisionByName(String name) {
        Optional<BaseAdministrativeDivision> administrativeDivision =
                administrativeDivisionRepository.findByNameContainsAndParentId(name, 0);
        // 检查
        if(!administrativeDivision.isPresent()) {
            // 如果填入未知数据
            log.warn("省级行政区域: " + name + " 未在数据库中找到,将使用未知符号占位");
            administrativeDivision = administrativeDivisionRepository.findByName("未知");
            if(administrativeDivision.isPresent()) {
                return administrativeDivision.get();
            }
            else throw new BaseInformationNotExistException(BaseAdministrativeDivision.class, name);

        }
        return administrativeDivision.get();
    }

    @Override
    public BaseCollege findCollegeByName(String name) {
        Optional<BaseCollege> college =
                collegeRepository.findByName(name);
        // 检查
        if(!college.isPresent()) throw new BaseInformationNotExistException(BaseCollege.class, name);
        return college.get();
    }

    @Override
    public BaseEthnic findEthnicByName(String name) {
        Optional<BaseEthnic> ethnic =
                ethnicRepository.findByName(name);
        if(!ethnic.isPresent()) throw new BaseInformationNotExistException(BaseEthnic.class, name);
        return ethnic.get();
    }

    @Override
    public BaseMajor findMajorByName(String name) {
        Optional<BaseMajor> major =
                majorRepository.findByName(name);
        if(!major.isPresent()) throw new BaseInformationNotExistException(BaseMajor.class, name);
        return major.get();
    }

    @Override
    public BasePoliticalStatus findPoliticalStatusByName(String name) {
        Optional<BasePoliticalStatus> politicalStatus =
                politicalStatusRepository.findByName(name);
        if(!politicalStatus.isPresent())
            throw new BaseInformationNotExistException(BasePoliticalStatus.class, name);
        return politicalStatus.get();
    }

    @Override
    public BaseCandidateCategory findCandidateCategoryByName(String name) {
        Optional<BaseCandidateCategory> candidateCategory =
                candidateCategoryRepository.findByName(name);
        if(!candidateCategory.isPresent())
            throw new BaseInformationNotExistException(BaseCandidateCategory.class, name);
        return candidateCategory.get();
    }

    @Override
    public BaseStudentInfo findStudentInfoByStudentId(String studentId) {
        Optional<BaseStudentInfo> studentInfo =
                studentInfoRepository.findByStudentId(studentId);
        if(!studentInfo.isPresent())
            throw new BaseInformationNotExistException(BaseStudentInfo.class, studentId);
        return studentInfo.get();
    }

    @Override
    public void studentInfoImportFromDataTable(DataTable table) {
        Collection<Optional<Integer>> infoIndexOptional = new ArrayList<>();

        infoIndexOptional.add(table.getTitleIndex("学号"));
        infoIndexOptional.add(table.getTitleIndex("班号"));
        infoIndexOptional.add(table.getTitleIndex("姓名"));
        infoIndexOptional.add(table.getTitleIndex("性别"));
        infoIndexOptional.add(table.getTitleIndex("学院名称"));
        infoIndexOptional.add(table.getTitleIndex("专业名称"));
        infoIndexOptional.add(table.getTitleIndex("民族名称"));
        infoIndexOptional.add(table.getTitleIndex("政治面貌名称"));
        infoIndexOptional.add(table.getTitleIndex("省份名称"));


        Vector<Integer> infoIndex = new Vector<>();

        for(Optional<Integer> infoIdx : infoIndexOptional){
            if(!infoIdx.isPresent()){
                log.error("所提供的数据表不符合学生信息导入规范, 有关键数据缺失");
                throw new RuntimeException("Unfit Data Table");
            }
            else infoIndex.add(infoIdx.get());
        }

        int dataRowsSize = table.getRowsSize();

        for(int i = 0; i < dataRowsSize; i++){
            Vector<String> row = table.getRowVector(i);
            try {
                BaseStudentInfo studentInfo =
                        constructStudentInfo(row.elementAt(infoIndex.elementAt(0)),
                                row.elementAt(infoIndex.elementAt(1)),
                                row.elementAt(infoIndex.elementAt(2)),
                                row.elementAt(infoIndex.elementAt(3)),
                                row.elementAt(infoIndex.elementAt(4)),
                                row.elementAt(infoIndex.elementAt(5)),
                                row.elementAt(infoIndex.elementAt(6)),
                                row.elementAt(infoIndex.elementAt(7)),
                                row.elementAt(infoIndex.elementAt(8)));
                save(studentInfo);
            } catch (BaseInformationNotExistException e){
                String log_info = String.format("一项学生信息的某项基本信息未在数据库找到, 该项数据无效." +
                        " %s: %s",e.getClassName(), e.getValue());
                log.warn(log_info);
            } catch (BaseInformationIllegalException e){
                String log_info = String.format("一项学生信息的某项基本信息不合法, 该项数据无效." +
                        " %s: %s", e.getType(), e.getValue());
                log.warn(log_info);
            } catch (BaseInformationAlreadyExistException e){
                String log_info = String.format("一项学生信息的学号已在数据库中包含." +
                        " %s: %s",e.getClassName(), e.getValue());
                log.warn(log_info);
            }
        }
    }

    @Override
    public BaseStudentInfo constructStudentInfo(String studentId,
                                                String classId,
                                                String realName, String sex,
                                                String college,
                                                String major, String ethnic,
                                                String politicalStatus,
                                                String administrativeDivision)
    {
        // 检查
        if(!sex.equals("男") && !sex.equals("女"))
            throw new BaseInformationIllegalException(String.class, sex);
        if(classId.length() != 8)
            throw new BaseInformationIllegalException(String.class, classId);
        if(studentId.length() != 10)
            throw new BaseInformationIllegalException(String.class, studentId);
        if(realName.length() > 64)
            throw new BaseInformationIllegalException(String.class, realName);

        BaseStudentInfo studentInfo = new BaseStudentInfo();
        studentInfo.setSex(sex);
        studentInfo.setClassId(classId);
        studentInfo.setName(realName);
        studentInfo.setStudentId(studentId);
        studentInfo.setAdministrativeDivision(findAdministrativeDivisionByName(administrativeDivision));
        studentInfo.setCollege(findCollegeByName(college));
        studentInfo.setEthnic(findEthnicByName(ethnic));
        studentInfo.setMajor(findMajorByName(major));
        studentInfo.setPoliticalStatus(findPoliticalStatusByName(politicalStatus));

        return studentInfo;
    }

    @Override
    public BaseStudentInfo save(BaseStudentInfo baseStudentInfo) {
        if(baseStudentInfo.getAdministrativeDivision() == null
                || baseStudentInfo.getCollege() == null
                || baseStudentInfo.getEthnic() == null
                || baseStudentInfo.getMajor() == null
                || baseStudentInfo.getPoliticalStatus() == null
                || baseStudentInfo.getClassId().equals("")
                || baseStudentInfo.getStudentId().equals("")
                || baseStudentInfo.getName().equals(""))

            throw new BaseInformationIllegalException(
                    baseStudentInfo.getClass(),
                    "One of the Attributes IS NULL or Empty");

        // 检查学号重复
        if(checkStudentInfo(baseStudentInfo.getStudentId()))
            throw new BaseInformationAlreadyExistException(
                    baseStudentInfo.getClass(),
                    baseStudentInfo.getStudentId());



        return studentInfoRepository.save(baseStudentInfo);
    }

    @Override
    public BaseStudentInfo update(BaseStudentInfo baseStudentInfo) {
        // 更新前检查
        if(!checkStudentInfo(baseStudentInfo.getStudentId()))
            throw new BaseInformationNotExistException(BaseStudentInfo.class, baseStudentInfo.getStudentId());
        return studentInfoRepository.save(baseStudentInfo);
    }
}
