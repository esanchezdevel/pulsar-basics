# Pulsar-Basics

A project where you can find basic examples of how to use Apache Pulsar

### How to install Pulsar with Docker:

Run the following command in your terminal to run a container within a standalone Pulsar Cluster inside.

`docker run -it -p 6650:6650 -p 8080:8080 --mount source=pulsardata,target=/pulsar/data --mount source=pulsarconf,target=/pulsar/conf --name pulsar-cluster apachepulsar/pulsar:3.1.0 bin/pulsar standalone`

### Pulsar commands:

Enter into the docker container executing the following command:

`docker exec -it pulsar-cluster bash`

Once you are in the docker container, then you can run the following commands to get Pulsar data

- List all tenants:

`./bin/pulsar-admin tenants list`

- List all namespaces of each tenant:

`./bin/pulsar-admin namespaces list <tenant>`

- List all topics of each /tenant/namespace:

`./bin/pulsar-admin topics list public/default`

- List Schemas of one topic:

`./bin/pulsar-admin schemas get persistent://<tenant>/<namespace>/<topic>`

- List clusters:

`bin/pulsar-admin clusters list`

### Pulsar API

**API Documentation:** `https://pulsar.apache.org/admin-rest-api/`

- List all tenants:

`curl -vv http://localhost:8080/admin/v2/tenants`

- List all namespaces of one specific tenant:

`curl -vv http://localhost:8080/admin/v2/namespaces/<tenant>`

- List all persistent topics of each /tenant/namespace:

`curl -vv http://localhost:8080/admin/v2/persistent/<tenant>/<namespace>`

- List all NON persistent topics of each /tenant/namespace:

`curl -vv http://localhost:8080/admin/v2/non-persistent/<tenant>/<namespace>`

- List Schemas of one topic:

`curl -vv http://localhost:8080/admin/v2/schemas/public/default/test-topic/schema`

### Pulsar notes:

When we produce a message in a topic without specify the tenant and the namespace, the message will be produced in the tenant **public** and namespace **default**
