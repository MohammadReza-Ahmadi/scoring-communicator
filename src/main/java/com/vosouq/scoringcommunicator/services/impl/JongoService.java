package com.vosouq.scoringcommunicator.services.impl;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class JongoService {
    private Jongo jongo;

    @Value("${mongodb.database.host}")
    private String host;
    @Value("${mongodb.database.port}")
    private int port;
    @Value("${mongodb.database.name}")
    private String databaseName;
    @Value("${mongodb.database.username}")
    private String username;
    @Value("${mongodb.database.password}")
    private String password;
    @Value("${mongodb.authentication-database}")
    private String authenticationDb;

    @PostConstruct
    private void init() {
        MongoClientURI uri = new MongoClientURI("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/" + authenticationDb);
        MongoClient mongoClient = new MongoClient(uri);
        DB database = mongoClient.getDB(databaseName);
        jongo = new Jongo(database);
    }

    public MongoCollection getMongoCollection(String collectionName) {
        return jongo.getCollection(collectionName);
    }
}
