package com.iprivado.ms_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsConsumerApplication.class, args);
    }

}
