package com.codesdream.ase.service;

import com.codesdream.ase.model.leaves.Leave;


import java.util.Optional;
import java.util.OptionalInt;

public interface ILeavesService {
    //通过标题查找活动
    Optional<Leave> findLeaveByTitle(String title);

    //通过创建人姓名查找活动
    Optional<Leave> findLeaveByCreator(String creatorName);

    Optional<Leave>findLeaveById(int id);

    //活动
    Leave save(Leave leave);


    //请假删除
    void delete(Leave Leave);

    //请假信息更新
    Leave update(Leave Leave);
    //创建请假条目
    Leave createLeave(Leave Leave);

    //查询主要负责的活动
    //Leave findActivitiesInTheCharge(User user);

}
