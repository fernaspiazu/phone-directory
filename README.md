# Telephone Directory #

The application handles a set of entries, that contain a first name,
last name, and a telephone number.
The entries are validated, that it's not possible to enter an empty first or last name; and the phone number should be of the form: **+39 02 1234567**

[See](http://phone-directory.herokuapp.com/) the Web Application up and running on [Heroku](https://www.heroku.com/).

## Requirements ##
* JDK **1.6.x+**
* [Apache Maven **3.x**](http://maven.apache.org/)
* PostgreSQL Database up and running on your local machine (listening on port 5432). Credentials can be whatever you want, it's enough to modify the properties file [postgresql_local.properties](https://github.com/fumandito/phone-directory/blob/master/src/main/resources/postgresql_local.properties) and modify `postgresql.user`, `postgresql.password`, and eventually the property `postgresql.url` whether the database name is other one. 

## Compile and run it ##
In order to compile and run this webapp in your local computer it's sufficient to run these Maven commands:

* `$ mvn clean install`
* `$ mvn tomcat7:run-war` 

But maybe it would be better to run it with this one:

* `$ mvn clean tomcat7:run-war`

The above command will clean, install, package and run the Web app inside a Tomcat Embedded ([See Tomcat Maven Plugin](http://tomcat.apache.org/maven-plugin.html)). The application will listening on port 8080.

License
-------

This software is licensed under the [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).