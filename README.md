# Telephone Directory #

The application handles a set of entries, that contain a first name, last name, and a telephone number.
The entries are validated, that it's not possible to enter an empty first or last name; and the phone number should be of the form: **+39 02 1234567**

See the Web Application up and running as an [Heroku App](https://phone-directory.herokuapp.com/).

## Requirements ##
* JDK **1.8**
* [Apache Maven **3.x**](http://maven.apache.org/)
* PostgreSQL Database up and running on your local machine (listening on port 5432). Credentials can be whatever you want, it's enough to modify the properties file [postgresql_local.properties](https://github.com/fumandito/phone-directory/blob/master/src/main/resources/postgresql_local.properties) and modify `postgresql.user`, `postgresql.password`, and eventually the property `postgresql.url` whether the database name is other one.
* In this case I has a Postgres database within a Docker Container up and running.

## Compile and run it ##
In order to compile and run this webapp in your local computer it's sufficient to run these Maven commands:

* `$ mvn clean spring-boot:run`

otherwise

* `$ java -jar target/phone-directory-2.0.0.jar`

The commands above will clean, install, package and run the webapp.

This is a Spring Boot Application.

License
-------

This software is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).