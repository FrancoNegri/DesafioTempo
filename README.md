# REST API desafio tempo

This is a simple API that implements user sign-up, auth, and some simple operations using spring boot, hibernate and postgres.

API uses access token to authenticate users.

## Run the app

    docker-compose up
   
# REST API

## Sign up new user

### Request

`Post /login`

#### Body
```
{
  "username": "2",
  "password": "2"
}
```

### Response

```
{
    "id": 0
}
```

## Login user

### Request

`POST /login`

#### Body
```
{
  "username": "2",
  "password": "2"
}
```

### Response

```
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiMiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NTQ5NzU1NTMsImV4cCI6MTY1NDk3NjE1M30.ZQe1wneYNS0hHGG4MCn6vREUa77xkLIgZM7ViO7twUzIw1q4FS44Cn70Dn3W57EeCnGR3n2GE93o-TruBqrBlg"
}
```

## Sums two elements

### Request

`POST /sum`

#### Header

```
Authorization: *previously generated token*
```

#### Body
```
{
    "element1":2,
    "element2":1
}
```

### Response

```
{
    "result": 3
}
```

## Close session

### Request

`POST /close`

#### Header

```
Authorization: *previously generated token*
```

#### Body

```
{
  "username": "2",
  "password": "2"
}
```

### Response
```
{
    "response": "OK!"
}

```

## Get api call history 

### Request

`GET /history`

#### Body

```
{
  "limit":100,
  "offset":0
}
```


### Response

```
[
    {
        "id": 1,
        "endpoint": "/users",
        "result": "{\"id\":0}"
    },
    {
        "id": 2,
        "endpoint": "/login",
        "result": "{\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiMiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NTQ5NzU1NTMsImV4cCI6MTY1NDk3NjE1M30.ZQe1wneYNS0hHGG4MCn6vREUa77xkLIgZM7ViO7twUzIw1q4FS44Cn70Dn3W57EeCnGR3n2GE93o-TruBqrBlg\"}"
    },
    {
        "id": 3,
        "endpoint": "/sum",
        "result": "{\"result\":5}"
    },
    {
        "id": 4,
        "endpoint": "/sum",
        "result": "{\"result\":4}"
    },
    {
        "id": 5,
        "endpoint": "/close",
        "result": "{\"response\":\"OK!\"}"
    }
]

```
