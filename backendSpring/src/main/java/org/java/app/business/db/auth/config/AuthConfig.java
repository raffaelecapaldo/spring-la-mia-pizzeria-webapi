package org.java.app.business.db.auth.config;

import org.java.app.business.db.auth.serv.UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AuthConfig {
	
	 @Bean
	  public FilterRegistrationBean corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.addAllowedOrigin("http://localhost:5173"); 
	    config.addAllowedHeader("*");
	    config.addAllowedMethod("*");
	    source.registerCorsConfiguration("/**", config);
	    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	    bean.setOrder(0);
	    return bean;
	  }


	@SuppressWarnings("removal")
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http)
		throws Exception {
			 
			http.authorizeHttpRequests()			
				//ACCESSO RISORSE STATIC
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
		        //PROTEZIONE ROTTE OFFER CON PATHERN SIMIL API
		        .requestMatchers(new AntPathRequestMatcher("/**/addOffer")).hasAuthority("ADMIN")
		        .requestMatchers(new AntPathRequestMatcher("/**/editOffer/**")).hasAuthority("ADMIN")
		        //ROTTE ADMIN
		        .requestMatchers("/pizzas/create/**").hasAuthority("ADMIN")
		        .requestMatchers("/pizzas/update/**").hasAuthority("ADMIN")
		        .requestMatchers("/pizzas/delete/**").hasAuthority("ADMIN")
		        .requestMatchers("/ingredients/create").hasAuthority("ADMIN")
		        .requestMatchers("/ingredients/delete/**").hasAuthority("ADMIN")
		        //ROTTE USER
				.requestMatchers("/pizzas").hasAuthority("USER")
		        .requestMatchers("/ingredients").hasAuthority("USER")
				.requestMatchers("/pizzas/**").hasAuthority("USER")
		        .requestMatchers("/ingredients/**").hasAuthority("USER")
		        .requestMatchers("/**").permitAll() //Permetti redirect alla login
		        .and().formLogin().defaultSuccessUrl("/pizzas")
		        .and().logout(); 
				http.csrf().disable(); //per API, momentaneo
				
				
			
			return http.build();
	}
	
	
	
	@Bean
	UserService userDetailsService() {
		return new UserService();
	}

    @Bean
    PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
   
      authProvider.setUserDetailsService(userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
    }
    
    
    
    
}
