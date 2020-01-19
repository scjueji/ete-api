package com.t3.seat.test;

import com.SeatApplication;
import com.google.gson.Gson;
import com.t3.net.ResBean;
import com.t3.seat.controller.SeatController;
import com.t3.seat.service.RedisService;
import javafx.application.Application;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by Administrator on 2020/1/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SeatApplication.class)
public class SeatControllerTest {

    @Autowired
    private SeatController seatController;

    private MockMvc mvc;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(seatController).build();
    }

    @Test
    public void testController() throws Exception {
        //String  a=(String)  redisService.get("id",String.class);
        Gson gosn = new Gson();
        RequestBuilder builder = MockMvcRequestBuilders
                .post("/seat/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(gosn.toJson(new ResBean()));

        MvcResult result = mvc.perform(builder).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testRedis() throws Exception {
        redisTemplate.opsForValue().setIfAbsent("id1", 222);
        //when(redisTemplate.opsForValue().setIfAbsent("id1", 222)).thenReturn(true);
        doNothing().when(redisTemplate.opsForValue()).setIfAbsent("id1", 222);
        String a =(String)redisTemplate.opsForValue().get("id1");
        Assert.assertEquals(222,a);
        System.out.println(a+redisTemplate.opsForValue().setIfAbsent("id1", 222));

    }

    @Test
    public void testQueryArticle() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders
                .get("/rest/article/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        MvcResult result = mvc.perform(builder).andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testDeleteArticle() throws Exception {
        RequestBuilder builder = MockMvcRequestBuilders
                .delete("/rest/article/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_UTF8);
        MvcResult result = mvc.perform(builder).andReturn();
    }
}