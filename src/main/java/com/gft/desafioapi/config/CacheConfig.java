package com.gft.desafioapi.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	@Bean
	public net.sf.ehcache.CacheManager ehCacheManager() {
		CacheConfiguration myCache = new CacheConfiguration();

		myCache.setName("custom-cache");
		myCache.setMemoryStoreEvictionPolicy("LRU");
		myCache.setMaxEntriesLocalHeap(1000);
		myCache.setTimeToLiveSeconds(10);

		net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
		config.addCache(myCache);

		return net.sf.ehcache.CacheManager.newInstance(config);

	}

	@Bean
	@Override
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheManager());
	}
}
