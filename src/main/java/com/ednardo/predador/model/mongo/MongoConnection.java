package com.ednardo.predador.model.mongo;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

public class MongoConnection {

	public static final String DB_NAME = "boot";
	public static final String MONGO_HOST = "localhost";
	public static final String USER = "admin";
	public static final String PASSWORD = "YrFjWBsNd3ij";
	public static final int MONGO_PORT = 27017;

	@Bean
	public MongoOperations connection() throws UnknownHostException {
		MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
		UserCredentials userCredentials = new UserCredentials(USER, PASSWORD);
		MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME, userCredentials);
		return mongoOps;
	}
}
