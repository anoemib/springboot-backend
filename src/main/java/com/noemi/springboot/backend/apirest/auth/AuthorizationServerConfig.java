package com.noemi.springboot.backend.apirest.auth;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer  //Tener en cuenta usa ctrl + espacio para sugerir autocompletar.
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{  //configuración servidor de autenticación Oauth
	
@Autowired
private BCryptPasswordEncoder passwordEncoder;	

@Autowired
@Qualifier("authenticationManager")  //Esto es para asegurarnos que estamos importando el Bean correcto. 
private AuthenticationManager authenticationManager;

@Autowired
private InfoAdicionalToken infoAdicionalToken;

@Override
public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {  
	// TODO Auto-generated method stub
	//super.configure(security);
	//Se configura los permisos de los endpoint o ruta de acceso de oauth2.
	
	
	security.tokenKeyAccess("permitAll()")  //cualquiera puede solicitar acceso y recibir un token.
	.checkTokenAccess("isAuthenticated()"); //Permite validar el token. Solo acceden los token autenticado.
	
	
	//lo anterior configura los dos servidores de validación del server de autorización.
}

@Override
public void configure(ClientDetailsServiceConfigurer clients) throws Exception {  //Se registra la aplicación y usuario que se van a registrar.
	// TODO Auto-generated method stub
	//super.configure(clients);
	//acá se registra la aplicación que se conecta a la aplicación. tiene nombre y contraseña, los permisos, y como se conectan los usuarios. 
	clients.inMemory().withClient("angularapp").
	secret(passwordEncoder.encode("12345")).
	scopes("read","write") //permisos que tiene la aplicacion
	.authorizedGrantTypes("password", "refresh_token") //se autorizan los uaurios a través de contraseña, pueden ser otros tipos de autorizaciones. Token de acceso renovado. 
	.accessTokenValiditySeconds(3600)
	.refreshTokenValiditySeconds(3600);
}

@Override
public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	// TODO Auto-generated method stub
	
	
	//para pasar información adicional al token
	TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain(); 
	tokenEnhancerChain.setTokenEnhancers(Arrays.asList(infoAdicionalToken, accessTokenConverter()));
	//
	
	
	endpoints
	.authenticationManager(authenticationManager)
	.accessTokenConverter(accessTokenConverter())
	.tokenEnhancer(tokenEnhancerChain);

}

@Bean
public JwtAccessTokenConverter accessTokenConverter() {
	// TODO Auto-generated method stub
	
	JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter(); 
/*	
	jwtAccessTokenConverter.setSigningKey(JwtConfig.LLAVE_SECRETA); //clave secreta para firmar el token con un MAC //clave o contraseña interna del servidor, no es recomendable, lo mejor es un RSA. 
	*/
	
	jwtAccessTokenConverter.setSigningKey(JwtConfig.RSA_PRIVADA);  //se incorpora el ejemplo usando el metodo RSA. Clave Privada.  Con esto se firma el token.
	jwtAccessTokenConverter.setVerifierKey(JwtConfig.RSA_PUBLICA);  //Se incorpora la clave publica para el metodo  RSA. 
	 //Retorna un token
	return jwtAccessTokenConverter;
}



	
	
}
