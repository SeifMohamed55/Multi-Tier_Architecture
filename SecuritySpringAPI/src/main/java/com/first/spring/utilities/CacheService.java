package com.first.spring.utilities;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.first.spring.loginmodule.UserDetailsImpl;

@Component
public class CacheService {

	@Autowired
	private CacheManager cacheManager;
	
	private Logger log  = Loggers.getControllersLogger();
	
	public void evictUserFromCache(String email) {
		Cache cache = cacheManager.getCache(Constants.USER_CACHE_NAME);
		if (cache != null) {
			cache.evict(email);
		}
	}
	
	public void putUserDetailsInCache(UserDetailsImpl userDetails) {
	    Cache cache = cacheManager.getCache(Constants.USER_CACHE_NAME);
	    if (cache != null) {
	      cache.put(userDetails.getUsername(), userDetails); // Use email as key
	    }
	  }
	
	public UserDetailsImpl getCachedUserByEmail(String email) {
		Cache cache = cacheManager.getCache(Constants.USER_CACHE_NAME);
	    Cache.ValueWrapper wrapper = cache.get(email);
	    if (wrapper != null) {
	    	log.error("value returned");
	      return (UserDetailsImpl) wrapper.get();
	    }
	    log.error("cached user is null");
	    return null;  // User not found in cache
	}
	
	
}
