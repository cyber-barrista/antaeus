## Antaeus

Antaeus (/ænˈtiːəs/), in Greek mythology, a giant of Libya, the son of the sea god Poseidon and the Earth goddess Gaia.
He compelled all strangers who were passing through the country to wrestle with him. Whenever Antaeus touched the
Earth (his mother), his strength was renewed, so that even if thrown to the ground, he was invincible. Heracles, in
combat with him, discovered the source of his strength and, lifting him up from Earth, crushed him to death.

## Design overview

The service is broken down into two parts:

### Antaeus

No more than a thin REST layer over a SQLite database. It exposes a couple of GET endpoints for querying information
about customers and their invoices. More importantly it does expose a `pay` POST endpoint triggering which leads to
calling a third party payment provider aimed to process pending invoices.

### Payment manager

A service that makes use of `antaeus`'s API triggering its `pay` endpoint by some sort of schedule. For now implemented
as a simple cron job.

WARNING: Not a production ready solution by any means. Added for demonstration purposes only!

## Deployment / running guide

### Overview

We have two microservices (`antaeus` and `payment manager`) interacting with each other over the network. Perfect case
for containerization, for now it's powered by `docker` and `docker-compose` so those are required for local testing /
production deployment.

### Synopsys

+ Install `docker` and `docker-compose` CLI tools somehow
+ `cd deploy`
+ `./build-images.sh`
+ `docker-compose up -d`

### Troubleshooting

If something doesn't work first thing make sure that

+ All `sh` files under `deploy` folder got executable permission. If not do `sudo chmod +x [path to sh file]`
+ Make sure port `7000` is not used by something else. To find out if it's the case
  do `sudo netstat -plnt | grep 7000` (Note: you might need to install `netstat` by yourself)
