# Deep Envers #

Example of how to audit data using Spring Data e Spring Envers 

### Dependences ###

* Spring Envers;
* Spring Data;
* Flyway;
* Aspect;

### Domain and Audit tables in separate database schemas ###

* Using H2 DB with separate audit schema. H2 console could be access with app running, on: `http://localhost:8080/h2-console/` 
* Flyway scripts available on: `resources/db/migration`
* Domain tables (car and person) should be created on main schema;
* Audit tables (car_aud, person_aud and revinfo) should be created on audit schema;

### Aspect ###

* Aspect (AspectInterceptor) used just to audit log (used to log before/after repository calls);

### Default Envers Audit - Car ###

* Using @Audited on Car class to allow simple envers magic;
* CarRepository implements JpaRepository (Sprnig Data) and RevisionRepository (Spring Envers);
* CarController exposes CRUD and Audit Car endpoints;  

*Car Post Request:* 

```
curl --location --request POST 'localhost:8080/car' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name": "Bobolbi",
	"model": "Fusca" 
}
```

*Car Post Response:*
```
{
    "id": 1,
    "name": "Fusca do Oseas",
    "model": "Fusca"
}
```

*Car Audit Request:* 

```
curl --location --request GET 'localhost:8080/car/audit/1' \
--header 'Content-Type: application/json
```

*Car Audit Response:*
```
{
    "content": [
        {
            "metadata": {
                "delegate": {
                    "id": 1,
                    "timestamp": 1582976611729,
                    "username": "author.revision",
                    "revisionDate": "2020-02-29T11:43:31.729+0000"
                },
                "revisionNumber": 1,
                "revisionDate": "2020-02-29T08:43:31.729",
                "revisionInstant": "2020-02-29T11:43:31.729Z",
                "revisionType": "UNKNOWN",
                "requiredRevisionNumber": 1,
                "requiredRevisionInstant": "2020-02-29T11:43:31.729Z"
            },
            "entity": {
                "id": 1,
                "name": "Babolbi - 1582976611668",
                "model": "Fusca"
            },
            "revisionNumber": 1,
            "revisionInstant": "2020-02-29T11:43:31.729Z",
            "requiredRevisionNumber": 1,
            "requiredRevisionInstant": "2020-02-29T11:43:31.729Z"
        }
    ],
    "latestRevision": {
        "metadata": {
            "delegate": {
                "id": 1,
                "timestamp": 1582976611729,
                "username": "author.revision",
                "revisionDate": "2020-02-29T11:43:31.729+0000"
            },
            "revisionNumber": 1,
            "revisionDate": "2020-02-29T08:43:31.729",
            "revisionInstant": "2020-02-29T11:43:31.729Z",
            "revisionType": "UNKNOWN",
            "requiredRevisionNumber": 1,
            "requiredRevisionInstant": "2020-02-29T11:43:31.729Z"
        },
        "entity": {
            "id": 1,
            "name": "Babolbi - 1582976611668",
            "model": "Fusca"
        },
        "revisionNumber": 1,
        "revisionInstant": "2020-02-29T11:43:31.729Z",
        "requiredRevisionNumber": 1,
        "requiredRevisionInstant": "2020-02-29T11:43:31.729Z"
    },
    "empty": false
}
```

### Deep Envers Audit - Person ###

* Person have a more complete audit strategy;
* Person extends Auditable class, that maps 4 audit attributes on domain class using AuditorAwareImpl to fill the auditor username (createBy and lastUpdatedBy); 
* Person save username on revinfo table using AuditRevisionEntity and AuditorRevisionListener;
* AuditorConfguration initialize AuditorAwareImpl bean in Spring Context;
* PersonRepository implements JpaRepository (Spring Data) and RevisionRepository (Spring Envers);
* PersonController exposes CRUD and Audit Person endpoints;

*Person PostRequest:* 

```
curl --location --request POST 'localhost:8080/person' \
--header 'Content-Type: application/json' \
--data-raw '{
	"name": "John",
	"gender": "M" 
}
```

*Person Post Response:* 
```
{
    "id": 1,
    "name": "John",
    "gender": "M"
}
```

*Person Audit Request:* 

```
curl --location --request GET 'localhost:8080/person/audit/1' \
--header 'Content-Type: application/json
```

*Person Audit Response:*
```
{
    "content": [
        {
            "metadata": {
                "delegate": {
                    "id": 32,
                    "timestamp": 1582985631045,
                    "username": "author.revision",
                    "revisionDate": "2020-02-29T14:13:51.045+0000"
                },
                "revisionDate": "2020-02-29T11:13:51.045",
                "revisionInstant": "2020-02-29T14:13:51.045Z",
                "revisionNumber": 32,
                "requiredRevisionNumber": 32,
                "requiredRevisionInstant": "2020-02-29T14:13:51.045Z",
                "revisionType": "UNKNOWN"
            },
            "entity": {
                "createdBy": "author.auditor",
                "createdDate": "2020-02-29T14:13:50.983Z",
                "lastModifiedBy": "author.auditor",
                "lastModifiedDate": "2020-02-29T14:13:50.983Z",
                "id": 1,
                "name": "John - 1582985630846",
                "gender": "M"
            },
            "requiredRevisionNumber": 32,
            "revisionInstant": "2020-02-29T14:13:51.045Z",
            "requiredRevisionInstant": "2020-02-29T14:13:51.045Z",
            "revisionNumber": 32
        }
    ],
    "latestRevision": {
        "metadata": {
            "delegate": {
                "id": 32,
                "timestamp": 1582985631045,
                "username": "author.revision",
                "revisionDate": "2020-02-29T14:13:51.045+0000"
            },
            "revisionDate": "2020-02-29T11:13:51.045",
            "revisionInstant": "2020-02-29T14:13:51.045Z",
            "revisionNumber": 32,
            "requiredRevisionNumber": 32,
            "requiredRevisionInstant": "2020-02-29T14:13:51.045Z",
            "revisionType": "UNKNOWN"
        },
        "entity": {
            "createdBy": "author.auditor",
            "createdDate": "2020-02-29T14:13:50.983Z",
            "lastModifiedBy": "author.auditor",
            "lastModifiedDate": "2020-02-29T14:13:50.983Z",
            "id": 1,
            "name": "John - 1582985630846",
            "gender": "M"
        },
        "requiredRevisionNumber": 32,
        "revisionInstant": "2020-02-29T14:13:51.045Z",
        "requiredRevisionInstant": "2020-02-29T14:13:51.045Z",
        "revisionNumber": 32
    },
    "empty": false
}
```

