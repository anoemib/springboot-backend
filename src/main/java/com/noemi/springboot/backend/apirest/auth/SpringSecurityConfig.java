package com.noemi.springboot.backend.apirest.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableGlobalMethodSecurity(securedEnabled = true)  //Con esto se habilita la seguridad mediante anotations en los REST-CONTROLLERS 
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{  //Configuración de seguridad de Spring.

	@Autowired
	private UserDetailsService usuarioService;

	@Bean  //Lo registra en el contendor de Spring. Es decir registra la clase que retorna el metodo para que a futuro se pueda ocupar inyectandolo.
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
		
	}
	
	@Bean("authenticationManager")  //Por defecto la clase no se puede inyectar por que no es un Bean.  Por lo que sobreescribimos la clase con un anotation @Bean para que se pueda inyectar en la clase AuthorizationServerConfig.
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(this.usuarioService).passwordEncoder(passwordEncoder());
		super.configure(auth);
	} 
	
	@Override
	public void configure(HttpSecurity http) throws Exception {   
		// TODO Auto-generated method stub
		
		http.authorizeRequests()
		.anyRequest().
		authenticated()
		.and()
		.csrf()
		.disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}  

	
	
	
	
}
