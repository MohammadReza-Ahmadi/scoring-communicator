package com.vosouq.scoringcommunicator;

import com.vosouq.commons.annotation.VosouqApplication;
import org.springframework.boot.SpringApplication;

//@SpringBootApplication
//@EnableFeignClients
@VosouqApplication
//@Profile("dev")
//@EnableConfigurationProperties
public class CreditScoringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreditScoringApplication.class, args);
    }

}
