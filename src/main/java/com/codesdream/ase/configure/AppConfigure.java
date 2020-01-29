package com.codesdream.ase.configure;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigure {
    private String name = "全员育人";
    private String englishName = "All Staff Education";
    private String version = "0.0.1";
    private String organization = "码梦工坊";

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getOrganization() {
        return organization;
    }
}
