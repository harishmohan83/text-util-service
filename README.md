# text-util-service #
Text Util Service has apis to support creation, read and deletion of text. It also provide an api to fetch sample json data. The service uses an in-memory cassandra database.
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
