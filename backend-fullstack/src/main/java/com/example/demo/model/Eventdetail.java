package com.example.demo.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "eventinfo")
public class Eventdetail {
	
	@Id
	private String eventId;
	@Field
	private String userId;
	@Field
	private String eventName;
	@Field
	private String eventPoster;
	@Field
	private String eventVenue;
	@Field
	private String eventDateTime;
	@Field
	private Long price;
	@Field
	private String eventMode;
	@Field
	private String eventOrganizer;
	@Field
	private String eventDetails;
	@Field
	private Date creationDate;
	
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventPoster() {
		return eventPoster;
	}
	public void setEventPoster(String eventPoster) {
		this.eventPoster = eventPoster;
	}
	public String getEventVenue() {
		return eventVenue;
	}
	public void setEventVenue(String eventVenue) {
		this.eventVenue = eventVenue;
	}
	public String getEventDateTime() {
		return eventDateTime;
	}
	public void setEventDateTime(String eventDateTime) {
		this.eventDateTime = eventDateTime;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public String getEventMode() {
		return eventMode;
	}
	public void setEventMode(String eventMode) {
		this.eventMode = eventMode;
	}
	public String getEventOrganizer() {
		return eventOrganizer;
	}
	public void setEventOrganizer(String eventOrganizer) {
		this.eventOrganizer = eventOrganizer;
	}
	public String getEventDetails() {
		return eventDetails;
	}
	public void setEventDetails(String eventDetails) {
		this.eventDetails = eventDetails;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
	
	public Eventdetail() {
		// TODO Auto-generated constructor stub
	}
	

	
	
	
}
