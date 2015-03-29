package com.ednardo.predador.model.mongo;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;

@Component
public class MongoConnection {

	public static final String DB_NAME = "boot";
	public static final String MONGO_HOST = " 55173af8e0b8cde38f000011-sajadv.rhcloud.com";
	public static final String USER = "admin";
	public static final String PASSWORD = "YrFjWBsNd3ij";
	public static final int MONGO_PORT = 48411;

	@Bean
	public MongoOperations connection() throws UnknownHostException {
		MongoClient mongo = new MongoClient(MONGO_HOST, MONGO_PORT);
		UserCredentials userCredentials = new UserCredentials(USER, PASSWORD);
		MongoOperations mongoOps = new MongoTemplate(mongo, DB_NAME, userCredentials);
		return mongoOps;
	}
}
