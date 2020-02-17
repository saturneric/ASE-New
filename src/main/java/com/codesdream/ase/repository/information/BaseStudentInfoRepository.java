package com.codesdream.ase.repository.information;

import com.codesdream.ase.model.information.BaseStudentInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseStudentInfoRepository extends CrudRepository<BaseStudentInfo, Integer> {
    // 通过学号查找
    Optional<BaseStudentInfo> findByStudentId(String student_id);

}
