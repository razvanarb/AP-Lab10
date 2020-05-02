import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * An instance of the ClientThread class will be responsible with the communication of a client.
 */

public class ClientThread extends Thread {
    private Socket socket = null;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            boolean stopped = false; // it's value represents the fact that the socket is closed or not
            String response = "";
            while (!stopped) {
                // Get the request from the input stream: client → server
                String request = in.readLine();
                System.out.println("Client sent " + request + " command");
                if (request.equals("exit")) {
                    stopped = true;
                    out.println("Client stopped");
                }
                if (request.equals("stop")) {
                    stopped = true;
                    out.println("Server stopped");
                    socket.close();
                } else {
                    response = "Server received the request " + request;
                }
                out.println(response);
                out.flush();
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}

