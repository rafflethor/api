## api

[![Build Status](https://travis-ci.org/rafflethor/api.svg?branch=master)](https://travis-ci.org/rafflethor/api)

### Description

`api` is a Groovy GraphQL api module. It uses Ratpack.io to expose the
GraphQL engine.

### Run api

In order to run the api you can execute:

```shell
./gradlew run
```

It serve the GraphQL engine at `http://localhost:5050/graphql`

### GraphiQL endpoint

Once the api is running GraphiQL is available at
`http://localhost:5050/graphql/browse`
