package com.example.demo.optimizationServices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EventData;
import com.example.demo.model.Eventdetail;

@Service
public class PublicDataService {
	
	@Autowired
	private EventData eventData;
	
	
	@Bean
	public List<Eventdetail> forOfflineData() {
		List<Eventdetail> allData = eventData.findAll();
		List<Eventdetail> offlineList = new ArrayList<>();
		for (int i = 0; i < allData.size(); i++) {
			if (allData.get(i).getEventMode().equalsIgnoreCase("Offline") && allData.get(i).getEventMode() != null) {
				offlineList.add(allData.get(i));
			}

		}
		
//		List<Eventdetail> findByeventMode = eventData.findByeventMode("Offline"); this technique takes more time to load data around 33 secs
		
		return offlineList;
		
		
	}
	
	
	
	@Bean
	public List<Eventdetail> forOnlineData() {
		List<Eventdetail> allData = eventData.findAll();
		List<Eventdetail> onlineList = new ArrayList<>();
		for (int i = 0; i < allData.size(); i++) {
			if (allData.get(i).getEventMode().equalsIgnoreCase("Online") && allData.get(i).getEventMode() != null) {
				onlineList.add(allData.get(i));
			}

		}
		
//		List<Eventdetail> findByeventMode = eventData.findByeventMode("Offline"); this technique takes more time to load data around 33 secs
		
		return onlineList;
		
		
	}
	
	
	
	@Bean
	public List<Eventdetail> forAllData() {
		List<Eventdetail> allData = eventData.findAll();

		
//		List<Eventdetail> findByeventMode = eventData.findByeventMode("Offline"); this technique takes more time to load data around 33 secs
		
		return allData;
		
		
	}

}
