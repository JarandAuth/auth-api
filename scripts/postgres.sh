#!/bin/bash

DB=auth-api-db
NET=jarand-net

docker rm -f $DB || true

docker network create $NET || true

docker run -d --name $DB --network $NET -p 5432:5432 \
  -e POSTGRES_DB=auth-api-db \
  -e POSTGRES_USER=auth-api-dbo \
  -e POSTGRES_PASSWORD=postgres \
  postgres:latest
