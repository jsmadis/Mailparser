    #!/usr/bin/env bash

VERSION=$1

SRC="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

(
    cd $SRC/..
    mvn package -DskipTests
    cp parser/target/parser.war docker/mailparser/parser.war
    cp rest/target/rest.war docker/mailparser/rest.war
)

docker build -t "mailparser/mailparser:$VERSION" $SRC/mailparser
docker tag "mailparser/mailparser:$VERSION" mailparser/mailparser:latest


docker build -t "mailparser/database:$VERSION" $SRC/database
docker tag "mailparser/database:$VERSION" mailparser/database:latest