package org.java.app.business.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.java.app.business.db.pojo.Pizza;
import org.java.app.business.db.serv.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<List<Pizza>> getAllorSearch(@RequestParam(required = false, name = "q") String query) {
		
		List<Pizza> pizzas = query == null ?
				 pizzaService.findAll():
				 pizzaService.findByName(query);
		
		if (pizzas.size() < 1) 
			 return new ResponseEntity<List<Pizza>>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(pizzas, HttpStatus.OK);
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Pizza> getPizza(@PathVariable int id) {
		
		Optional<Pizza> optPizza = pizzaService.findById(id);
		
		if (optPizza.isEmpty()) {
			
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(optPizza.get(), HttpStatus.OK);
	}
	
	@PostMapping()
	public Map<String, Object> save(@RequestBody PizzaDTO pizzaDTO) {
		
		Pizza pizza = new Pizza(pizzaDTO);
		Pizza addedPizza = pizzaService.save(pizza);
		
		Map<String, Object> info = new LinkedHashMap<>();
		info.put("success", true);
		info.put("id", addedPizza.getId());
		
		
		return info;
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Pizza> updatePizza(@PathVariable int id,
		@RequestBody PizzaDTO pizzaDTO) {
		
		Optional<Pizza> optPizza = pizzaService.findById(id);
		System.out.println(pizzaDTO);
		
		if (optPizza.isEmpty()) {
			
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		Pizza pizza = optPizza.get();
		pizza.fillFromPizzaDto(pizzaDTO);
	
		try {
			
			pizza = pizzaService.save(pizza);
			
			return new ResponseEntity<>(pizza, HttpStatus.OK);
		} catch (Exception e) {
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Boolean> deletePizza(@PathVariable int id) {
		
		Optional<Pizza> optPizza = pizzaService.findById(id);
		
		if (optPizza.isEmpty()) {
			
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		Pizza pizza = optPizza.get();
		pizzaService.deleteAllOffers(pizza);
		pizzaService.delete(pizza);
		
		return new ResponseEntity<>(true, HttpStatus.OK);
	}		
	
	
}
