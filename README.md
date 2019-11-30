# Java-RMI-Calculator

Simple Calculator with a Swing GUI, made to demonstrate the functionality of the Java Remote Method Invocation API. 

How it Works:

- First the server class is run, which implements the Interface class containing the calculator functions.
- The client then connectes to this server, where it can issue requests to the server (only 2 numbers and an operator).
- The server overrides the interface functions to provide the logic of the calculator. 
- Once the logic is performed, the result is returned to the client.
