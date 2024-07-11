### README

# Multi-threaded Dictionary Server

## Introduction

This project is aimed at building a multi-threaded dictionary server that allows multiple clients to connect and perform operations such as search, add, update, and delete. The server handles all possible failures and ensures reliable communication using TCP sockets and threads.

## System Architecture

The system follows a client-server architecture:
- **Server**: Allows multiple clients to connect and perform operations concurrently.
- **Client**: Connects to the server to perform operations like search, add, delete, and update entries.

### Main Components:
1. **Dictionary**: Stores words and their meanings in a .txt file.
2. **Server**: Manages client connections and processes requests.
3. **Client**: Sends requests to the server and receives responses.

### Process:
1. The server opens a socket and listens for connections.
2. Upon receiving a connection request, a new thread (ClientHandler) is created to manage the client's requests.
3. The client sends requests, and the server processes these requests and sends back responses.

## Java Classes Design

### Server Side:
1. **DictionaryServer.java**:
   - Starts the server and listens for client connections.
   - Creates a new ClientHandler thread for each connection.

2. **ServerGUI.java**:
   - Provides a graphical interface displaying server status and logs.

3. **Dictionary.java**:
   - Manages the dictionary data, including loading, saving, querying, adding, deleting, and updating entries.

4. **ClientHandler.java**:
   - Manages interactions with a connected client in a separate thread.

### Client Side:
1. **DictionaryClient.java**:
   - Manages communication with the server using a Socket.
   - Sends requests and receives responses.

2. **ClientGUI.java**:
   - Provides a graphical interface for users to interact with the server.

## Protocols

The server and client communicate using a custom protocol based on text messages:

| Pattern        | Meaning                                 |
| -------------- | --------------------------------------- |
| `s&word`       | Search word and return its meanings     |
| `u&word%meaning` | Update word and return success/failure |
| `r&word`       | Remove word and return success/failure  |
| `a&word%meaning` | Add word with meaning and return success/failure |
| `E&reason`     | Exception. Show the string in a message dialog and end the app |
| `Q`            | Exception. End the app without any dialog |
| `S`            | The operation is successful             |
| `%meaning`     | Successfully get data from Dictionary   |

## Analysis

The server uses a thread-per-connection architecture for simplicity, although a worker pool architecture might be more efficient for real-world applications. TCP is used for reliable communication, and plain strings are used for message exchange, although JSON would be more suitable for a production system.

## Conclusion

The system successfully implements a multi-threaded dictionary server with capabilities like querying, adding, updating, and deleting words, along with a graphical user interface for both the server and client. For better format and structure, JSON could be used in future implementations.

## GUI

### Server:
- Displays server status, current port, and client operations.
- Includes a time box for monitoring and a "Stop Server" button to shut down the server.

### Client:
- Four buttons for search, add, update, and delete operations.
- Displays results or errors based on the operation.

## Failure Handling

Known exceptions are handled by message dialogs, and dictionary errors are shown in the console. Example errors include server disconnection and invalid operations on non-existent words.

---

This README provides a comprehensive overview of the multi-threaded dictionary server project, including system architecture, class design, protocols, and handling of operations and failures.
