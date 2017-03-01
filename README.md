# Spring Cloud Stream PoC

Small proof of concept using Spring Cloud Stream as a framework for building message-driven microservice applications.

There are three different types of services:

- ***Emitter***: emits a random number in JSON format to our broker.
- ***Enricher***: aggregate application that:
  - filters odd numbers.
  - enriches the random number with a timestamp.
  - clones enriched DTO (random number times).
- ***Consumer***: consumes a JSON representation of the enricher output.

![Diagram](diagram.png?raw=true "Diagram")

## Prerequisites

### tmux

Only needed if you want to run the startup script:

You can install it via [Homebrew](https://brew.sh/#)

``brew install tmux``


### RabbitMQ

This project is using RabbitMQ as a broker.

You can install it via [Homebrew](https://brew.sh/#)

``brew install rabbitmq``

Or using the official docker image:

- Install [Docker](https://www.docker.com/products/overview)
- Run the following command to pull from Docker official repository

  ``docker run --rm -d -p 5672:5672 rabbitmq:3``

## How to test it?

Execute the following command to startup all microservices instances (emitter x3, enricher )

  ``run.sh``
