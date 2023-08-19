package com.example.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.UserMessage;

public interface MessageData extends MongoRepository<UserMessage, String> {

}
