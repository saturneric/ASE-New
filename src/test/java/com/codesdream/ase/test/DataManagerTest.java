package com.codesdream.ase.test;

import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.component.datamanager.*;
import com.codesdream.ase.repository.permission.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 测试DataModel相关查找器
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataManagerTest {
    @Resource
    ASESpringUtil springUtil;

    // Model 查找器测试
    @Test
    public void dataModelSearcherTest() {
        DataModelSearcher dataModelSearcher = springUtil.getBean(DataModelSearcher.class);
        dataModelSearcher.getDataModelClass("permission", "Tag");
        Assert.assertTrue(dataModelSearcher.isPresent());

        for(String param : dataModelSearcher.getDataModelParamArgs()){
            System.out.println(param);
        }

    }

    // Repository 查找器测试
    @Test
    public void dataModelRepositorySearcherTest(){
        DataModelRepositorySearcher dataModelRepositorySearcher =
                springUtil.getBean(DataModelRepositorySearcher.class);

        dataModelRepositorySearcher.getDataModelRepositoryClass("permission", "User");
        Assert.assertTrue(dataModelRepositorySearcher.isPresent());
        UserRepository userRepository = dataModelRepositorySearcher.getDataModelRepositoryInstance();

    }

    // 测试Excel导出功能
    @Test
    public void dataExcelGeneratorTest(){
        DataExcelGenerator dataExcelGenerator = new DataExcelGenerator("DataExcelGeneratorTest.xlsx");

        // 设置表头信息
        Collection<String> titles = new ArrayList<>();
        titles.add("Name");
        titles.add("Sex");
        titles.add("Age");
        dataExcelGenerator.setTableTitle(titles);

        Collection<String> dataCollection = new ArrayList<>();
        dataCollection.add("Tom");
        dataCollection.add("M");
        dataCollection.add("18");

        dataExcelGenerator.insertRow(1, dataCollection);
        dataExcelGenerator.insertRow(dataCollection);

        dataCollection.add("Tom");
        dataCollection.add("M");
        dataCollection.add("18");
        dataExcelGenerator.insertRowDataALL(dataCollection);

        // 保存数据
        dataExcelGenerator.save();

    }

    // 测试Excel导入功能
    @Test
    public void dataExcelReaderTest(){
        DataExcelReader dataExcelReader = new DataExcelReader("DataExcelGeneratorTest.xlsx");

        // 从文件中读取数据
        dataExcelReader.readFile();
        // 读取表头信息
        Collection<String> title = dataExcelReader.readColsTitle();
        // 读一行取数据
        Collection<String> data = dataExcelReader.readRow();

        Iterator<String> iterator = title.iterator();
        Assert.assertEquals(iterator.next(), "Name");
        Assert.assertEquals(iterator.next(), "Sex");
        Assert.assertEquals(iterator.next(), "Age");

        iterator = data.iterator();
        Assert.assertEquals(iterator.next(), "Tom");
        Assert.assertEquals(iterator.next(), "M");
        Assert.assertEquals(iterator.next(), "18");
    }


    // Data Table 基本测试
    @Test
    public void dataTableBaseTest(){
        DataTable table = springUtil.getBean(DataTable.class);
        table.addColTitle("Name").addColTitle("Sex").addColTitle("Age");


        Collection<String> dataCollection = new ArrayList<>();
        dataCollection.add("Tom");
        dataCollection.add("M");
        dataCollection.add("18");
        table.addRow(dataCollection);
        dataCollection.clear();
        dataCollection.add("Pat");
        dataCollection.add("F");
        dataCollection.add("16");
        table.addRow(dataCollection);

        dataCollection = table.getRow(0);
        Iterator<String> iterator = dataCollection.iterator();
        Assert.assertEquals(iterator.next(), "Tom");
        Assert.assertEquals(iterator.next(), "M");
        Assert.assertEquals(iterator.next(), "18");

        dataCollection = table.getRow(1);
        iterator = dataCollection.iterator();
        Assert.assertEquals(iterator.next(), "Pat");
        Assert.assertEquals(iterator.next(), "F");
        Assert.assertEquals(iterator.next(), "16");
    }

    @Test
    public void dataTableImportTest(){
        DataTable table = springUtil.getBean(DataTable.class);
        table.importTable(new DataExcelReader("DataExcelGeneratorTest.xlsx"));
        Collection<String> dataCollection = table.getRow(0);
        Iterator<String> iterator = dataCollection.iterator();
        Assert.assertEquals(iterator.next(), "Tom");
        Assert.assertEquals(iterator.next(), "M");
        Assert.assertEquals(iterator.next(), "18");
    }

    @Test
    public void dataTableExportTest(){
        DataTable table = springUtil.getBean(DataTable.class);
        table.addColTitle("Name").addColTitle("Sex").addColTitle("Age");


        Collection<String> dataCollection = new ArrayList<>();
        dataCollection.add("Tom");
        dataCollection.add("M");
        dataCollection.add("18");
        table.addRow(dataCollection);
        dataCollection.clear();
        dataCollection.add("Pat");
        dataCollection.add("F");
        dataCollection.add("16");
        table.addRow(dataCollection);

        table.exportTable(new DataExcelGenerator("DataTableExport.xlsx"));
    }


}
