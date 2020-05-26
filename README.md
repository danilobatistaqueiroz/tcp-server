# TCP Server

**Running using the jar file**  

You can download the jar file and run:  
Download the jar file in: https://github.com/danilobatistaqueiroz/tcp-server/build/distributions/prova.zip   
Extract the file   

Run the server (choose a port, eg. 8888):  
Using Linux:  `./prova/bin/prova 8888`   
Using Windows:  `prova\bin\prova 8888`   

Run the client:  
Open another shell and type: `telnet localhost 8888`  
Type the querylength and the query, for example: `3:mib`  
To exit, type: `.`  


**Building and running**  
You can download the source code, compile and run:  
enter in a command shell and type:  
`git clone https://github.com/danilobatistaqueiroz/tcp-server.git`  

enter in the directory:  
`cd tcp-server`  

type (where 8888 is the port):  
`gradle build run --args=8888`  

open another shell and type:  
`telnet localhost 8888`  

type the querylength and the query, for example:  
`3:mib`  

to exit, type .  
`.`  

**How to compile, test and analisy with sonarqube**  
enter in the directory and type:  
`gradle build jacocoTestReport` 

start a sonarqube server  

update the informations to sonarqube:  
`gradle sonarqube` 

open a browser and navigate to sonarqube dashboard