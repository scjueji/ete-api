package com.t3.api.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import com.t3.api.config.PropertyConfig;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Administrator on 2020/1/7.
 */
public class SpeedFilter extends ZuulFilter {
    @Autowired
    private PropertyConfig propertyConfig;
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return -3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        if(RATE_LIMITER.tryAcquire())return "";
        return null;
    }
}
