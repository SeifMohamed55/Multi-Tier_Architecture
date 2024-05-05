package com.first.spring.config;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.first.spring.utilities.JwtTokenUtil;
import com.first.spring.utilities.AESEncryptor;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.first.spring.aspects")
public class BeanInitializer {
	
	@Bean
	public BCryptPasswordEncoder encryptor() {
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	public JwtTokenUtil jwtUtil() {
		return new JwtTokenUtil();
	}
	
	@Bean 
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}
	
	@Bean
	@Autowired
	public AESEncryptor aesEnc(Environment env) {
		return new AESEncryptor(env);
	}

}
