package com.t3.api.config;

import com.t3.api.filter.AuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

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


    @Bean
    public StringRedisTemplate myredisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setHashKeySerializer(new Jackson2JsonRedisSerializer(Object.class));

        return template;
    }

}
