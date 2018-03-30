# text-util-service #
Text Util Service has apis to support create, read and deletion of text. It also provide an api to fetch sample json data. The service uses an in-memory cassandra database.
> **Warning: The data won't be persisted to disk **</br></br>
Api Specification is available here **https://text-util-service-specifications-1.api-docs.io/1.0/util/read-text**

## Pre-Requisites #
Install JDK 1.8.0_XXX or later
</br>
Install Apache Maven 3.3.9 or later

## Set Up #
1) Clone this repository in to your local machine.
2) Go to the root folder of your project and run the following maven command to compile, run unit tests and </br>to create the executable jar.</br>
**mvn clean package**

## Application Start Up #
1) Go to the target folder of your project and execute the following command. This will start an embedded tomcat tomcat container, initializes the in-memory cassandra data base and creates schema.</br>
**./text-service-util-1.0.0.jar**

## Usage #
**Curl command to create text**

curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -d  '{"arbitrary_text": "hello"}' http://localhost:8080/util/v1/text

**Curl command to read text**

curl -X GET -H 'Accept: application/json' http://localhost:8080/util/v1/text/textId/[text id from create response]

**Curl command to delete text**

curl -X DELETE -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{"text_id": "[text id from create response]"}' http://localhost:8080/util/v1/text

**Curl command to read sample json**

curl -X GET -H 'Accept: application/json' http://localhost:8080/util/v1/jsonplaceholder

## Deploying Service to AWS using EB  CLI #
1) Create an AWS account, if you don't have one
2) Set up EB CLI
3) Go to the root folder of your project and execute the following command. This will deploy the service to AWS </br>
**eb create --envvars PORT=8080**
4) Use the following command to check the status of the deployment</br>
**eb status text-util-service**
5) Use the following curl commands to access the application when it is ready.

curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -d  '{"arbitrary_text": "hello"}' http://[load balancer name]/util/v1/text</br>

For example: curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -d  '{"arbitrary_text": "hello"}' http://text-util-service-dev.us-east-1.elasticbeanstalk.com/util/v1/text </br>

curl -X GET -H 'Accept: application/json' http://[load balancer name]/util/v1/text/textId/[text id from create response]</br>

curl -X DELETE -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{"text_id": "[text id from create response]"}' http://[load balancer name]/util/v1/text</br>


curl -X GET -H 'Accept: application/json' http://[load balancer name]/util/v1/jsonplaceholder
