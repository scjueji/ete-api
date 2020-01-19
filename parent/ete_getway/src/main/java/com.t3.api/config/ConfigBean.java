package com.t3.api.config;

import com.t3.api.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/12/30.
 */
@Configuration
public class ConfigBean {
    @Bean
    public AuthFilter initAuth(){
        return  new AuthFilter();
    }

    @Bean
    public PropertyConfig propertyConfig(){
        return  new PropertyConfig();
    }
}
