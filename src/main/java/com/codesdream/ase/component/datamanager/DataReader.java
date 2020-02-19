package com.codesdream.ase.component.datamanager;

import java.util.Collection;

/**
 * 表结构信息读取器接口
 */
public interface DataReader {
    // 从文件中读取数据(在使用上要求这个调用可有可无)
    void readFile();

    // 获得表头列的数目
    Collection<String> readColsTitle();

    // 读取特定序号的行的数据
    Collection<String> readRow(int row);

    // 得到数据的总行数
    int getRowsSize();

    // 得到第一数据行的序号
    int firstDataRowIndex();

    // 得到最后一行数据行的序号
    int lastDataRowIndex();

}
