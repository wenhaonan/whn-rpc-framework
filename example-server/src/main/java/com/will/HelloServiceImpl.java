package com.will;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(Hello hello) {
        logger.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        logger.info("HelloServiceImpl返回: {}.", result);
        return result;
    }
}
