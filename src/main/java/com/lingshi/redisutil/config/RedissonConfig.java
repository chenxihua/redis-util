package com.lingshi.redisutil.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName: RedissonConfig
 * @Create By: chenxihua
 * @Author: Administrator
 * @Date: 2020/3/1 16:39
 **/
@Configuration
public class RedissonConfig {

    private final static Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    @Value("${spring.redis.host}")
    private String addressUrl;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedissonClient getRedisson() throws Exception{
        String url = "redis://"+addressUrl+":"+port;
        RedissonClient redisson = null;
        Config config = new Config();
        config.useSingleServer()
                .setAddress(url)
                .setDatabase(0)
                .setPassword(password);
        redisson = Redisson.create(config);

        logger.info("输出: {}", redisson.getConfig().toJSON().toString());
        return redisson;
    }


}
