config = {
	cache {
		name 'productSearchCache'
		eternal false
		overflowToDisk false
		maxElementsInMemory 10000
		maxElementsOnDisk 0
		//TODO: find out a way to externalize this timeToLive parameter value
		timeToLiveSeconds 60*60*5
		memoryStoreEvictionPolicy 'LRU'
	 }
}
