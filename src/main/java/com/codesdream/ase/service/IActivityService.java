package com.codesdream.ase.service;

import com.codesdream.ase.model.activity.Activity;
import com.codesdream.ase.model.activity.Report;

import java.util.Optional;

public interface IActivityService {
    //通过标题查找活动
    Optional<Activity> findActivityByTitle(String title);

    //通过创建人姓名查找活动
    Optional<Activity> findActivityByCreator(String creatorName);

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

}
