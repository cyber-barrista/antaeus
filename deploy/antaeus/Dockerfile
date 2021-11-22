FROM adoptopenjdk/openjdk11:latest

RUN apt-get update && apt-get install -y sqlite3

ENV PROJECT_HOME /opt/antaeus

RUN mkdir -p $PROJECT_HOME

COPY * $PROJECT_HOME/

WORKDIR $PROJECT_HOME

CMD ./run-antaeus.sh
