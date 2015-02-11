# Camel REST & CRUD Quickstart

## Curl

### Get

    curl http://localhost:9090/rest/customerservice/customers/123

### Post

    curl -i -H "Content-Type: application/json" -X POST -d '{"Customer":{"name":"Charles"}}' http://localhost:9090/rest/customerservice/customers

### Put

    curl -i -H "Content-Type: application/json" -X POST -d '{"Customer":{"id":124,name":"Pauline"}}' http://localhost:9090/rest/customerservice/customers

### Delete

      curl -X DEL http://localhost:9090/rest/customerservice/customers/123

## HTTPie

HTTPie is a command line HTTP client, a user-friendly cURL replacement.
http://httpie.org

### Get

    http GET http://localhost:9090/rest/customerservice/customers/123
    http GET http://localhost:9090/rest/customerservice/customers/124

### Post

    echo '{"Customer":{"name":"Charles"}}' | http POST http://localhost:9090/rest/customerservice/customers

### Put

    echo '{"Customer":{"id":124,"name":"Puline"}}' | http POST http://localhost:9090/rest/customerservice/customers

### Delete

    http DELETE http://localhost:9090/rest/customerservice/customers/124





