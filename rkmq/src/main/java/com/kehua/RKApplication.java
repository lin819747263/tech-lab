package com.kehua;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@EnableScheduling
@SpringBootApplication
public class RKApplication {
    public static void main( String[] args ) {
        SpringApplication.run(RKApplication.class, args);
    }
}
