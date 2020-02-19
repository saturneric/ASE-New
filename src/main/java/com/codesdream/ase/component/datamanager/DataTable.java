package com.codesdream.ase.component.datamanager;

import com.codesdream.ase.exception.DataIllegalTableFormatException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

// 描述一张数据表
@Component
@Scope("prototype")
public class DataTable {
    Collection<String> titleCollection = new ArrayList<>();
    Vector<DataTableRow> dataRows = new Vector<>();

    // 为表添加列及列名
    public DataTable addColTitle(String title){
        titleCollection.add(title);
        return this;
    }

    // 获得特定行的数据
    public Collection<String> getRow(int index){
        return dataRows.elementAt(index).getRow();
    }

    // 从DataReader导入特定表
    public void ImportTable(DataReader reader){
        // 从文件中读取数据
        reader.readFile();
        // 读取列信息
        titleCollection = reader.readColsTitle();

        int rowsSize = reader.getRowsSize();
        int index = 0;
        for(int i = reader.firstDataRowIndex(); i < reader.lastDataRowIndex(); i++){
            dataRows.add(new DataTableRow(index++, reader.readRow(i)));
            // 检查是否列数一致
            if(dataRows.lastElement().getColsSize() != this.getColsSize()) {
                // 清空表数据
                this.dataRows.clear();
                throw new DataIllegalTableFormatException();
            }
        }
    }

    // 为表添加行
    public void addRow(Collection<String> row){
        dataRows.add(new DataTableRow(dataRows.size() + 1, row));
    }

    // 获得表的列数
    public int getColsSize(){
        return titleCollection.size();
    }

    // 获得表的行数
    public int getRowsSize(){
        return dataRows.size();
    }
}
