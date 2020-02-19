package com.codesdream.ase.test;

import com.codesdream.ase.component.ASESpringUtil;
import com.codesdream.ase.component.datamanager.DataExcelReader;
import com.codesdream.ase.component.datamanager.DataTable;
import com.codesdream.ase.service.IBaseInformationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BaseInformationTest {

    @Resource
    private IBaseInformationService informationService;

    @Resource
    private ASESpringUtil springUtil;

    @Test
    public void studentInfoImportTest() {
        DataTable table = springUtil.getBean(DataTable.class);
        table.importTable(new DataExcelReader("SoftInformation.xlsx"));
        informationService.studentInfoImportFromDataTable(table);
    }

    @Test
    public void baseInformationBaseTest() {
        Assert.assertTrue(informationService.checkAdministrativeDivision("广东省"));
        Assert.assertEquals(informationService.findAdministrativeDivisionByName("广东").getName(), "广东省");
        Assert.assertTrue(informationService.checkCollege("软件学院"));
        Assert.assertTrue(informationService.checkEthnic("汉族"));
        Assert.assertTrue(informationService.checkMajor("软件工程"));
        Assert.assertTrue(informationService.checkPoliticalStatus("群众"));

    }
}
