# webstaurantstore-automation


## Test Case

* Go to https://www.webstaurantstore.com/
* Search for 'stainless work table'.
* Check the search result ensuring every product has the word 'Table' in its title.
* Add the last of found items to Cart.
* Empty Cart.

## How to Run

make sure `maven` is installed. Go to root folder where `pom.xml` is visible. 
Then run: 

```bash
mvn clean test
```
## CI Pipeline

Every commit or pull request against main branch trigger a test runner which runs the tests on `windows-2019`