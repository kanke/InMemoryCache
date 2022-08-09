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

## Feedback ##
Positive
The code is readable.
Maven build compiles without alteration and all tests pass.
Test coverage is good. See below.
Threading issues are addressed successfully (though coarsely).
Generics are used (but not completely – leading to warnings)
 

Negative
Candidate has not really implemented what was asked for. They have used a non-thread safe apache commons LRU cache (which is perfectly fine), but all they have really done is wrap it in their own class with synchronized blocks.
There is a method to set a value on the cache, whereas we ask for a cache that knows how to get the value for a given key, and does so by itself when a new key is requested.
We hope that the candidate does this in a thread safe way so that concurrent requests for the same key only result in a single call to the underlying data source. The candidate has bypassed all this and left it up to the calling code.
A large proportion of the code is to do with timing out cache entries. This is not a requirement in the question.
 
>> Line Coverage: 51/54 (94%)
>> Generated 21 mutations Killed 13 (62%)
>> Mutations with no coverage 1. Test strength 65%
>> Ran 79 tests (3.76 tests per mutation)
