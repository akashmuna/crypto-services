## Crypto Service Details:

    1.  The Service is using IP based Rate Limit Bucket4j approach which restricts users to 50 times per hour call limit.(Configurable in Yaml)

    2.  Service is using Swagger API for Documentation of the Services. 
        The Swagger File(cryptoservices.yaml) can be uploaded [Swagger Editor](https://editor.swagger.io/) to check the API documentation

    3. Service can be containerized using docker images build and running the image on a container.

### Scope for Improvement:

    1. Test Coverage part of code coverage needs to be introduced to complete the code coverage aspect.

    2. Security of the services allowing role specific access.

    3. Visulation part of UI can be implemented using Spring thymeleaf or Angular UI

### How to run the service:

1. Maven build

    ```bash
    mvn clean package
	```

2. Build the docker image

   ```bash
   docker build -f Dockerfile -t cryptoservice:1.0 .
   ```
3. span the docker container with the mentioned env variable to work

   ```bash
   docker run -e DATABASE_SERVER=jdbc:h2:mem:testdb -e DIR_PATH=/tmp/prices/ -e ENV=docker -dp 8080:8080 cryptoservice:1.0
   ```

### Endpoints

Local System EndPoints:

    GET API Min/Max/old/new with Date Range Filters

        http://localhost:8080/web/v1/cryptoservices?symbol=BTC
    
        http://localhost:8080/web/v1/cryptoservices?symbol=BTC&startDate=2000-01-01&endDate=2023-01-01&maxFlag=true&minFlag=true&oldFlag=true&newFlag=true
    
        http://localhost:8080/web/v1/cryptoservices?startDate=2000-01-01&endDate=2023-01-01&maxFlag=true&minFlag=true&oldFlag=true&newFlag=true
   
        Sample response:

```json
      {
         "cryptoServicesMasterModelList": [
            {
               "id": null,
               "updatedDate": null,
               "symbol": "BTC",
               "price": null,
               "errorStatus": null,
               "compNormalizedPrice": null,
               "cryptoProp": {
                  "min": 33276.59,
                  "max": 47722.66,
                  "oldest": 38411.10,
                  "newest": 46813.21,
                  "normRange": null
               }
            },
            {
               "id": null,
               "updatedDate": null,
               "symbol": "XRP",
               "price": null,
               "errorStatus": null,
               "compNormalizedPrice": null,
               "cryptoProp": {
                  "min": 0.58,
                  "max": 0.85,
                  "oldest": 0.59,
                  "newest": 0.59,
                  "normRange": null
               }
            },
            {
               "id": null,
               "updatedDate": null,
               "symbol": "ETH",
               "price": null,
               "errorStatus": null,
               "compNormalizedPrice": null,
               "cryptoProp": {
                  "min": 2336.52,
                  "max": 3828.11,
                  "oldest": 2540.20,
                  "newest": 3715.32,
                  "normRange": null
               }
            },
            {
               "id": null,
               "updatedDate": null,
               "symbol": "DOGE",
               "price": null,
               "errorStatus": null,
               "compNormalizedPrice": null,
               "cryptoProp": {
                  "min": 0.13,
                  "max": 0.19,
                  "oldest": 0.14,
                  "newest": 0.14,
                  "normRange": null
               }
            },
            {
               "id": null,
               "updatedDate": null,
               "symbol": "LTC",
               "price": null,
               "errorStatus": null,
               "compNormalizedPrice": null,
               "cryptoProp": {
                  "min": 104.10,
                  "max": 151.10,
                  "oldest": 109.60,
                  "newest": 148.10,
                  "normRange": null
               }
            }
         ]
      }
```

    GET API to get normalized Ranges of Crypto

        http://localhost:8080/web/v1/cryptoservices/normalized?highestFlag=true

        http://localhost:8080/web/v1/cryptoservices/normalized?startDate=2000-01-01&endDate=2023-01-01

        Sample response:

```json
            {
                "cryptoServicesMasterModelList": [
                    {
                        "id": "45dac7f7-552b-426b-adf1-2ea53a230dfc",
                        "updatedDate": "2022-01-02 05:30:00.0",
                        "symbol": "BTC",
                        "price": 47722.66,
                        "errorStatus": null,
                        "compNormalizedPrice": 20520.7438,
                        "cryptoProp": {
                            "min": 33276.59,
                            "max": 47722.66,
                            "oldest": null,
                            "newest": null,
                            "normRange": 0.43
                        }
                    }
                ]
            }
```
    POST API to add new Record

        http://localhost:8080/web/v1/cryptoservices

        Body:
```json
            {
                "id": null,
                "updatedDate": "2022-02-25 01:21:32.0",
                "symbol": "BTC",
                "price": 38418.10,
                "errorStatus": null
            }
```