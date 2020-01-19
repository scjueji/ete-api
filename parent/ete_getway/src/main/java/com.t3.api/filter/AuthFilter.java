package com.t3.api.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.t3.api.config.PropertyConfig;
import com.t3.api.entity.UserBean;
import com.t3.api.mapper.UserMapper;
import com.t3.api.service.RedisService;
import com.t3.net.ResBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2019/12/30.
 */
public class AuthFilter extends ZuulFilter {

    @Autowired
    private PropertyConfig propertyConfig;
    @Autowired
    RedisService redisService;
    @Autowired
    UserMapper userMapper ;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        return  dofilter();

    }

    private Object dofilter(){
        RequestContext context = RequestContext.getCurrentContext();
        context.getResponse().setCharacterEncoding("UTF-8");
        HttpServletRequest request = context.getRequest();
        String key = request.getParameter("key");
        if (StringUtils.isEmpty(key)) {
            ResBean resBean = new ResBean();
            resBean.setCode("403");
            resBean.setMessage("没有访问权限");
            String json = JSON.toJSONString(resBean);
            System.out.println(json);
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(403);
            context.setResponseBody(json);
        }else {
            UserBean userBean = (UserBean)redisService.get(key,UserBean.class);
            if(userBean == null ){
                userBean = userMapper.selectLoginUserByKeyAuth(key,propertyConfig.getId());
                System.out.println(userBean);
                redisService.set(key,userBean);
            }

        }
        return null;
    }
}