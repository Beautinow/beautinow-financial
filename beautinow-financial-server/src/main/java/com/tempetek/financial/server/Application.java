package com.tempetek.financial.server;

import com.spring4all.swagger.EnableSwagger2Doc;
import io.seata.spring.boot.autoconfigure.SeataAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.tempetek"}, exclude = SeataAutoConfiguration.class)
@EnableFeignClients(basePackages = {("com.tempetek.dictionary"), ("com.tempetek.dearsystem")})
@EnableSwagger2Doc
@EnableDiscoveryClient
@MapperScan("com.tempetek.financial.server.mapper")
@EnableAsync
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
