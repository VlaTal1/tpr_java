package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "org.example.connector")
@SpringBootApplication
public class DealershipApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealershipApplication.class, args);
    }
}
