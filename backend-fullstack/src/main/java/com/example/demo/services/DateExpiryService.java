package com.example.demo.services;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.config.configration;
import com.example.demo.dao.EventData;
import com.example.demo.model.Eventdetail;

@Component
public class DateExpiryService {

    @Autowired
    private EventData eventData;

    @Bean
    @Scheduled(fixedRate = 5000 * 6 * 10 * 2 * 6) // Run every 1 hour
    public void CheckExpiryDate() throws Exception {
        List<Eventdetail> listdata = eventData.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        long currentMillis = Instant.now().toEpochMilli();
        System.out.println(listdata);
        for (int i = 0; i < listdata.size(); i++) {
            LocalDateTime dateTime = LocalDateTime.parse(listdata.get(i).getEventDateTime(), formatter);
            long millis = dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
            if (currentMillis > millis) {
                eventData.deleteById(listdata.get(i).getEventId());
            }

        }

    }
}