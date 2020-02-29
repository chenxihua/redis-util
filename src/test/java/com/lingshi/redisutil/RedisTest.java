package com.lingshi.redisutil;

import com.lingshi.redisutil.bean.User;
import com.lingshi.redisutil.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * @ClassName: RedisTest
 * @Create By: chenxihua
 * @Author: Administrator
 * @Date: 2020/2/29 14:33
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    private final static Logger logger = LoggerFactory.getLogger(RedisTest.class);

    @Autowired
    RedisUtil redisUtil;


    @Test
    public void testRedis(){
        Jedis jedis = new Jedis("192.168.244.130", 6379);
        jedis.auth("123456");
        jedis.set("zhong", "my laopo");
        String zhong = jedis.get("zhong");
        System.out.println("==> "+zhong);
    }



    //   -----------------  测试字符串  -------------------

    @Test
    public void testAdd(){
        boolean set = redisUtil.set("djaifan1jign", "zhongchuying");
        logger.info("{}", set);
    }

    @Test
    public void testAddTime(){
        boolean set = redisUtil.set("chenxihua", "chenxihua", 1000);
        logger.info("输出：{}", set);
    }

    @Test
    public void testGetStr(){
        long aa = redisUtil.getExpire("djaifan1jign");
        String bb = (String)redisUtil.get("djaifan1jign");

        long chenxihua = redisUtil.getExpire("chenxihua");
        String chenxihua1 = (String)redisUtil.get("chenxihua");
        logger.info("{}, {}, {}, {}", chenxihua, chenxihua1, aa, bb);
    }



    //  -------------------  测试 Map  ----------------------

    @Test
    public void testPullMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("1", 11);
        map.put("2", 222);
        map.put("3", 3333);
        boolean hmset = redisUtil.hmset("123", map);
        logger.info("输出： {}", hmset);
    }

    @Test
    public void testPullMapTime(){
        Map<String, Object> result = new HashMap<>();
        result.put("11", new User(12, "12", "12"));
        result.put("22", new User(13, "13", "13"));
        result.put("33", new User(14, "14", "14"));
        boolean hmset = redisUtil.hmset("userdata", result, 1000);
        logger.info("输出： {}", hmset);
    }

    /**
     * 往已经存在的 Hash 中继续存放一个 key-value
     */
    @Test
    public void testPullMapTimeAdd(){
        boolean userdata = redisUtil.hset("userMap", "555", new User(555, "555", "555"));
        logger.info("输出： {}", userdata);
    }

    @Test
    public void testMapGet(){
        Map<Object, Object> userdata = redisUtil.hmget("userdata");
        logger.info("{}", userdata);

        Object userMap = redisUtil.hget("userMap", "555");
        logger.info("{}", userMap);
    }

    /**
     * 删除 map 中的某一个元素， 其中，item 是值map中的key
     */
    @Test
    public void testMapDel(){
        redisUtil.hdel("userMap", "555");
    }



    //     -----------------  测试 Set  --------------

    @Test
    public void testAddSet(){
        long sSet = redisUtil.sSet("strKey", "chenxihua", "zhongchuying", "ling nan shi fan", "sheng huo");
        logger.info("{}", sSet);
    }

    @Test
    public void testAddSet2(){
//        long sSet = redisUtil.sSet("strKey", "chenxihua", "chenxihua", "mei, nu", "好酒好诗");
//        logger.info("{}", sSet);

        boolean b = redisUtil.sHasKey("strKey", "chenxihua");
        boolean bb = redisUtil.sHasKey("strKey", "miiii");
        logger.info("{}, {}", b, bb);
    }

    @Test
    public void testSetOther(){
        // 获取所有键值
        Set<Object> strKey = redisUtil.sGet("strKey");
        logger.info("1: {}", strKey);

        // 判断Set的长度
        long strKey1 = redisUtil.sGetSetSize("strKey");
        logger.info("{}", strKey1);

        // 移除某个值
        long setRemove = redisUtil.setRemove("strKey", "chenxihua");
        logger.info("{}", setRemove);
    }



    //    -----------------------     测试 List  ------------------------

    @Test
    public void testAddList(){
        // 添加 List
        List<User> userList = new ArrayList<>();
        userList.add(new User(135, "ajiganklg", "超过麻将馆"));
        userList.add(new User(111, "更改发", "difa"));
        userList.add(new User(222, "agiang", "jigangajg"));
        userList.add(new User(674, "jang大哥，的感几个", "jiijijij"));
        boolean listKey = redisUtil.lSet("listKey", userList);


        // 获取长度
        long lGetListSize = redisUtil.lGetListSize("listKey");
        logger.info("2: {}", lGetListSize);

        // 在 List 的基础上，继续添加
        boolean bb = redisUtil.lSet("listKey", new User(999, "999", "99999999"));
        logger.info("3: {}", bb);

        // 获取所有 List 内容
        List<Object> objectList = redisUtil.lGet("listKey", 0, -1);
        logger.info("1: {}", objectList);

        // 根据索引修改list中的某条数据
        boolean lUpdateIndex = redisUtil.lUpdateIndex("listKey", 1L, new User(8888, "8811188", "生意兴隆"));
        logger.info("4: {}", lUpdateIndex);

        // 通过索引 获取list中的值
        Object listKey1 = redisUtil.lGetIndex("listKey", 1L);
        logger.info("5: {}", listKey1);
    }


}
