package com.yuzuhard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
@EnableCaching
//@EnableElasticsearchRepositories(basePackages = "com.how2java.tmall.es")
//@EnableJpaRepositories(basePackages = {"com.how2java.tmall.dao", "com.how2java.tmall.pojo"})
public class Application {
//    static {
//        PortUtil.checkPort(6379, "Redis 服务端", true);
//        PortUtil.checkPort(9300, "ElasticSearch 服务端", true);
//        PortUtil.checkPort(5601, "Kibana 工具", true);
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}