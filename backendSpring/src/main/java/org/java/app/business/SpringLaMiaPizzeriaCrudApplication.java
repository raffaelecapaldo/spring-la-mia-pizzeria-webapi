package org.java.app.business;

import java.time.LocalDate;

import org.java.app.business.db.auth.pojo.Role;
import org.java.app.business.db.auth.pojo.User;
import org.java.app.business.db.auth.serv.RoleService;
import org.java.app.business.db.auth.serv.UserService;
import org.java.app.business.db.pojo.Ingredient;
import org.java.app.business.db.pojo.Pizza;
import org.java.app.business.db.pojo.SpecialOffer;
import org.java.app.business.db.serv.IngredientService;
import org.java.app.business.db.serv.PizzaService;
import org.java.app.business.db.serv.SpecialOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringLaMiaPizzeriaCrudApplication implements CommandLineRunner  {
	
	@Autowired
	private PizzaService pizzaService;
	
	@Autowired
	private SpecialOfferService specialOfferService;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringLaMiaPizzeriaCrudApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Ingredient pomodoro = new Ingredient("Pomodoro");
		Ingredient mozzarella = new Ingredient("Mozzarella");
		Ingredient acciughe = new Ingredient("Acciughe");
		Ingredient wurstel = new Ingredient("Wurstel");
		
		ingredientService.save(pomodoro);
		ingredientService.save(mozzarella);
		ingredientService.save(acciughe);
		ingredientService.save(wurstel);

		
		Pizza pizza1 = new Pizza("Margherita", "La pizza più semplice", "http://localhost:8080/images/pizza-margherita.jpg", 4.50f, pomodoro, mozzarella);
		Pizza pizza2 = new Pizza("Capricciosa", "Ogni sfizio è un capriccio", "http://localhost:8080/images/pizza-capricciosa.jpg", 8.50f, pomodoro, mozzarella);
		Pizza pizza3 = new Pizza("Marinara", "Per stare leggero", "http://localhost:8080/images/pizza-marinara.jpg", 6f, pomodoro);
		Pizza pizza4 = new Pizza("Napoli", "Con le acciughe fresche", "http://localhost:8080/images/pizza-napoli.webp", 8f, pomodoro, acciughe);
		Pizza pizza5 = new Pizza("Wurstel e patatine", "Per tutte le età", "http://localhost:8080/images/pizza-wurstel.jpg", 5.50f, wurstel);

		pizzaService.save(pizza1);
		pizzaService.save(pizza2);
		pizzaService.save(pizza3);
		pizzaService.save(pizza4);
		pizzaService.save(pizza5);
		
		SpecialOffer sp1 = new SpecialOffer("Due pizza al prezzo di una", LocalDate.now(), LocalDate.now().plusDays(2), pizza1);
		SpecialOffer sp2 = new SpecialOffer("Bibite gratis", LocalDate.now(), LocalDate.now().plusDays(2), pizza1);
		SpecialOffer sp3 = new SpecialOffer("Buono sconto 20% prossimo ordine", LocalDate.now(), LocalDate.now().plusDays(2), pizza2);

		

		specialOfferService.save(sp1);
		specialOfferService.save(sp2);
		specialOfferService.save(sp3);

		//ROLES
		
		Role admin = new Role("ADMIN");
		Role user = new Role("USER");
		
		roleService.save(admin);
		roleService.save(user);
		
		//USERS

		
		final String userPsw = new BCryptPasswordEncoder().encode("user");
		final String adminPsw = new BCryptPasswordEncoder().encode("admin");
		
		User normalUser = new User("user", userPsw, user);
		User adminUser = new User("admin", adminPsw, admin, user);
		
		userService.save(normalUser);
		userService.save(adminUser);




		

		//pizzaService.findAll().forEach(p -> System.out.println(p));
		

		
	}

	

}
