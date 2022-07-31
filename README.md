# :bowtie: About this application #

Implement an in memory cache. The cache’s purpose is to allow the results of expensive or long running calculations or
data retrievals to be stored in memory to avoid unnecessary duplication of effort.
You should assume:

- There will be multiple calling threads requesting data.
- You do not need to implement the underlying calculation that produces the values. Your code should be implemented so
  that it can be provided with a way to run these calculations.
- The key is the only input to the calculation which produces the value. The same key will always produce the same
  value.
- Data source lookups / calculations may be slow.

## Original Problem Statement ##

[Link to problem statement doc](https://docs.google.com/document/d/1xt-h9w0D0gLbaQd10rIz_dZRRVLLw6ks/edit?usp=sharing&ouid=116154266718479583188&rtpof=true&sd=true)

## Requirements Met ##

The requirements are:

- The cache must provide a method to request the value for a key -> Look at get(String key) in Cache interface and
  implementation
- If the cache does not contain the requested data it should load it from an underlying data source, and then cache it
  for future requests -> Look at add(String key, Object value) in Cache interface and implementation
- If the cache exceeds a set number of items in size then the least recently requested items should be removed -> Used a
  LRUMap implementation with a fixed maximum size which removes the least recently used entry if an entry is added when full. 
  The least recently used algorithm works on the get and put operations only.
  https://commons.apache.org/proper/commons-collections/apidocs/org/apache/commons/collections4/map/LRUMap.html
- It should use generics so it can be instantiated for different types of keys and values -> Yes, this is currently
  being done, look at InMemoryCache and CacheObject Class.

## To run this project ##

> :computer: - Clone to computer
> 🧹 - Run `mvn clean install`
> The above command runs tests as well

## Future Implementation ##
TBC
