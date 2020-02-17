package com.codesdream.ase.component.datamanager;

import com.codesdream.ase.component.ASESpringUtil;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * DataModel对应Repository层查询器
 */
@Data
@Component
@Scope("prototype")
public class DataModelRepositorySearcher {
    @Resource
    ASESpringUtil springUtil;

    public final String repositoryPath = "com.codesdream.ase.repository.";

    private Class<?> repositoryClass = null;

    private boolean present;

    public void getDataModelRepositoryClass(String subSystem, String dataModel){
        dataModel = doCheckName(dataModel);
        try {
            this.repositoryClass = Class.forName(dataModelFullNameGenerator(subSystem, dataModel));
            this.setPresent(true);
        } catch (ClassNotFoundException e) {
            this.setPresent(false);
        }
    }

    public <T> T getDataModelRepositoryInstance() {
        // 确保可以引用
        if(isPresent()) {
            @SuppressWarnings("unchecked")
            T repository = (T) springUtil.getBean(repositoryClass);
            return repository;
        }
        return null;
    }


    public static String doCheckName(String string) {
        char[] charArray = string.toCharArray();
        if(Character.isLowerCase(charArray[0])) charArray[0] -= 32;
        else return string;
        return String.valueOf(charArray);
    }

    private String dataModelFullNameGenerator(String subSystem, String dataModel){
        return new String(repositoryPath + subSystem + "." + dataModel + "Repository");
    }

}
