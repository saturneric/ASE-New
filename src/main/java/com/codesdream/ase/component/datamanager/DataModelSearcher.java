package com.codesdream.ase.component.datamanager;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 根据子系统及名称查找特定的Data Model
 */
@Data
@Component
@Scope("prototype")
public class DataModelSearcher {
    public final String modelPath = "com.codesdream.ase.model.";
    private Class<?> dataModelClass = null;

    private boolean present;

    public void getDataModelClass(String subSystem, String dataModel) {
        dataModel = doCheckName(dataModel);
        try {
            dataModelClass = Class.forName(dataModelFullNameGenerator(subSystem, dataModel));
            this.setPresent(true);
        }catch (ClassNotFoundException e){
            this.setPresent(false);
        }
    }

    public Collection<String> getDataModelParamArgs() {
        if(isPresent()) {
            Collection<String> paramArgs = new ArrayList<>();
            for (Field field : dataModelClass.getDeclaredFields()) {
                paramArgs.add(field.getName());
            }
            return paramArgs;
        }
        return null;
    }

    public <T> T getDataModelInstance(Class<T> dataModelClass) {
        if(isPresent()){
            try {
                return dataModelClass.newInstance();
            } catch (IllegalAccessException e) {
                this.setPresent(false);
                return null;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            }
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
        return new String(modelPath + subSystem + "." + dataModel);
    }
}
