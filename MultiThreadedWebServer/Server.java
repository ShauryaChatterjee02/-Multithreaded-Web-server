import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    // Returns a Consumer that handles communication with a client socket
    public Consumer<Socket> getConsumer() {

        return (clientSocket) -> {

            try (
                // Create PrintWriter to send data to the client
                // 'true' enables auto-flushing after println()
                PrintWriter toSocket =
                        new PrintWriter(clientSocket.getOutputStream(), true)
            ) {

                // Send a welcome message to the connected client
                // getInetAddress() returns the client's IP address
                toSocket.println(
                        "Hello from server " + clientSocket.getInetAddress()
                );

            } catch (IOException ex) {

                // Handle input/output errors
                ex.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {

        // Port number on which the server will listen
        int port = 8010;

        // Create an instance of the Server class
        Server server = new Server();

        try {

            // Create a ServerSocket and bind it to port 8010
            // This allows the server to accept incoming connections
            ServerSocket serverSocket = new ServerSocket(port);

            // Set a timeout of 70 seconds for the accept() operation
            serverSocket.setSoTimeout(70000);

            // Display a message indicating that the server has started
            System.out.println(
                    "Server is listening on port " + port
            );

            // Continuously wait for client connections
            while (true) {

                // Wait until a client connects
                // accept() blocks until a connection is received
                Socket clientSocket = serverSocket.accept();

                // Create a new thread for each connected client
                // This allows the server to handle multiple clients
                // simultaneously without blocking other connections
                Thread thread = new Thread(
                        () -> server.getConsumer().accept(clientSocket)
                );

                // Start the client-handling thread
                thread.start();
            }

        } catch (IOException ex) {

            // Handle errors related to networking
            ex.printStackTrace();
        }
    }
}