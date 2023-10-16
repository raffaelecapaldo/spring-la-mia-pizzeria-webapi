package org.java.app.business.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.java.app.business.db.pojo.Pizza;
import org.java.app.business.db.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/pizzas")
public class PizzaRestController {

	@Autowired
	private PizzaService pizzaService;
	
	@GetMapping
	@ResponseBody
	public ResponseEntity<List<Pizza>> getAllorSearch(String name) {
		
		List<Pizza> pizzas = name == null ?
				 pizzaService.findAll():
				 pizzaService.findByName(name);
		
		if (pizzas.size() < 1) 
			 return new ResponseEntity<List<Pizza>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(pizzas, HttpStatus.OK);
		
	}
	
	
}
