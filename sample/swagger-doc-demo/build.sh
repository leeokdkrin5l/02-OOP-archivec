#!/usr/bin/env bash
mvn clean -U process-resources package -Dmaven.test.skip=true
mkdir source
cp target/swagger-doc-demo-1.0-SNAPSHOT-sources.jar  source