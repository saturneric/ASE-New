package com.codesdream.ase.component.datamanager;

import java.util.Collection;

/**
 * 表给狗数据文件生成器接口
 */
public interface DataGenerator {
    // 读取表头信息
    void setTableTitle(Collection<String> titles);

    // 向表中写入一行数据
    void insertRow(int rowIndex, Collection<String> dataCollection);

    // 将修改保存表到文件中
    void save();
}
