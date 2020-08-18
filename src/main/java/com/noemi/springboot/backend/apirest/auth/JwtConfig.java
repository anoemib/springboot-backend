package com.noemi.springboot.backend.apirest.auth;

public class JwtConfig {

	public static final String LLAVE_SECRETA = "alguna.clave.secreta.12345668";

	//Static, es un atributo de la clase y no del objeto
	//final, no se puede modificar.
	

	public static final String RSA_PRIVADA = "-----BEGIN RSA PRIVATE KEY-----\r\n" + 
			"MIIEpAIBAAKCAQEApSGzhhZcuAhmroePv3i8tDLGM3FZlk0I4VQo2t3SspSJ6+IM\r\n" + 
			"kSTIdpCiNEwzOsrK9bRGbsQ1itGF3IEYDdu+x76Q5Ly/vwYBqxsyJQ3bWpxFlSwE\r\n" + 
			"YSJscpg+5RcUk4d6B69zdAPZfy8VSvUemCDUUpeElKYyqw9YzxlSeVI2dM10R1OG\r\n" + 
			"AXyTZnH1W1otYY9BvsXzZMVDkOLIRgKf/syoUrYGT73eMwGnZm6db+8+rlxtJm3F\r\n" + 
			"GQIfhASg34w3CruMFmM1Vs40BJ34IDDL39lh+VD5w59k2gI+0I01WcxEqBGFlRFD\r\n" + 
			"UdxTGcRSwH7qX+5QE7Blce8+bkQPnmYSLH4o+QIDAQABAoIBAEcTz4MqwvQP4arD\r\n" + 
			"/PLMlGT36eogoxAXzne20vMKQfPODeOzY/dhnsuyC695ESAARAYod5yb5fP/pZ+5\r\n" + 
			"DVKBwIeXliRuvhYKDtPIm/fpE/7Xfvfrf2MT/xwAPio7mga6bKM8OPS553dgG8ks\r\n" + 
			"4uNWVi3KvC2DsBGDRJW375D6+yegynB1yy0m1TzqJnhoP9pntPZjK/ae7JsHOsco\r\n" + 
			"slZvoWYX8bw1+a2L8juBJRAYcvQEEm9vAjyr/zx7D1LDxPS76TQMCO9iqkf5j23w\r\n" + 
			"kK2Lhc4rjFaix1BKcYQRksX4X2R67yMMBJdlFxEaJQ0yH4NdkzbtPofIzcm+Yhl5\r\n" + 
			"r2Ev/bUCgYEA2Ca1qrL1q3R9/0A84P+tEkaKhYTvjk3wBHBD0SsdfHuyoexU+pFJ\r\n" + 
			"2aiTQlJQQjETqmqQNfq4zxWGYUb0pK0Q3mjVgh9WZ989uN3vp1VwCFDCs2mYvoPP\r\n" + 
			"mSchUS8gfc6OZt2ZO3A65uP3wdnZPElhAdW1f3SwMrzkg+yDHDl+Xz8CgYEAw5Md\r\n" + 
			"grT1Lu7Yg8NxOQKW968eA3hxaGVMkviSQ0JrTMj64Zin9ARCT2TMnzHvgmXJ2/VF\r\n" + 
			"SBTIWTiX5X/4btmykwiluyv23sogiT5jdY/0YLsQ185U5Vg5SrckaHMibj3Vq7U3\r\n" + 
			"go4Y1DM/PzQjVjWUOYThOtUPAYFv2JSfCztFIccCgYEA1IRCqyEm37s6QiMzd/iJ\r\n" + 
			"fmVyvlH0ghJowMRsORRN+l1YRVGqP21nkaPnnRZ3KI0+C5iYTypCWACOzcfIsrwh\r\n" + 
			"0Lp76WLMPnZEJiW3AevDqblVpLZW4kGQc8EAReSy6l6SrdqEVOdEUkyjBKoepuJy\r\n" + 
			"iS5IM5dTwZ7sgjSgt7hjYlsCgYEAhyIImv60BvjLq8eIXQ74By39vAIlwi90SHzu\r\n" + 
			"ewDSqRyZlPVo30duFfOdG0ZiqqPwZUZE17hccqGS+uWAX/+VztiDwx9ulaPt9Uah\r\n" + 
			"bGLKRWgcDDEqIcni1EhVJMKhALFDCUDOmSvb5gt+4QoCfJfK0i+JfKAonS61fF28\r\n" + 
			"wwLyEpMCgYAMBdIh8TyIzPigW/UEJhAG4Oo2tjRb3kqCszi3xJ1Z4DlA5y73JaB+\r\n" + 
			"DfoNfZTa8BU+CV++etYa85fU4XyEu3+iiuEL/ozJttr4YSQeL80b2WlzfB2nudoV\r\n" + 
			"XZeVNhI29qMvvQw11Df8+Kn8nCf1dRByPPFeqCOzpLj/SECpjNspew==\r\n" + 
			"-----END RSA PRIVATE KEY-----";
	
	public static final String RSA_PUBLICA = "-----BEGIN PUBLIC KEY-----\r\n" + 
			"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApSGzhhZcuAhmroePv3i8\r\n" + 
			"tDLGM3FZlk0I4VQo2t3SspSJ6+IMkSTIdpCiNEwzOsrK9bRGbsQ1itGF3IEYDdu+\r\n" + 
			"x76Q5Ly/vwYBqxsyJQ3bWpxFlSwEYSJscpg+5RcUk4d6B69zdAPZfy8VSvUemCDU\r\n" + 
			"UpeElKYyqw9YzxlSeVI2dM10R1OGAXyTZnH1W1otYY9BvsXzZMVDkOLIRgKf/syo\r\n" + 
			"UrYGT73eMwGnZm6db+8+rlxtJm3FGQIfhASg34w3CruMFmM1Vs40BJ34IDDL39lh\r\n" + 
			"+VD5w59k2gI+0I01WcxEqBGFlRFDUdxTGcRSwH7qX+5QE7Blce8+bkQPnmYSLH4o\r\n" + 
			"+QIDAQAB\r\n" + 
			"-----END PUBLIC KEY-----";	
}
