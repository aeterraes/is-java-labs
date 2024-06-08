package org.example.owners;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"org.example"})
@EntityScan(basePackages = "org.example.dataaccess.entity")
@EnableJpaRepositories(basePackages = "org.example.dataaccess.repository")
public class OwnersApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnersApplication.class, args);
    }

}
