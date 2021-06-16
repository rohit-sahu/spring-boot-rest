###### **Start the Apache kafka using below commands in linux**

`cd Downloads/`

`wget http://www-us.apache.org/dist/kafka/2.4.0/kafka_2.13-2.4.0.tgz`

`mkdir ~/Downloads/kafka && cd ~/Downloads/kafka`

`tar xzf kafka_2.13-2.4.0.tgz`

`cd ~/Downloads/kafka`

`tar -xvzf ~/Downloads/kafka_2.13-2.4.0.tgz --strip 1`

`nano config/server.properties`

_**Use below commands starting the kafka as service**_

`service --status-all`

`cd ../../Downloads/kafka/bin`

`sh zookeeper-server-start.sh` 

`sh zookeeper-server-stop.sh`

`sh zookeeper-server-start.sh`

_**Use below commands starting the kafka on console**_

`kafka/bin/zookeeper-server-start.sh kafka/config/zookeeper.properties`

`kafka/bin/zookeeper-server-stop.sh kafka/config/zookeeper.properties`

`kafka/bin/kafka-server-start.sh kafka/config/server.properties`

`kafka/bin/kafka-server-stop.sh kafka/config/server.properties`

**OR**

`./bin/zookeeper-server-start.sh ./config/zookeeper.properties & ./bin/kafka-server-start.sh ./config/server.properties`

_**Use below commands to check the topic list**_

`kafka/bin/kafka-topics.sh — list — zookeeper localhost:2181`

`kafka/bin/kafka-topics.sh --list --zookeeper localhost:2181`

_**Use below commands if causing issue while starting the kafka**_

`ps -ef | grep zookeeper`

`pkill -9 -f zookeeper`

`ps -ef | grep zookeeper`

_**Use below commands to configure ssl**_

`openssl req -new -x509 -keyout ca-key -out ca-cert -days 3650`

`keytool -keystore kafka.server.truststore.jks -alias CA -import -file ca-cert`

`keytool -keystore kafka.server.keystore.jks -alias localhost -validity 3650 -genkey -keyalg RSA -ext SAN=dns:kafka-broker0.de.net`

`keytool -keystore kafka.server.keystore.jks -alias localhost -certreq -file ca-request-broker0`

`openssl x509 -req -CA ca-cert -CAkey ca-key -in ca-request-broker0 -out ca-signed-broker0 -days 3650 -CAcreateserial`

`keytool -keystore kafka.server.keystore.jks -alias CA -import -file ca-cert`

`keytool -keystore kafka.server.keystore.jks -alias localhost -import -file ca-signed-broker0`

