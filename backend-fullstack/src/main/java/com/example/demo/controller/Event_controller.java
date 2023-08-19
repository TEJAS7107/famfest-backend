package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.EventData;
import com.example.demo.model.Eventdetail;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/event")
public class Event_controller {

	@Autowired
	private EventData info;

	@PostMapping("/add_event")
	public String AddEvent(@RequestBody Eventdetail details) {

		details.setCreationDate(new Date());
		details.setEventId(UUID.randomUUID().toString().split("-")[0]);
		info.save(details);
		return details.getEventId();
	}

	@GetMapping("/get_event_data/{id}")
	public List<Eventdetail> getAlldataForUser(@PathVariable String id) {
		// Optional<Eventdetail> data = info.findAllByUserId(id);
		List<Eventdetail> al = new ArrayList<>();
		List<Eventdetail> getdata = info.findAll();
		for (int i = 0; i < getdata.size(); i++) {
			if (getdata.get(i).getUserId() != null) {
				if (getdata.get(i).getUserId().equalsIgnoreCase(id)) {

					al.add(getdata.get(i));

				}
			}

		}

		return al;
	}

	@DeleteMapping("/delete_event/{id}")
	public String DeleteEvent(@PathVariable String id) {

		info.deleteById(id);

		return "data Deleted successfully";

	}

}
