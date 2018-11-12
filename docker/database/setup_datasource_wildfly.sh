#!/usr/bin/env bash

SRC="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

(
    mkdir -p ${JBOSS_HOME}modules/system/layers/base/org/postgresql/jdbc/main
    cd ${JBOSS_HOME}modules/system/layers/base/org/postgresql/jdbc/main
    curl -O https://jdbc.postgresql.org/download/postgresql-42.0.0.jar
    cp $SRC/module.xml $PWD
    cd ${JBOSS_HOME}bin
    cp $SRC/datasource-install.cli $PWD
    sh ${JBOSS_HOME}bin/jboss-cli.sh --file=${JBOSS_HOME}bin/datasource-install.cli
)