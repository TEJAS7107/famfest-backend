package com.example.demo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.TransactionDetails;
import java.util.List;

public interface TransactionData extends MongoRepository<TransactionDetails, String> {
    List<TransactionDetails> findByUserId(String userId);
}
