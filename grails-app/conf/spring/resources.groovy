// Place your Spring DSL code here
beans = {
	productSearchCache(
			org.springframework.cache.ehcache.EhCacheFactoryBean) {
				//TODO: findout a way to externalize this timeToLive parameter value
				timeToLive = 60*60*5 // life span in seconds
			}
}
