package com.codesdream.ase.service;

import com.codesdream.ase.component.datamanager.DataTable;
import com.codesdream.ase.model.information.*;

public interface IBaseInformationService {
    // 检查行政区域是否合法
    boolean checkAdministrativeDivision(String name);

    // 检查学院名称是否合法
    boolean checkCollege(String name);

    // 检查民族名称是否合法
    boolean checkEthnic(String name);

    // 检查专业信息是否合法
    boolean checkMajor(String name);

    // 检查政治面貌信息是否合法
    boolean checkPoliticalStatus(String name);

    // 检查考生类型是否合法
    boolean checkCandidateStatus(String name);

    // 检查学生信息是否存在
    boolean checkStudentInfo(String studentId);

    BaseAdministrativeDivision findAdministrativeDivisionByName(String name);

    BaseCollege findCollegeByName(String name);

    BaseEthnic findEthnicByName(String name);

    BaseMajor findMajorByName(String name);

    BasePoliticalStatus findPoliticalStatusByName(String name);

    BaseCandidateCategory findCandidateCategoryByName(String name);

    BaseStudentInfo findStudentInfoByStudentId(String studentId);

    // 从文件中导入学生基本信息
    void studentInfoImportFromDataTable(DataTable table);

    BaseStudentInfo constructStudentInfo(String studentId,
                                         String classId, String realName, String sex, String college, String major, String ethnic, String politicalStatus,
                                         String administrativeDivision);

    BaseStudentInfo save(BaseStudentInfo baseStudentInfo);
}
