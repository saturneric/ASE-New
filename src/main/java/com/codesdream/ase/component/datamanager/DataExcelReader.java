package com.codesdream.ase.component.datamanager;


import com.codesdream.ase.exception.*;
import com.codesdream.ase.exception.notfound.DataFileNotFoundException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 读取简单表格式的Excel文件
 */
public class DataExcelReader implements DataReader {
    Workbook workbook = null;
    Sheet sheet = null;
    short colNumber = 0;
    int rowNumberNow = 0;
    String path;
    boolean hasReadFile = false;

    public DataExcelReader(String path){
        this.path = path;
        readFile();
    }

    public void readFile(){
        if(hasReadFile) return;
        try{
            FileInputStream fileInputStream = new FileInputStream(path);
            workbook = WorkbookFactory.create(fileInputStream);
            sheet = workbook.getSheetAt(0);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            throw new DataFileNotFoundException(path);
        } catch (InvalidFormatException e) {
            throw new DataInvalidFormatException(e);
        } catch (IOException e) {
            throw new DataIOException();
        }
    }

    public Collection<String> readColsTitle(){
        Row titleRow = sheet.getRow(0);
        colNumber = titleRow.getLastCellNum();
        // 表头项目个数不可为0
        if(colNumber == 0) throw new  DataIllegalTableFormatException();
        Collection<String> title = new ArrayList<>();
        for(int cellIdx = 0; cellIdx < colNumber; cellIdx++){
            title.add(readCell(titleRow.getCell(cellIdx)));
        }
        return title;
    }

    public Collection<String> readRow(int idx){
        // 检查是否获取表头数据
        if(this.colNumber == 0) readColsTitle();
        if(idx > getRowsSize()) throw new DataReaderRowIndexOutOfRangeException();
        this.rowNumberNow = idx;
        Collection<String> data = new ArrayList<>();
        Row dataRow = sheet.getRow(rowNumberNow);
        // 检查列数是否合适
        if(dataRow.getLastCellNum() > colNumber) throw new DataIllegalTableFormatException();
        for(int cellIdx = 0; cellIdx < colNumber; cellIdx++){
            data.add(readCell(dataRow.getCell(cellIdx)));
        }
        return data;
    }

    @Override
    public int getRowsSize() {
        return lastDataRowIndex() - firstDataRowIndex();
    }

    @Override
    public int firstDataRowIndex() {
        return 1;
    }

    @Override
    public int lastDataRowIndex() {
        return sheet.getLastRowNum();
    }

    public Collection<String> readRow(){
        if(rowNumberNow >= getRowsSize()) return null;
        return readRow(rowNumberNow + 1);
    }

    private String readCell(Cell cell){
        String cellValue = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue().toString();
                } else {
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            default:
        }
        return cellValue;
    }

}
