package com.lwbldy.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis 扫描类
 */
@Configuration
@MapperScan({"com.lwbldy.mbg.mapper","com.lwbldy.*.dao"})
public class MyBatisConfig {
}
