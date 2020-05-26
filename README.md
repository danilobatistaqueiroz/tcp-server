# TCP Server

**Running the jar file**  

You can download the jar file and run:  
Download the jar file in: https://github.com/danilobatistaqueiroz/tcp-server/blob/master/build/distributions/prova.zip   
Extract the file   

Run the server (choose a port, eg. 8888):  
Using Linux:  `./prova/bin/prova 8888`   
Using Windows:  `prova\bin\prova 8888`   

Run the client:  
Open another shell and type: `telnet localhost 8888`  
Type the querylength and the query, for example: `3:mib`  
To exit, type: `.`  

> If you don't have a telnet client:   
> On Windows, you can use a tool, eg. Putty:  
> https://putty.org/  
> https://blog.octanetworks.com/how-to-telnet-using-putty/  
> On Ubuntu Linux, you can install using:  
> `sudo apt-get update && apt-get install telnet` 


**Building and running**  
You can download the source code, compile and run:  
Enter in a command shell and type:  
`git clone https://github.com/danilobatistaqueiroz/tcp-server.git`  

Enter in the directory:  
`cd tcp-server`  

Type (where 8888 is the port):  
`gradle build run --args=8888`  

Open another shell and type:  
`telnet localhost 8888`  

Type the querylength and the query, for example:  
`3:mib`  

To exit, type .  
`.`  

**How to compile, test and analyze with sonarqube**  
Enter in the directory and type:  
`gradle build jacocoTestReport` 

Start a sonarqube server  

Update the informations to sonarqube:  
`gradle sonarqube` 

Open a browser and navigate to sonarqube dashboard