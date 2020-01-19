package com.t3.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Administrator on 2019/12/30.
 */
@SpringBootApplication
@EnableZuulProxy
public class ApiAppilcation {
    public static void main(String[] args) {
        SpringApplication.run(ApiAppilcation.class, args);
    }
}
