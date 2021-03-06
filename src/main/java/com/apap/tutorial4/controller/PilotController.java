package com.apap.tutorial4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		return "addPilot";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping(value = "/pilot/view")
	private String view(@RequestParam(value = "licenseNumber") String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/view/{licenseNumber}")
	public String viewPath(@PathVariable String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		if (pilot == null) {
			model.addAttribute("Oops","license number "+licenseNumber+" tidak dapat ditemukan.");
			return "error";
		}
		else {
			model.addAttribute("pilot", pilot);
			return "view-pilot";
		}
	}
	
	@RequestMapping(value="/pilot/delete/{licenseNumber}")
	private String delete(@PathVariable String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		pilotService.deletePilot(pilot);
		return "delete";
	}
	
	@RequestMapping(value="/pilot/update/{licenseNumber}")
	private String update(@PathVariable String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot",pilot);
		return "update-pilot";
	}
	
	@RequestMapping(value="/pilot/update",method=RequestMethod.POST)
	private String updatePilot(@ModelAttribute PilotModel pilot) {
		pilotService.addPilot(pilot);
		return "add";
	}
}