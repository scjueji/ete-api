package com.t3.seat.test.seatcase;

/**
 * Created by Administrator on 2020/1/6.
 */

import com.t3.seat.entity.SeatBean;
import com.t3.service.SeatService;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TestCase {

    @Test
    public void testSeatInterface() {
        SeatService seatService = Mockito.mock(SeatService.class);
        when(seatService.getSeatInfo("0111111")).thenReturn(new SeatBean("2222", 1).toString());

        String seatInfo = seatService.getSeatInfo("0111111");

        System.out.println(seatInfo);
        assertEquals(seatInfo, new SeatBean("2222", 1).toString());

    }

    @Test
    public void testRedis() {
        RedisTemplate redisTemplate = Mockito.mock(RedisTemplate.class);

        ValueOperations operations = Mockito.mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(operations);
        HashMap<String, Object> map = new HashMap<>();
        Answer<Object> answer = new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                System.out.println(arguments[1]);
                map.put((String) arguments[0], arguments[1]);
                return true;
            }
        };

        Answer answer1 = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                return "" + map.get((String) arguments[0]);
            }
        };
        when(redisTemplate.opsForValue().setIfAbsent(anyString(),any())).thenAnswer(answer);
        //doAnswer(answer).when(redisTemplate.opsForValue().setIfAbsent(anyString(), any()));
        redisTemplate.opsForValue().setIfAbsent("1", "zhangsn");
        redisTemplate.opsForValue().setIfAbsent("2", "lisi");
        when(redisTemplate.opsForValue().get("2")).thenAnswer(answer1);
        when(redisTemplate.opsForValue().get("1")).thenAnswer(answer1);
        String str1 = (String) redisTemplate.opsForValue().get("1");
        String str2 = (String) redisTemplate.opsForValue().get("2");
        System.out.println(str1 + str2);
        assertEquals("zhangsn", str1);

    }

    @Test
    public void testRequest() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HashMap<String, Object> map = new HashMap<>();
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                System.out.println(arguments[1]);
                map.put((String) arguments[0], arguments[1]);
                return "模拟容器添加成功" + arguments[1];
            }
        };
        Answer answer1 = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] arguments = invocationOnMock.getArguments();
                return "模拟容器添加成功" + map.get((String) arguments[0]);
            }
        };
        doAnswer(answer).when(request).setAttribute(anyString(), any());
        request.setAttribute("1", "lisi");

        when(request.getAttribute("1")).thenAnswer(answer1);
        Object attribute = request.getAttribute("1");
        System.out.println(attribute);

    }

    @Test
    public void tesDecimal() {
        BigDecimal number =new BigDecimal("1.9");
        int a=number.intValue();
        System.out.println(a);
    }
}
