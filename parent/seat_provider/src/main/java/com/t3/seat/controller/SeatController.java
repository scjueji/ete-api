package com.t3.seat.controller;

import com.t3.seat.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Administrator on 2019/12/27.
 */
@RequestMapping("/seat")
@RestController
public class SeatController {

    @Autowired
    private RedisService redisService;
    @RequestMapping("/{id}")
    public String test(@PathVariable String id)  throws Exception{
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("开始了");
            }
        });

        for (int i = 0; i <10 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    boolean res =redisService.putIfNotExits("id","011203");
                    System.out.println(Thread.currentThread().getName()+"获取是否成功"+res);
                }
            },"thread"+i).start();
        }

        return id;
    }
    public String testException(Integer id){
        return "testException";
    }
}
