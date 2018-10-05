package com.apap.tutorial4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
	
	@RequestMapping("/flight/view/{id}")
	public String viewPath(@PathVariable String id, Model model) {
		FlightModel flight = flightService.getFlightDetailById(Long.parseLong(id));
		model.addAttribute("flight", flight);
		return "view-flight";
	}
	
	@RequestMapping(value="/flight/delete/{id}")
	private String deleteFlight(
			@PathVariable String id, 
			Model model
			) {
		FlightModel flight = flightService.getFlightDetailById(Long.parseLong(id));
		flightService.deleteFlight(flight);
		return "delete";
	}
	
	@RequestMapping(value="/flight/update/{licenseNumber}/{id}")
	private String update(@PathVariable("id") String id, @PathVariable("licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = flightService.getFlightDetailById(Long.parseLong(id));
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("flight",flight);
		model.addAttribute("pilot",pilot);
		return "update-flight";
	}
	
	@RequestMapping(value="/flight/update",method=RequestMethod.POST)
	private String updateFlight(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
}