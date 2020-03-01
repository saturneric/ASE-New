package com.codesdream.ase.validator;

import com.codesdream.ase.model.activity.Activity;
import javafx.beans.binding.ObjectExpression;

import java.lang.reflect.Field;

public class TestNullValueValidator {

    public static void main(String[] args){
        Activity activity = new Activity();
        TestNullValueValidator.run(activity);

    }

    static void run(Object object){
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields){
            String name = field.getName();
            name = name.substring(0,1).toUpperCase()+name.substring(1);
            String type = field.getGenericType().toString();
            System.out.println("name: " + name);
            System.out.println("Type: " + type);
            System.out.println();
        }
    }
}
