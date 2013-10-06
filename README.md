# Roadmapper

A web application for tracking roadmap data.

# Setup

These instructions assume you have cloned the repo into a local directory named `roadmapper`.

## 1. Dependencies

| Required | Version |
| -------- | -------:|
[Java (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)  | ~ 1.7.0
[Postgres](http://www.postgresql.org/download/)                                   | ~ 9.3.0
[Play](http://www.playframework.com/documentation/2.1.3/Installing)               | ~ 2.2.0

### Quickstart on OS X via [Homebrew](http://brew.sh/)

Make sure you have the Java 7 *JDK* installed. If you downloaded from _java.com_
you probably only have the JRE, not the JDK. You have to go through _oracle.com_
and get Java SE "[Java for developers](http://www.oracle.com/technetwork/java/javase/downloads/index.html)".

    $ java -version
    java version "1.7.0_40"
    ...

Then use Homebrew to install `postgres` and `play`:

    $ brew install postgres play
    $ initdb /usr/local/var/postgres/
    $ pg_ctl -D /usr/local/var/postgres/ start

For OSX, you should check out [PostgresApp](http://postgresapp.com/documentation).


## 2. Setup database

Create a user and database for the app.

    $ psql -h localhost
    > create user roadmapper;
    > create database roadmapper;
    > \q


## 3. Change New Relic license key
  
Edit `newrelic.yml` to set your New Relic license key.


## 4. Configure OAuth

Edit `conf/overrides.conf` and enter the root URL, client ID, and client secret. If you're a New Relic employee, you can get these values by asking another Roadmapper developer. If you're not a New Relic employee, you'll need to have your own OAuth 2 server up and running with a client set up using a redirect URL of `http://localhost:9000/auth/callback`.

Note: You can also optionally configure the OAuth values using environment variables: `OAUTH_ROOT_URL`, `OAUTH_CLIENT_ID`, and `OAUTH_CLIENT_SECRET` respectively.


## 4b. Bypass OAuth

If you want to get Roadmapper running in your dev environment without dealing with OAuth, you can override the auth system by adding `auth.impersonate.email : "..."` in `conf/overrides.conf`. Any email address your provide for the value will be blindly authenticated. *Obviously this is only intended for development.*

Make sure you have a row corresponding to the same email address in the `users` db table.


## 5. Start Play

From the `roadmapper` directory,

    $ play run

Then navigate to [http://localhost:9000](http://localhost:9000/) in your browser to run the app's setup process.


## 6. Success


# Development

Play has a [great feature for setting up a project](http://www.playframework.com/documentation/2.0/IDE) for common Java IDEs. Once the setup steps are done, follow the directions for your IDE and you should have a nice environment for development.

While Play is running your changes to files (HTML, JavaScript, CSS, Java, and Scala) auto-refresh the server to speed your development flow.

