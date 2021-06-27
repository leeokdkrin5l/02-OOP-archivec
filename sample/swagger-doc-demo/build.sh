#!/usr/bin/env bash
mvn clean -U process-resources package -Dmaven.test.skip=true
cp target/swagger-doc-demo-1.0-SNAPSHOT.jar  source