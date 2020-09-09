package com.codesdream.ase.validator;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GeneralValidator {

    public static boolean isTelNumber (String telNum){
        String reg = "^[1](([3][0-9])|([4][5,7,9])|([5][^4,6,9])|([6][6])" +
                "|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(telNum);
        return m.find();
    }
}
