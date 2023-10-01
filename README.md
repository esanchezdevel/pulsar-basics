# Pulsar-Basics

A project where you can find basic examples of how to use Apache Pulsar

### How to install Pulsar with Docker:

Run the following command in your terminal to run a container within a standalone Pulsar Cluster inside.

`docker run -it -p 6650:6650 -p 8080:8080 --mount source=pulsardata,target=/pulsar/data --mount source=pulsarconf,target=/pulsar/conf --name pulsar-cluster apachepulsar/pulsar:3.1.0 bin/pulsar standalone`

### Pulsar commands:

- List all tenants:

`./bin/pulsar-admin tenants list`

- List all namespaces of each tenant:

`./bin/pulsar-admin namespaces list <tenant>`

- List all topics of each /tenant/namespace:

`./bin/pulsar-admin topics list public/default`

- List Schemas of one topic:

`./bin/pulsar-admin schemas get persistent://<tenant>/<namespace>/<topic>`

### Pulsar notes:

When we produce a message in a topic without specify the tenant and the namespace, the message will be produced in the tenant **public** and namespace **default**
