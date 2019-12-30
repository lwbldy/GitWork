package com.lwbldy.common.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class WebConfig {

    //设置图片验证码
    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties  = new Properties();
        //设置验证码长度是4
        properties.put("kaptcha.textproducer.char.length","4");

//        properties.put("kaptcha.border","yes");
//        properties.put("kaptcha.border.color","yes");
//        properties.put("kaptcha.textproducer.font.color","yes");
//        properties.put("kaptcha.image.width","yes");
//        properties.put("kaptcha.border","yes");
//        properties.put("kaptcha.border","yes");
//        properties.put("kaptcha.border","yes");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
