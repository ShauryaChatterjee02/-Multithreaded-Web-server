import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // Method responsible for starting the server
    // and handling client connections
    public void run() throws IOException {

        // Port number on which the server will listen
        int port = 8010;

        // Create a ServerSocket and bind it to port 8010
        // The server will wait for incoming client connections
        ServerSocket serverSocket = new ServerSocket(port);

        // Display server startup messages
        System.out.println("Server started...");
        System.out.println("Server is listening on port: " + port);

        // Keep the server running continuously
        while (true) {

            // Wait for a client to connect
            // accept() is a blocking method, meaning the program
            // waits here until a client connects
            Socket clientSocket = serverSocket.accept();

            // Display information about the connected client
            // getRemoteSocketAddress() returns the client's
            // IP address and port number
            System.out.println(
                "Connected to " +
                clientSocket.getRemoteSocketAddress()
            );

            // Create PrintWriter to send data from
            // Server -> Client
            //
            // 'true' enables auto-flush, so println()
            // immediately sends the message
            PrintWriter toClient =
                    new PrintWriter(
                            clientSocket.getOutputStream(),
                            true
                    );

            // Create BufferedReader to receive data from
            // Client -> Server
            //
            // InputStreamReader converts bytes received
            // from the socket into characters
            //
            // BufferedReader allows us to read the data
            // efficiently, especially using readLine()
            BufferedReader fromClient =
                    new BufferedReader(
                            new InputStreamReader(
                                    clientSocket.getInputStream()
                            )
                    );

            // Read the message sent by the client
            //
            // readLine() waits until the client sends
            // a line of text
            String clientMessage = fromClient.readLine();

            // Display the message received from the client
            System.out.println(
                "Client says: " + clientMessage
            );

            // Send a response back to the client
            toClient.println(
                "Hello World from the server"
            );

            // Close the output stream
            toClient.close();

            // Close the input stream
            fromClient.close();

            // Close the connection with the client
            clientSocket.close();
        }
    }

    public static void main(String[] args) {

        // Create an object of the Server class
        Server server = new Server();

        try {

            // Start the server
            server.run();

        } catch (Exception ex) {

            // Handle any exception that occurs
            ex.printStackTrace();
        }
    }
}