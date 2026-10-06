import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    // Returns a Runnable that contains the client-side networking logic
    public Runnable getRunnable() throws UnknownHostException, IOException {

        // Anonymous implementation of the Runnable interface
        return new Runnable() {

            @Override
            public void run() {

                // Port number of the server
                int port = 8010;

                try {

                    // Get the IP address of the server
                    // "localhost" refers to the same computer
                    InetAddress address = InetAddress.getByName("localhost");

                    // Create a Socket and connect to the server
                    // Server must be running on port 8010
                    Socket socket = new Socket(address, port);

                    /*
                     * try-with-resources automatically closes:
                     * 1. PrintWriter
                     * 2. BufferedReader
                     *
                     * when this block finishes.
                     */
                    try (

                        // Used to send data from Client to Server
                        // 'true' enables auto-flushing
                        PrintWriter toSocket =
                                new PrintWriter(
                                        socket.getOutputStream(),
                                        true
                                );

                        // Used to receive data from Server
                        BufferedReader fromSocket =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()
                                        )
                                )
                    ) {

                        // Send a message to the server
                        // getLocalSocketAddress() gives the client's
                        // local IP address and port
                        toSocket.println(
                                "Hello from Client "
                                + socket.getLocalSocketAddress()
                        );

                        // Wait for the server's response
                        // readLine() blocks until the server sends a line
                        String line = fromSocket.readLine();

                        // Display the server's response
                        System.out.println(
                                "Response from Server " + line
                        );

                    } catch (IOException e) {

                        // Handle errors related to reading/writing
                        // data through the socket
                        e.printStackTrace();
                    }

                    // Socket is closed automatically when the
                    // try-with-resources block is completed

                } catch (IOException e) {

                    // Handle connection-related errors
                    e.printStackTrace();
                }
            }
        };
    }

    public static void main(String[] args) {

        // Create an instance of Client
        Client client = new Client();

        /*
         * Create 100 client threads.
         *
         * Each thread will:
         * 1. Connect to the server
         * 2. Send a message
         * 3. Receive the server's response
         * 4. Print the response
         */
        for (int i = 0; i < 100; i++) {

            try {

                // Get the Runnable containing the client logic
                // and create a new Thread
                Thread thread = new Thread(
                        client.getRunnable()
                );

                // Start the thread
                thread.start();

            } catch (Exception ex) {

                // If an exception occurs while creating
                // the client thread, stop the loop
                return;
            }
        }

        // End of main method
        return;
    }
}