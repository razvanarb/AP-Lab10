import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * An instance of the GameClient class will read commands from the keyboard and it will send them to the server.
 */


public class GameClient {

    String serverAddress = "127.0.0.1";
    int PORT = 8100;


    private GameClient() throws IOException {
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))) {
            // Send a request to the server
            String response = "";
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter your name: ");
            String request = scanner.nextLine();
            out.println(request);
            boolean stopped = false;
            while (!stopped) {
                request = scanner.nextLine();
                if (request.equals("new game")) {
                    out.println(request);
                    response = in.readLine();
                    System.out.println(response);
                }
                if (response.equals("Insert game number: ")) {
                    request = scanner.nextLine();
                    out.println(request);
                    response = in.readLine();
                }
                request = scanner.nextLine();
                out.println(request);
                response = in.readLine();
                System.out.println(response);
                request = scanner.nextLine();
                out.println(request);
                response = in.readLine();
                System.out.println(response);
                if (response.equals("The move has been made successfully")) {
                    for (int i = 0; i < 16; i++) {
                        response = in.readLine();
                        System.out.println(response);
                    }
                }
            }
            response = in.readLine();
            System.out.println(response);
        } catch (IOException e) {
            System.err.println("Didn't connect to server " + e);
        }
    }

    public static void main(String[] args) {
        try {
            GameClient client = new GameClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
