# slang-webapp
Web application for slang

Clone the repository and run:

mvn spring-boot:run

The webapp is configured to run against Postgres DB
You can easily start one with docker
`docker run -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=score -d -p 5432:5432 postgres`