package com.t3.api.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2019/12/13.
 */
@Service
public class RedisService<T> {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public <T> boolean set(String key ,T value){

        try {

            //任意类型转换成String
            String val = beanToString(value);

            if(val==null||val.length()<=0){
                return false;
            }

            stringRedisTemplate.opsForValue().set(key,val);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    public <T> T get(String key,Class<T> clazz){
        try {
            System.out.println(stringRedisTemplate+"stringRedisTemplate");
            String value = stringRedisTemplate.opsForValue().get(key);

            return (T)stringToBean(value,clazz);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String value, Class<T> clazz) {
        if(value==null||value.length()<=0||clazz==null){
            return null;
        }

        if(clazz ==int.class ||clazz==Integer.class){
            return (T)Integer.valueOf(value);
        }
        else if(clazz==long.class||clazz==Long.class){
            return (T)Long.valueOf(value);
        }
        else if(clazz==String.class){
            return (T)value;
        }else {
            return JSON.toJavaObject(JSON.parseObject(value), clazz);
        }
    }

    /**
     *
     * @param
     * @param
     * @return String
     */
    private <T> String beanToString(T value) {

        if(value==null){
            return null;
        }
        Class <?> clazz = value.getClass();
        if(clazz==int.class||clazz==Integer.class){
            return ""+value;
        }
        else if(clazz==long.class||clazz==Long.class){
            return ""+value;
        }
        else if(clazz==String.class){
            return (String)value;
        }else {
            return JSON.toJSONString(value);
        }

    }
    public void lpush(String key, String value) {
        stringRedisTemplate.opsForList().leftPush(key, value);
    }

    public void lpop(String key) {
        stringRedisTemplate.opsForList().leftPop(key);
    }

    public <T> boolean zset(String key,String value,double score) {
       return stringRedisTemplate.opsForZSet().add(key,value,score);
    }

    public  Set<String>  zrange (String key,double score1,double score2) {

        return stringRedisTemplate.opsForZSet().rangeByScore(key, score1, score2);
    }
    public  long   zremove (String key,String val1) {

        return stringRedisTemplate.opsForZSet().remove(key, val1);
    }
    public  Set<String>   sMmmbers (String key,String val1) {

        return stringRedisTemplate.opsForSet().members(key);
    }
    public  boolean   isMmmbers (String key,String val1) {

        return stringRedisTemplate.opsForSet().isMember(key, val1);
    }

    public  long   sadd (String key,String val1) {

        return stringRedisTemplate.opsForSet().add(key, val1);
    }

    public  long   sremove (String key,String val1) {

        return stringRedisTemplate.opsForSet().remove(key, val1);
    }

    public  Set<String>   sinter (String key,String key1) {

        return stringRedisTemplate.opsForSet().intersect(key, key1);
    }

    public  Set<String>   sdiffer (String key,String key1) {

        return stringRedisTemplate.opsForSet().difference(key, key1);
    }

    public  Set<String>   sunion (String key,String key1) {

        return stringRedisTemplate.opsForSet().union(key, key1);
    }
    public  long   hSet (String key,String feild,String value) {

        stringRedisTemplate.opsForHash().put(key,feild,value);

        return 1;
    }

    public  String   hGet (String key,String feild) {
        return (String)stringRedisTemplate.opsForHash().get(key, feild);
    }

    public Map<String ,String> hGetAllFeilds (String key) {
        return(Map) stringRedisTemplate.opsForHash().entries(key);
    }

    public Set<String> hGetAllkeys (String key) {
        return(Set) stringRedisTemplate.opsForHash().keys(key);
    }

    public List<Object > hGetAllFeildsValues (String key,Collection<Object> feilds) {
        return(List<Object >) stringRedisTemplate.opsForHash().multiGet(key,feilds);
    }

    public boolean putIfNotExits (String key,String val,long time) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key,val, Duration.ofMillis(10));
    }

    public void test(String key,String val){
    }
}

