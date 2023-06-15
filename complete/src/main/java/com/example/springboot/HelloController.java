package com.example.springboot;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.*;

@RestController
public class HelloController {


	@GetMapping("ru/get/cities")
	public String cities() throws SQLException {
		return Application.getCities();
	}
	@GetMapping("en/get/cities")
	public String citiesEn() throws SQLException {
		return Application.getCitiesEn();
	}
	@GetMapping("ru/get/airports")
	public String airports(@RequestParam(name="cityName", required=false, defaultValue="null") String name, Model model) throws SQLException {
		if (name.equals("null"))
			return Application.getAirports();
		else
			return Application.getAirportsByCity(name);
	}
	@GetMapping("en/get/airports")
	public String airportsEn(@RequestParam(name="cityName", required=false, defaultValue="null") String name, Model model) throws SQLException {
		if (name.equals("null"))
			return Application.getAirportsEn();
		else
			return Application.getAirportsByCityEn(name);
	}



}
