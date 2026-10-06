import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    // Method responsible for connecting to the server,
    // sending a message, and receiving the server's response
    public void run() throws IOException {

        // Port number on which the server is running
        int port = 8010;

        // Get the IP address of the server
        // "localhost" means the server is running
        // on the same computer
        InetAddress address =
                InetAddress.getByName("localhost");

        // Create a Socket and connect to the server
        // using the server's IP address and port number
        Socket socket =
                new Socket(address, port);

        // Create PrintWriter to send data
        // from Client -> Server
        //
        // 'true' enables auto-flush, so println()
        // immediately sends the message
        PrintWriter toSocket =
                new PrintWriter(
                        socket.getOutputStream(),
                        true
                );

        // Create BufferedReader to receive data
        // from Server -> Client
        //
        // InputStreamReader converts the incoming
        // bytes into characters
        BufferedReader fromSocket =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()
                        )
                );

        // Send a message to the server
        //
        // getLocalSocketAddress() returns the client's
        // local IP address and dynamically assigned port
        toSocket.println(
                "Hello World from socket "
                + socket.getLocalSocketAddress()
        );

        // Wait for and read the response sent by the server
        //
        // readLine() is a blocking operation and waits
        // until the server sends a line of text
        String line = fromSocket.readLine();

        // Display the response received from the server
        System.out.println(
                "Server says: " + line
        );

        // Close the output stream
        toSocket.close();

        // Close the input stream
        fromSocket.close();

        // Close the socket connection
        socket.close();
    }

    public static void main(String[] args) {

        // Create an object of the Client class
        Client client = new Client();

        try {

            // Start the client
            client.run();

        } catch (Exception ex) {

            // Handle any exception that occurs
            ex.printStackTrace();
        }
    }
}