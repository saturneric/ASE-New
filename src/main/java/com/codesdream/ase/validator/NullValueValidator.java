package com.codesdream.ase.validator;

import com.codesdream.ase.model.activity.Attendance;
import com.codesdream.ase.model.activity.Period;
import com.codesdream.ase.model.activity.Report;
import com.codesdream.ase.model.permission.User;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class NullValueValidator {

    /**
     * 传入一个对象，利用Java的反射机制判断各个属性的值是否为空，并返回空值列表
     * @param object 传入的对象
     * @return 一个字符串列表，维护值为空的属性的名字
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public List<String> checkNullValues (Object object) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] fields = object.getClass().getDeclaredFields();
        List<String> result = new ArrayList<>();
        for (Field field : fields){
            String name = field.getName();
            String type = field.getGenericType().toString();
            Method method = object.getClass().getMethod("get" + name);
            name = field.getName();
            if(type.equals("class java.lang.String")){
                String str = (String) method.invoke(object);
                if(str.isEmpty()) result.add(name);
            }
            else if(type.equals("class com.codesdream.ase.model.permission.User")){
                User user = (User) method.invoke(object);
                if(user == null) result.add(name);
            }
            else if(type.equals("java.util.Set<com.codesdream.ase.model.permission.User>")){
                Set<User> users = (Set<User>) method.invoke(object);
                if(users.isEmpty()) result.add(name);
            }
            else if(type.equals("class com.codesdream.ase.model.activity.Period")){
                Period period = (Period) method.invoke(object);
                if(period == null) result.add(name);
            }
            else if(type.equals(("class java.time.LocalDateTime"))){
                LocalDateTime date = (LocalDateTime) method.invoke(object);
                if(date == null) result.add(name);
            }
            else if(type.equals("java.util.List<java.lang.String>")){
                List<String> strings = (List<String>) method.invoke(object);
                if(strings.isEmpty()) result.add(name);
            }
            else if(type.equals("boolean")){
                boolean a = (boolean) method.invoke(object);
                if(!a) result.add(name);
            }
            else if(type.equals("class com.codesdream.ase.model.activity.Attendance")){
                Attendance attendance = (Attendance) method.invoke(object);
                if(attendance == null) result.add(name);
            }
            else if(type.equals("class com.codesdream.ase.model.activity.Report")){
                Report report = (Report) method.invoke(object);
                if(report == null) result.add(name);
            }
        }
        return result;
    }
}
