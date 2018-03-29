# text-util-service #
Text Util Service has apis to support create, read and deletion of text. It also provide an api to fetch sample json data. 

## Pre-Requisites #
Install JDK 1.8.0_131 or later
</br>
Install Apache Maven 3.3.9 or later

## Set Up #
1) Clone the repository in your local machine.
2) Go to the root folder of the project and run the following maven command to compile, run unit tests and </br>create the executable jar.
</br>  mvn clean package

## Usage #
**Curl command to create text**

curl -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' -d  '{"arbitrary_text": "hello"}' http://localhost:8080/util/v1/text

**Curl command to read text**

curl -X GET -H 'Accept: application/json' http://localhost:8080/util/v1/text/textId/<text id from create response>

**Curl command to delete text**

curl -X DELETE -H 'Content-Type: application/json' -H 'Accept: application/json' -d '{"text_id": "<text id from create response>"}' http://localhost:8080/util/v1/text

**Curl command to read sample json**

curl -X GET -H 'Accept: application/json' http://localhost:8080/util/v1/jsonplaceholder
