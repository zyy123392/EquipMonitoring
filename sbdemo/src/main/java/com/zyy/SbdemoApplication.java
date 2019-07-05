package com.zyy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zyy.dao")

public class SbdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbdemoApplication.class, args);

    }


}
