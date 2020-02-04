package com.codesdream.ase.configure;

import org.springframework.stereotype.Component;

/**
 * 应用程序常用配置信息
 * 用于常见的应用程序本身的相关信息的引用
 */
@Component
public class AppConfigure {
    /**
     * 获得应用程序的中文名
     * @return 返回包含完整内容的字符串
     */
    public String getName() {
        return "全员育人";
    }

    /**
     * 获得应用程序的版本号
     * @return 返回版本号内容的字符串
     */
    public String getVersion() {
        return "0.0.1_200204";
    }

    /**
     * 获得应用程序的英文名
     * @return 返回包含完整内容的字符串
     */
    public String getEnglishName() {
        return "All Staff Education";
    }

    /**
     * 获得开发小组的名称
     * @return 包含完整内容的字符串
     */
    public String getOrganization() {
        return "全员育人WEB端开发组";
    }
}
