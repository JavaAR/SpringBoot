package com.deepblue.punchcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(locations = {"classpath:druid-bean.xml"})
public class PunchCardApplication {

    public static void main(String[] args) {
        SpringApplication.run(PunchCardApplication.class, args);
    }

}
