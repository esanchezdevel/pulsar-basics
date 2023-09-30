# Pulsar-Basics

A project where you can find basic examples of how to use Apache Pulsar

### How to install Pulsar with Docker:

Run the following command in your terminal to run a container within a standalone Pulsar Cluster inside.

`docker run -it -p 6650:6650 -p 8080:8080 --mount source=pulsardata,target=/pulsar/data --mount source=pulsarconf,target=/pulsar/conf --name pulsar-cluster apachepulsar/pulsar:3.1.0 bin/pulsar standalone`