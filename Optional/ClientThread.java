package ServerApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * An instance of the ServerApplication.ClientThread class will be responsible with the communication of a client.
 */

public class ClientThread extends Thread {
    private Socket socket = null;
    private String playerName;
    private int gameNumber;
    int symbol = 0;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String request = in.readLine();
            playerName = request;
            out.println("S-a conectat playerul " + playerName);
            boolean stopped = false; // it's value represents the fact that the socket is closed or not
            String response = "";
            while (!stopped) {
                // Get the request from the input stream: client → server
                request = in.readLine();
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
                    if (request.equals("new game")) {
                        gameNumber = GameServer.createGame(playerName, symbol++, 15);
                        response = "Game number " + gameNumber + " started. Waiting for opponent.";
                        out.println(response);
                        out.flush();
                    }
                    if (request.equals("join game")) {
                        response = "Insert game number: ";
                        out.println(response);
                        out.flush();
                        request = in.readLine();
                        gameNumber = Integer.parseInt(request);
                        if (GameServer.joinGame(playerName, symbol++, gameNumber)) {
                            response = "Connected to the game successfully";
                        } else {
                            response = "The game number " + gameNumber + "doesn't exist.";
                        }
                    }
                    if (request.equals("submit move")) {
                        response = "Insert the row: ";
                        out.println(response);
                        out.flush();
                        int coorX = Integer.parseInt(in.readLine());
                        response = "Insert the column: ";
                        out.println(response);
                        out.flush();
                        int coorY = Integer.parseInt(in.readLine());
                        if (GameServer.submitMove(gameNumber, playerName, coorX, coorY)) {
                            response = "The move has been made successfully.";
                            out.println(response);
                            out.flush();
                            int[][] board = GameServer.getCurrentBoard(gameNumber).getBoard();
                            StringBuilder raspunsBuilder = new StringBuilder(response);
                            for (int i = 0; i < board.length; i++) {
                                for (int j = 0; j < board[i].length; j++) {
                                    raspunsBuilder.append(board[i][j]).append(' ');
                                }
                                raspunsBuilder.append('\n');
                            }
                            response = raspunsBuilder.toString();
                            out.println(response);
                            out.flush();
                        }
                        else
                        {
                            response = "The game is over ";
                            out.println(response);
                            out.flush();
                        }


                    }
                }
            }
            } catch(IOException e){
                System.err.println("Communication error... " + e);
            } finally{
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }
    }



