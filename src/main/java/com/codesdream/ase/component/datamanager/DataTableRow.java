package com.codesdream.ase.component.datamanager;

import lombok.Data;

import java.util.Collection;

// 描述数据表的行
@Data
public class DataTableRow {
    int index;
    Collection<String> row;

    public DataTableRow(int index, Collection<String> row){
        this.index = index;
        this.row = row;
    }

    public int getColsSize(){
        return row.size();
    }
}
