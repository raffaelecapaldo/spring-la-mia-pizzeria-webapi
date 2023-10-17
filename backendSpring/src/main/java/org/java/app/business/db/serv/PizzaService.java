package org.java.app.business.db.serv;

import java.util.List;
import java.util.Optional;

import org.java.app.business.db.pojo.Pizza;
import org.java.app.business.db.pojo.SpecialOffer;
import org.java.app.business.db.repo.PizzaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepo pizzaRepo;
	
	@Autowired
	private SpecialOfferService specialOfferService;
	
	public Pizza save(Pizza pizza) {
		return pizzaRepo.save(pizza);
	}
	
	public void delete(Pizza pizza) {
		pizzaRepo.delete(pizza);
	}
	
	public List<Pizza> findAll() {
		return pizzaRepo.findAll();
	}
	
	public Optional<Pizza> findById(int id) {
		return pizzaRepo.findById(id);
	}
	
	public List<Pizza> findByName(String name) {
			
			return pizzaRepo.findByNameContaining(name);
		}
	
	public void deleteAllOffers(Pizza pizza) {
		List<SpecialOffer> specialOffersOfPizza = pizza.getSpecialOffers();
		for (SpecialOffer sp : specialOffersOfPizza) {
			specialOfferService.delete(sp);
		}
	}

}
