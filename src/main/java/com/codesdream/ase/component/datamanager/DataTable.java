package com.codesdream.ase.component.datamanager;

import com.codesdream.ase.exception.innerservererror.DataIllegalTableFormatException;
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
        if(index >= getRowsSize()) throw new IndexOutOfBoundsException();
        return dataRows.elementAt(index).getRow();
    }

    // 获得特定行的数据
    public Vector<String> getRowVector(int index){
        if(index >= getRowsSize()) throw new IndexOutOfBoundsException();
        return new Vector<>(dataRows.elementAt(index).getRow());
    }

    // 从DataReader导入特定表
    public void importTable(DataReader reader){
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

    // 查找有无相关表头项
    public Optional<Integer> getTitleIndex(String title){
        int index = 0;
        for(String dataTitle :titleCollection){
            if(dataTitle.equals(title))
                return Optional.of(index);
            index++;
        }
        return Optional.empty();
    }

    // 导出表数据
    public void exportTable(DataGenerator dataGenerator){
        dataGenerator.setTableTitle(titleCollection);
        for(DataTableRow row : dataRows){
            dataGenerator.insertRow(row.getIndex(), row.getRow());
        }
        dataGenerator.save();
    }

    // 为表添加行
    public void addRow(Collection<String> row){
        Collection<String> dataRow = new ArrayList<>(row);
        dataRows.add(new DataTableRow(dataRows.size() + 1, dataRow));
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
