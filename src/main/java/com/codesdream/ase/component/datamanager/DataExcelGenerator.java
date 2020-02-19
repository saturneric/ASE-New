package com.codesdream.ase.component.datamanager;

import com.codesdream.ase.exception.DataFileNotFoundException;
import com.codesdream.ase.exception.DataIOException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 利用数据生成Excel文件
 */
public class DataExcelGenerator implements DataGenerator {
    Workbook workbook = new XSSFWorkbook();

    Sheet sheet;
    Integer colNumber;
    String path;

    public DataExcelGenerator(String path) {
        sheet = workbook.createSheet("Data");
        this.path = path;
    }

    public void setTableTitle(Collection<String> titles){
        Row sheetRow = sheet.createRow(0);
        int idx = 0;
        for(String title : titles){
            sheetRow.createCell(idx).setCellValue(title);
            idx++;
        }
        colNumber = titles.size();
    }

    public void insertRow(int rowIndex, Collection<String> dataCollection){
        Row row = sheet.createRow(rowIndex);
        int idx = 0;
        for(String data : dataCollection){
            // 限制表头与表体的数据数目
            if(idx >= colNumber) break;
            row.createCell(idx).setCellValue(data);
            idx++;
        }
    }

    public void insertRow(Collection<String> dataCollection){
        insertRow(sheet.getLastRowNum() + 1, dataCollection);

    }

    public void insertRowDataALL(Collection<String> dataCollections){
        int cellIdx = 0;
        Collection<String> dataCollection = new ArrayList<>();
        for(String dataCollectionItem :dataCollections){
            dataCollection.add(dataCollectionItem);
            cellIdx++;
            if(cellIdx % colNumber == 0) {
                insertRow(dataCollection);
                dataCollection.clear();
                cellIdx = 0;
            }

        }
    }


    public void save() {
        try {
            FileOutputStream stream = new FileOutputStream(path, false);
            workbook.write(stream);
            stream.close();
        } catch (FileNotFoundException e) {
            throw new DataFileNotFoundException(path);
        } catch (IOException e) {
            throw new DataIOException();
        }
    }
}
