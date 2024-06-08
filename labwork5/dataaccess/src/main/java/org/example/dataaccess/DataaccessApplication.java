package org.example.dataaccess;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.example.dataaccess.repository", "org.example.dataaccess.entity"})
public class DataaccessApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataaccessApplication.class, args);
    }

}
