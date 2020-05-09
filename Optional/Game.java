package ServerApplication;

/**
 * The game class contains the rules of the game.
 */
public class Game {

    private Board board;
    private Player player1;
    private Player player2;

    public Game(int boardsize) {
        board = new Board(boardsize);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }

    public boolean addPlayer(String name, int symbol) {
        if (player1 == null) {
            player1 = new Player(name, symbol);
            return true;
        } else
            if (player2 == null) {
                if(player1.getSymbol()!=symbol)
                    player2 = new Player(name,symbol);
                    return true;
        }
        return false;
    }

    public boolean play(int coorX, int coorY, Player player) {
        if (!board.isPositionOccupied(coorX, coorY)) {
            return false;
        } else {
            board.playAtPosition(coorX, coorY, player);
            return true;
        }
    }

}
