# Java TCP Client-Server

A Java-based TCP Client-Server application demonstrating **socket programming, TCP network communication, client-server interaction, input/output streams, and multithreaded client handling** using Java's `Socket` and `ServerSocket` APIs.

---

## 📌 Project Overview

This project demonstrates the fundamentals of **TCP socket programming in Java** by implementing communication between a client and a server.

The server listens for incoming TCP connections on port **8010**. Once a client connects, the client sends a message to the server, and the server reads the message and sends a response back to the client.

The project also demonstrates a **multithreaded server implementation**, where each connected client can be handled using a separate thread.

---

## 🚀 Features

- TCP Client-Server communication
- Java Socket Programming
- `ServerSocket` implementation
- `Socket` implementation
- Client connection handling
- Sending messages from Client to Server
- Sending responses from Server to Client
- `BufferedReader` for reading data
- `InputStreamReader` for converting byte streams to characters
- `PrintWriter` for sending text data
- Localhost communication
- Port-based communication
- Exception handling
- Socket connection management
- Multithreaded client handling
- Concurrent client connections
- Blocking socket operations
- Resource management and socket closure

---

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java | Programming Language |
| Java Socket API | Network Communication |
| `ServerSocket` | Server-side TCP connection |
| `Socket` | Client-server connection |
| `BufferedReader` | Reading incoming data |
| `InputStreamReader` | Converting byte streams |
| `PrintWriter` | Sending text data |
| Java Threads | Concurrent client handling |
| TCP | Network Protocol |
| JDK 26 | Java Development Kit |

---

## 📂 Project Structure

```text
Java-TCP-Client-Server/
│
├── Server.java
├── Client.java
├── README.md
└── .gitignore
```

---

## 🏗️ System Architecture

The application follows a basic **Client-Server Architecture**.

```text
                         TCP Connection
                                │
              ┌─────────────────┴─────────────────┐
              │                                   │
              ▼                                   ▼
       ┌──────────────┐                    ┌──────────────┐
       │    CLIENT    │                    │    SERVER    │
       │              │                    │              │
       │    Socket    │                    │ ServerSocket │
       └──────┬───────┘                    └──────┬───────┘
              │                                   │
              │──── Connect to Port 8010 ────────►│
              │                                   │
              │──── Send Message ────────────────►│
              │                                   │
              │◄──── Send Response ──────────────│
              │                                   │
              │──── Close Connection ─────────────►
              │                                   │
              └───────────────────────────────────┘
```

---

## 🔄 Communication Flow

The complete communication process is:

```text
1. Start the Server
        │
        ▼
2. Server creates ServerSocket
        │
        ▼
3. Server listens on port 8010
        │
        ▼
4. Client starts
        │
        ▼
5. Client creates Socket
        │
        ▼
6. Client connects to localhost:8010
        │
        ▼
7. Server accepts the connection
        │
        ▼
8. Client sends a message
        │
        ▼
9. Server reads the message
        │
        ▼
10. Server sends a response
        │
        ▼
11. Client reads the response
        │
        ▼
12. Client and Server close the connection
```

---

## 🔌 Server Implementation

The server uses `ServerSocket` to listen for incoming TCP connections.

```java
ServerSocket serverSocket = new ServerSocket(8010);
```

The server continuously waits for clients:

```java
while (true) {

    Socket clientSocket = serverSocket.accept();

    // Handle client
}
```

### 📥 Receiving Data on the Server

The server uses `BufferedReader` to receive the message sent by the client.

```java
BufferedReader fromClient =
        new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()
                )
        );

String clientMessage = fromClient.readLine();
```

Communication direction:

```text
Client
   │
   │ Message
   ▼
Server
```

### 📤 Sending Data from the Server

The server uses `PrintWriter` to send a response back to the client.

```java
PrintWriter toClient =
        new PrintWriter(
                clientSocket.getOutputStream(),
                true
        );

toClient.println("Hello World from the server");
```

Communication direction:

```text
Server
   │
   │ Response
   ▼
Client
```

---

## 💻 Client Implementation

The client connects to the server using the server's host and port.

```java
InetAddress address =
        InetAddress.getByName("localhost");

Socket socket =
        new Socket(address, 8010);
```

### Connection Details

```text
Host:     localhost
Port:     8010
Protocol: TCP
```

`localhost` refers to the same computer on which the client is running.

---

## 📤 Sending Data from the Client

The client sends a message to the server using `PrintWriter`.

```java
toSocket.println(
        "Hello World from socket "
        + socket.getLocalSocketAddress()
);
```

Communication:

```text
Client ──────────────────► Server

          Hello World
```

---

## 📥 Receiving Data from the Server

The client receives the server response using `BufferedReader`.

```java
String line = fromSocket.readLine();

System.out.println(
        "Server says: " + line
);
```

Communication:

```text
Server ──────────────────► Client

          Server Response
```

---

## 🧵 Multithreading

A multithreaded version of the server can create a separate thread for every connected client.

Example:

```java
Thread thread = new Thread(
        () -> server.getConsumer().accept(clientSocket)
);

thread.start();
```

This allows the server to handle multiple clients concurrently.

### Sequential Client Handling

Without multithreading:

```text
Client 1
   │
   ▼
Server
   │
   ▼
Finish Client 1
   │
   ▼
Client 2
   │
   ▼
Server
   │
   ▼
Finish Client 2
```

The server processes clients one after another.

### Multithreaded Client Handling

With multithreading:

```text
                    ┌──► Thread 1 ──► Client 1
                    │
Server ─────────────┼──► Thread 2 ──► Client 2
                    │
                    └──► Thread 3 ──► Client 3
```

Each client can be handled independently, improving the server's ability to handle multiple simultaneous connections.

---

## 🔑 Important Java Networking Classes

### 1. `ServerSocket`

`ServerSocket` is used on the server side to listen for incoming TCP connections.

```java
ServerSocket serverSocket =
        new ServerSocket(8010);
```

### 2. `Socket`

`Socket` represents a TCP connection between the client and server.

**Client:**

```java
Socket socket =
        new Socket(address, port);
```

**Server:**

```java
Socket clientSocket =
        serverSocket.accept();
```

### 3. `PrintWriter`

`PrintWriter` is used to send text data through the socket.

```java
PrintWriter writer =
        new PrintWriter(
                socket.getOutputStream(),
                true
        );
```

The `true` enables automatic flushing.

### 4. `BufferedReader`

`BufferedReader` is used to read text data received through the socket.

```java
BufferedReader reader =
        new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()
                )
        );
```

### 5. `InputStreamReader`

`InputStreamReader` converts byte streams into character streams.

```java
new InputStreamReader(
        socket.getInputStream()
);
```

### 6. `Thread`

`Thread` is used to execute client-handling operations concurrently.

```java
Thread thread =
        new Thread(() -> {
            // Client handling
        });

thread.start();
```

---

## 📊 Client-Server Data Flow

```text
                         CLIENT
                            │
                            │
                            │ Socket Connection
                            ▼
                     ┌─────────────┐
                     │   SERVER    │
                     │ Port: 8010  │
                     └──────┬──────┘
                            │
                ┌───────────┴───────────┐
                │                       │
                ▼                       ▼
          PrintWriter             BufferedReader
                │                       │
                │                       │
                │ Client → Server       │
                └───────────►           │
                                        │
                ◄───────────            │
                │                       │
                │ Server → Client       │
                │                       │
                ▼                       ▼
             Response                Message
```

---

# ▶️ How to Run the Project

## Prerequisites

Make sure Java is installed.

Check your Java version:

```bash
java -version
```

Check the Java compiler:

```bash
javac -version
```

This project was developed using:

```text
JDK 26
```

---

## Step 1: Clone the Repository

```bash
git clone https://github.com/<your-username>/<repository-name>.git
```

Navigate into the project directory:

```bash
cd <repository-name>
```

---

## Step 2: Compile the Java Files

```bash
javac Server.java Client.java
```

---

## Step 3: Start the Server

Run:

```bash
java Server
```

Expected output:

```text
Server started...
Server is listening on port: 8010
```

The server is now waiting for client connections.

---

## Step 4: Start the Client

Open another terminal in the same directory.

Run:

```bash
java Client
```

Expected output:

```text
Server says: Hello World from the server
```

---

## 🖥️ Example Server Output

```text
Server started...
Server is listening on port: 8010
Connected to /127.0.0.1:xxxxx
Client says: Hello World from socket /127.0.0.1:xxxxx
```

---

## 💻 Example Client Output

```text
Server says: Hello World from the server
```

---

## ⚙️ Configuration

The server listens on port `8010`.

```java
int port = 8010;
```

The client connects using the same port:

```java
int port = 8010;
```

The client uses:

```java
InetAddress.getByName("localhost");
```

Therefore:

```text
Server Host : localhost
Server Port : 8010
Protocol    : TCP
```

---

## 🧪 Testing

The project can be tested locally by running the server and client in separate terminals.

### Terminal 1 — Server

```bash
java Server
```

### Terminal 2 — Client

```bash
java Client
```

For the multithreaded implementation, multiple clients can be started to verify concurrent client handling.

---

## 🛡️ Exception Handling

The project uses Java's exception-handling mechanism to handle networking and I/O errors.

Example:

```java
try {
    server.run();
} catch (Exception ex) {
    ex.printStackTrace();
}
```

This helps prevent unexpected application termination and provides information about errors.

---

## 🔒 Resource Management

Sockets and streams should be closed after communication is completed.

Example:

```java
toSocket.close();
fromSocket.close();
socket.close();
```

Proper resource management helps prevent resource leaks and ensures that network connections are released after communication.

---

## 📚 Concepts Demonstrated

This project provides practical experience with:

- TCP/IP communication
- Socket programming
- Client-server architecture
- `ServerSocket`
- `Socket`
- Input streams
- Output streams
- `BufferedReader`
- `InputStreamReader`
- `PrintWriter`
- Java Threads
- Concurrent client handling
- Port-based communication
- Localhost networking
- Exception handling
- Resource management

---

## 👨‍💻 Author

**Shaurya Chattopadhyay**

B.Tech – Computer Science & Engineering (AI & ML)

---

## ⭐ Project Purpose

This project was developed to understand and demonstrate **Java networking fundamentals and TCP socket communication**, including how clients establish connections with servers, exchange messages, and how a server can handle multiple clients concurrently.
