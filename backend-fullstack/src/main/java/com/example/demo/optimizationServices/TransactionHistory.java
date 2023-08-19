package com.example.demo.optimizationServices;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.TransactionData;
import com.example.demo.model.TransactionDetails;

@Service
public class TransactionHistory {

    @Autowired
    private TransactionData transactionData;

    public List<TransactionDetails> getTransactionDetails(String UserId) {

        List<TransactionDetails> list = transactionData.findAll();
        List<TransactionDetails> newList = new LinkedList<>();

        for (TransactionDetails transactionDetails : list) {
            if (transactionDetails.getUserId().equalsIgnoreCase(UserId)) {

                newList.add(transactionDetails);

            }
        }

        return newList;

    }

}
