package com.QQLY;

import io.everitoken.sdk.java.exceptions.ApiResponseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan(basePackages = "com.QQLY.mapper")
@ComponentScan(basePackages = {"com","org.n3r.idworker"})
public class ExpressApplication {

    public static void main(String[] args) throws ApiResponseException {
        SpringApplication.run(ExpressApplication.class, args);
    }

}
