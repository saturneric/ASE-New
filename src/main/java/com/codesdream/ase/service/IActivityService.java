package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.Report;
import com.codesdream.ase.model.permission.User;

import java.util.List;
import java.util.Optional;

public interface IActivityService {
    //通过标题查找活动
    Optional<Activity> findActivityByTitle(String title);

    //通过创建人姓名查找活动
    Optional<Activity> findActivityByCreator(String creatorName);

    //通过活动类型查找活动
    List<Activity> findActivityByType(User user, String type);

    //活动持久化
    Activity save(Activity activity);

    //添加活动报告
    Activity addReport(Activity activity, Report report);

    //活动删除
    void delete(Activity activity);

    //活动信息更新
    Activity update(Activity activity);

    //活动创建
    Activity createActivity(Activity activity);

    //查询主要负责的活动
    List<Activity> findMainResponsibleActs(User user);

    //查询次要负责的活动
    List<Activity> findSecondaryResponsibleActs(User user);

    //查询所有可见活动（即可报名活动、主次要负责的活动、参与过的活动等等之外却仍然可见的活动并集）
    List<Activity> findVisibleActs(User user);

    //查找可报名的活动
    List<Activity> findSignActs(User user);

    //查询参与过的活动
    List<Activity> findParticipatedActs(User user);

    //查询将要参与的活动
    List<Activity> findParticipatingActs(User user);

    //查询创建的活动
    List<Activity> findCreatedActs(User user);

    //查询所有活动（权限内）
    List<Activity> findAll(User user);

}
