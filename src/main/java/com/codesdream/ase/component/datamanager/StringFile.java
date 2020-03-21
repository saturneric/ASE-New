package com.codesdream.ase.component.datamanager;

import lombok.Data;

// 储存字符串标识的文件，并可以转换为json进行传输
@Data
public class StringFile {
    private String strData = null;
    private String sha1Checker = null;
    private Integer size = null;
    private String type  = "none";
}
