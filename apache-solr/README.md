## **Apache Solr Example**

##`Available Scripts`<br />
In the project directory, you can run:<br />
>_[Download](https://lucene.apache.org/solr/downloads.html) the Apache solr in local system as per OS_ 

>####_For Ubuntu OS Commands:_
#### Start Solr: 
    ./bin/solr start
#### Start Solr in the foreground: 
    ./bin/solr start -f
#### Stop Solr: 
    ./bin/solr stop
#### Restart Solr: 
    ./bin/solr restart
#### Check Logs: 
    tail -f server/logs/solr.log
#### Start Solr on a different port: 
    ./bin/solr start -p 2000
#### Create Core: 
    ./bin/solr create -c user_core
#### Delete Core:
    ./Solr delete -c my_core 
#### Solr Help: 
    ./bin/solr -help
#### Solr Status: 
    ./bin/solr status
#### Solr Admin:
    http://localhost:8983/solr/