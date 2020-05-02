import javax.net.ssl.HandshakeCompletedEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * GameServer class with it's constructor will create a ServerSocket at a specified port and after a client is connected, executes the commands given by them.
 */
public class GameServer {

    int PORT = 8100; // The server's port
    ServerSocket serverSocket = null;

    public GameServer() throws IOException {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println("Waiting for client...");
                Socket socket = serverSocket.accept();
                new ClientThread(socket).start();
            }
        } catch (IOException e) {
            System.err.println("Couldn't connect " + e);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println("Couldn't close socket " + e);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
    }
}



