# Phone Directory #

The application handles a set of entries, that contain a first name, last name, and phone number.

The entries are validated with the rules:
 - Non empty first name
 - Non empty last name
 - Non empty phone number
 - Phone number has to have the format: **+39 02 1234567**

See the Web Application up and running as an [Heroku App](https://phone-directory.herokuapp.com/).

## Requirements ##
* JDK **1.8**
* [Apache Maven **3.x**](http://maven.apache.org/)
* MongoDB running on localhost:27017

## Compile and run it ##
* `$ mvn clean spring-boot:run`

OR

* `$ java -jar target/phone-directory-3.0.0.jar`

This is a Spring Boot Application.

As WebServer Tomcat is used.

License
-------

This software is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).