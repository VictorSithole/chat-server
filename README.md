# Backend Websocket Chat Server
The WebSocket Chat Server is a real-time messaging application that allows users to
connect to a WebSocket URL using Basic Authentication. It provides the ability to send and 
receive messages in real-time through the WebSocket connection. Additionally, it offers 
RESTful endpoints to send, retrieve, and delete messages from the WebSocket server.

# Features
- Connect to a WebSocket URL using Basic Authentication
- Send messages to the WebSocket URL and receive immediate responses
- Retrieve and delete messages from the WebSocket server using RESTful endpoints

# Prerequisites
Before running the WebSocket Chat Server, ensure that the following requirements are met:
- Java Development Kit (JDK) 8 or higher is installed.
- Maven build tool is installed.
- Application properties file is configured with hardcoded username and password for the WebSocket connection.

# Installation
Follow the steps below to install and run the WebSocket Chat Server:<br>
1. Clone the repository:<br>
- git clone https://gitlab.com/Vickells/chat-server.git
2. Navigate to the project directory:
- cd chat-server
3. Build the project using Maven:
- mvn clean install
4. Run the application
- mvn spring-boot:run

# Configuration
Ensure that the following properties are set in the application.properties file:<br>

Basic Authentication Credentials<br>
spring.security.user.name=your-username<br>
spring.security.user.password=your-password

# Usage
WebSocket Connection<br>
1. The WebSocket Chat Server will attempt to connect to the WebSocket URL using the provided Basic Authentication credentials.
2. Once the connection is established, the server will be ready to send and receive messages.

# Sending Messages
To send a message to the WebSocket URL, perform a websocket request to the /websocket endpoint:

# Retrieving messages
- Includes server response immediately after sending messages on the websocket url
- Restful api using the GET request endpoint /api/chat-room/messages

# Deleting messages
- Restful api using the DELETE request endpoint api/chat-room/messages/{id}






