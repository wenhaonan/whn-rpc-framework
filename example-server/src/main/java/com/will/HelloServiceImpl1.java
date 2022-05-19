package com.will;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author haonan.wen
 * @createTime 2022/5/19 下午3:18
 */
public class HelloServiceImpl1 implements HelloService1{
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(Hello hello) {
        logger.info("HelloServiceImpl1收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        logger.info("HelloServiceImpl1返回: {}.", result);
        return result;
    }
}
