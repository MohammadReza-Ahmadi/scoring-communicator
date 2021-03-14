package com.vosouq.scoringcommunicator.infrastructures.configs;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mongo")
public class JongoAutoProperties implements Serializable {
    private String host = "127.0.0.1";   // main library
    private int port = 27017;            // main library
    private String cluster = "127.0.0.1"; // replica set
    private int cport = 27017;           // replica set port
    private String database;
    private String username;
    private String password;
    private boolean open = true;
}
