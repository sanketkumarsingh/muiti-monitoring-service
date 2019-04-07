This Java project mainly contains 4 services which are brought up on localhost on different ports and a monitor which polls the services 
based on caller/consumer configuration.

The main components includes:
1. Services
	a.	It contains 4 services which are hard-coded at port: 8001, 8002, 8003 and 8004 at localhost. The services have attribute 
    	such as id, name, port, host (ipaddress or localhost), grace period.
	b.  The services must be brought up first else the monitoring status will be down. 
	c.	Services send status 
		i) UP - if service is up
		ii) DOWN -  if service is down
		iii) OUTAGE -  if caller doesn't require notification for a particular service.

2.  Monitor
    a.	It allows consumer or caller to enter the details such which service they want notification. In the implementation,
        when called runs the Main.java, it gets the notification for the services printed on the console. 
    b.	Main.java can be run in multiple console for different callers to simulate the multple caller scenarios.
    c.	For a caller, program requires:
        i)	for which services they want notification
        ii)	for each of those services, what is the polling frequency they need.
        iii) for each of those services, what is outage start timestamp and end timestamp.


How To run the project:
1. Prerequisite: It assumed the system have:
	a.	Java 8
	b.	Maven home set in PATH variable.

2. a) Go to location in command prompt or terminal where you extracted the project (let's say the location is /workspace):
	      /workspace/global-relay-assignment
   b) Run mvn clean install

3. Bring up the services
	a)	Go to location in command prompt or terminal where you extracted the project (let's say the location is /workspace):
	      /workspace/global-relay-assignment and run mvn exec:java -Dexec.mainClass="action.StartServices"
	b)	The sample output is shown below:
	
	[INFO] Scanning for projects...
	[INFO]                                                                         
	[INFO] ------------------------------------------------------------------------
	[INFO] Building global-relay-assignment 0.0.1-SNAPSHOT
	[INFO] ------------------------------------------------------------------------
	[INFO] 
	[INFO] --- exec-maven-plugin:1.4.0:java (default-cli) @ global-relay-assignment ---
	Enter the services you want to be up to test monitoring. There are four service currently with id's: 1,2,3 and 4.  Enter the service ids in comma separated format (ex: 1,2,4)
	1,2,3,4
	service status: serviceD is Up and listening..
	service status: serviceB is Up and listening..
	service status: serviceC is Up and listening..
	service status: serviceA is Up and listening..
	
4.	Start the monitor
	a)	Open a new terminal and go to location: /workspace/global-relay-assignment and run 
		mvn exec:java -Dexec.mainClass="main.Main"
	b)  The sample output is shown below:
	
		[INFO] Scanning for projects...
		[INFO]                                                                         
		[INFO] ------------------------------------------------------------------------
		[INFO] Building global-relay-assignment 0.0.1-SNAPSHOT
		[INFO] ------------------------------------------------------------------------
		[INFO] 
		[INFO] --- exec-maven-plugin:1.4.0:java (default-cli) @ global-relay-assignment ---
		Enter caller id
		1
		Enter service details for which notification is required.
		Do you want to enter a service detail: (Y/N)
		Y
		Enter one of the service id. Supported ids: 1,2,3 or 4.(required):
		1
		Enter polling freq. in seconds for the service. enter 0 if to be polled continously. (required).
		5
		Enter outage start date in format: dd/MM/yyyy-HH:mm:ss.SSS (optional, skip by entering N)
		N
		Enter outage end date in format: dd/MM/yyyy-HH:mm:ss.SSS (optional, skip by entering N)
		N
		Do you want to enter next service detail: (Y/N)
		Y
		Enter one of the service id. Supported ids: 1,2,3 or 4.(required):
		3
		Enter polling freq. in seconds for the service. enter 0 if to be polled continously. (required).
		10
		Enter outage start date in format: dd/MM/yyyy-HH:mm:ss.SSS (optional, skip by entering N)
		N
		Enter outage end date in format: dd/MM/yyyy-HH:mm:ss.SSS (optional, skip by entering N)
		N
		Do you want to enter next service detail: (Y/N)
		N
		Starting to poll for callerId:1
		Received status from service: Is serviceA Up? Ans: Yes
		Received status from service: Is serviceC Up? Ans: Yes
		Polling Count:1. Service:serviceA Current Timestamp:2019-03-17 20:02:41.908 Status:up
		Polling Count:1. Service:serviceC Current Timestamp:2019-03-17 20:02:41.908 Status:up
		===========================================
		Received status from service: Is serviceA Up? Ans: Yes
		Polling Count:2. Service:serviceA Current Timestamp:2019-03-17 20:02:46.951 Status:up
		===========================================
		Received status from service: Is serviceC Up? Ans: Yes
		===========================================
		Polling Count:2. Service:serviceC Current Timestamp:2019-03-17 20:02:51.954 Status:up
		Received status from service: Is serviceA Up? Ans: Yes
		Polling Count:3. Service:serviceA Current Timestamp:2019-03-17 20:02:51.98 Status:up
		===========================================
		Received status from service: Is serviceA Up? Ans: Yes
		Polling Count:4. Service:serviceA Current Timestamp:2019-03-17 20:02:57.007 Status:up
		
   c) Understanding the output:
      i)	After the user input, Monitor creates 'n' thread to poll 'n' services and each thread, keep polling 
      		the respective service after each polling frequency duration.
      ii)	The output line like: 
      		"Polling Count:1. Service:serviceA Current Timestamp:2019-03-17 20:02:41.908 Status:up" - means
      		that for the first poll for serviceA, the status was UP.
      iii)	On service side, output such as below might be seen:
			Receiving message from 127.0.0.1: Is serviceC Up?
			Receiving message from 127.0.0.1: Is serviceA Up?
			Receiving message from 127.0.0.1: Is serviceA Up?
			Receiving message from 127.0.0.1: Is serviceC Up?
			
			This means, the service received the message send by monitor thread asking whether the service is up.
			
			
Implementation overview:
1. The implementation uses server Socket to send message. The monitor acts as client, sends message.
   The server replies with response and acts as services.
2. When we bring up the services, n services are brought up by n threads. (here n=4, but it can changed easily).
3. When we run Monitor, 'm' threads corresponding to 'm' services (m<n) are created which polls the services by performing 
   message communication. If message handshake is successful, then status is send as UP otherwise DOWN.
    