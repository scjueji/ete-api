package com.t3.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2019/12/30.
 */
@ConfigurationProperties(prefix = "dh.dzp.qx")
public class PropertyConfig {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

