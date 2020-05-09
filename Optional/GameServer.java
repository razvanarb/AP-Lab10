package ServerApplication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * ServerApplication.GameServer class with it's constructor will create a ServerSocket at a specified port and after a client is connected, executes the commands given by them.
 */
public class GameServer {

    int PORT = 8100; // The server's port
    ServerSocket serverSocket = null;
    private static ArrayList<Game> allGames = new ArrayList<>();

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

    public static int createGame(String playerName, int symbol, int boardSize) {
        Game game = new Game(boardSize);
        game.addPlayer(playerName, symbol);
        allGames.add(game);
        return allGames.indexOf(game);
    }

    public static boolean joinGame(String playerName, int symbol, int gameNumber) {
        allGames.get(gameNumber).addPlayer(playerName, symbol);
        return true;
    }

    public static boolean submitMove(int gameNumber, String playerName, int coorX, int coorY) {

        if(allGames.get(gameNumber).getPlayer1().getName()==playerName) {
            if(!allGames.get(gameNumber).getBoard().isOver()) {
                allGames.get(gameNumber).play(coorX, coorY, allGames.get(gameNumber).getPlayer1());
                return true;
            }
            else
                return false;
        }
        else {
            if(!allGames.get(gameNumber).getBoard().isOver()) {
                allGames.get(gameNumber).play(coorX, coorY, allGames.get(gameNumber).getPlayer2());
                return true;
            }
            else
                return false;
        }
    }

    public static Board getCurrentBoard(int gameNumber) {
        return allGames.get(gameNumber).getBoard();
    }

    public void main(String[] args) throws IOException {
        GameServer server = new GameServer();
    }
}



