# Moulding machines monitoring system
Service responsible calculating metrics for moulding machines. 

## Important!!!
In this project there are two packages one contains whole application and second is called `common` which contains some 
classes/functions they can be used in other projects too. I prefer to externalize this and push to maven repository as separate
project it will allow to use it in each project in company.

## Database & migrations

### MySql
Project is using MySql database. In this project [flyway](https://flywaydb.org/) migrations are used. It helps to manage database structure changes.
Flyway keep track versioning of DB schema. Data is loaded into database called `machine_metrics`, it is created automatically in `test` profile in `EmbeddedMysqlConfiguration` class.

### Redis
Second database used in this project is `Redis`. Role of that is caching data to limit count of queries to MySql database. Cache is not fully implemented, all is described 
with comments (pseudo-code). In next iterations it could be used to keep snapshots of data to limit time needed to calculate metrics (if needed). By snapshots I understand partial
data calculated in some spring scheduler which is later used to prepare metrics result. Not all calculations would be done per each HTTP request to application.

## Run application properties

**Application profiles:**

* `test` - this profile is used in tests - it contains embedded MySql database and Redis (Cache), it can be used to run application locally for development purposes too. In this profile 'EmbeddedMysqlConfiguration' is activated by: `@Profile("test")`
* `stage` - this profile should be used in staging (pre-production)
* `prod` - this profile should be used in production

## Instruction to run project locally with 'test' profile (embedded MySql and Redis)
```sh
$ ./mvnw clean install
```
```sh
$ cd target
```
```sh
$ java -Dspring.profiles.active=test -jar moulding-machines-monitoring-system-0.0.1.jar
```
## Application health
Application has implemented actuator, thanks to that there is implemented health check endpoint:
```sh
$ curl -X GET http://localhost:8081/moulding-machines-monitoring-system/manage/health
```
Example response:
```json
{
    "status": "UP",
    "diskSpace": {
        "status": "UP",
        "total": 250790436864,
        "free": 108278022144,
        "threshold": 10485760
    },
    "redis": {
        "status": "UP",
        "version": "2.8.19"
    },
    "db": {
        "status": "UP",
        "database": "MySQL",
        "hello": 1
    }
}
```

## Example HTTP Rest call
```sh
$ curl -X GET 'http://localhost:8080/moulding-machines-monitoring-system/v1/metrics/machine?machineName=3x2%20brick%20mould&from=1515283200000&to=1515369600000'
```

Example response:
```json
{
    "machineName": "3x2 brick mould",
    "datetimeFrom": 1515283200000,
    "datetimeTo": 1515369600000,
    "production": 566384,
    "scrapPercentage": 0.03449921755406377,
    "downtimePercentage": 0.2112676056338028,
    "machineStatus": "GOOD",
    "performance": 0.8147527777777778,
    "availability": 1.037037037037037,
    "quality": 0.9655007824459362,
    "oee": 0.8157794238683127,
    "productionInTime": {
        "2018-01-07": [
            {
                "hour": 0,
                "value": 25263
            },
            {
                "hour": 1,
                "value": 16644
            },
            {
                "hour": 2,
                "value": 26143
            },
            {
                "hour": 3,
                "value": 23183
            },
            {
                "hour": 4,
                "value": 19144
            },
            {
                "hour": 5,
                "value": 23530
            },
            {
                "hour": 6,
                "value": 25810
            },
            {
                "hour": 7,
                "value": 19633
            },
            {
                "hour": 8,
                "value": 21782
            },
            {
                "hour": 9,
                "value": 21590
            },
            {
                "hour": 10,
                "value": 27437
            },
            {
                "hour": 11,
                "value": 18720
            },
            {
                "hour": 12,
                "value": 25551
            },
            {
                "hour": 13,
                "value": 24531
            },
            {
                "hour": 14,
                "value": 26445
            },
            {
                "hour": 15,
                "value": 20521
            },
            {
                "hour": 16,
                "value": 26613
            },
            {
                "hour": 17,
                "value": 25574
            },
            {
                "hour": 18,
                "value": 21950
            },
            {
                "hour": 19,
                "value": 28071
            },
            {
                "hour": 20,
                "value": 22075
            },
            {
                "hour": 21,
                "value": 25057
            },
            {
                "hour": 22,
                "value": 28999
            },
            {
                "hour": 23,
                "value": 22118
            }
        ]
    }
}
```

## Swagger documentation (JSON)
Swagger JSON can be found [here](http://localhost:8080/moulding-machines-monitoring-system/api-docs)

Request to get Swagger documentation:
```sh
$ curl -X GET http://localhost:8080/moulding-machines-monitoring-system/api-docs
```

**WARNING!!!** - IP in provided URL should be changed when application will be deployed to server

## Authors
* **Jakub Legowik** - [e-mail](kuba.legowik@gmail.com)