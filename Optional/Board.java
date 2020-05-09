package ServerApplication;

/**
 * An instance of the ServerApplication.Board will represent the board of a game.
 */
public class Board {
    private int[][] board;
    private int boardsize;

    public Board(int boardsize) {
        this.boardsize = boardsize;
        board = new int[boardsize][boardsize];
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public boolean playAtPosition(int coorX, int coorY, Player player) {
        if(!isPositionOccupied(coorX,coorY)) {
            board[coorX][coorY] = player.getSymbol();
            return true;
        }
        return false;

    }

    public boolean isPositionOccupied(int coorX, int coorY) {
        if (board[coorX][coorY] != 0)
            return true;
        else
            return false;
    }

    /**
     * Calculates how many consecutive symbols are in all directions N NE E S SV V NV
     */
    public boolean isVictorie(int i, int j) {
        boolean victory = false;
        int nrconsecS = 0;
        int nrconsecE = 0;
        int nrconsecSE = 0;
        int nrconsecN = 0;
        int nrconsecV = 0;
        int nrconsecNV = 0;
        int nrconsecSV = 0;
        int nrconsecNE = 0;

        for (int k = i; k < i + 4; k++) {
            for (int l = j; l < j + 4; l++) {
                if (board[k][j] == board[k + 1][j])
                    nrconsecS++;
                if (board[i][l] == board[i][l + 1])
                    nrconsecE++;
                if (board[k][l] == board[k + 1][l + 1])
                    nrconsecSE++;
            }
        }
        for (int k = i; k > i - 4; k--) {
            for (int l = j; l > j - 4; l--) {
                if (board[k][j] == board[k - 1][j])
                    nrconsecN++;
                if (board[i][l] == board[i][l - 1])
                    nrconsecV++;
                if (board[k][l] == board[k - 1][l - 1])
                    nrconsecNV++;
            }
        }

        for (int k = i; k < i + 4; k++)
            for (int l = j; l > j - 4; l--) {
                if (board[k][l] != board[k + 1][j - 1])
                    nrconsecSV++;
            }

        for (int k = i; k > i - 4; k--)
            for (int l = j; l < j + 4; l++) {
                if (board[k][l] != board[k - 1][j + 1])
                    nrconsecNE++;
            }
        if (nrconsecS == 5 || nrconsecE == 5 || nrconsecSE == 5 || nrconsecN == 5 || nrconsecV == 5 || nrconsecNV == 5 || nrconsecSV == 5 || nrconsecNE == 5)
            victory = true;
        return victory;
    }
    /**
     * Checks if the game is over
     */

    public boolean isOver() {
        boolean isOver = true;
        for (int i = 0; i < boardsize; i++)
            for (int j = 0; j < boardsize; j++)
                if (board[i][j] == 0 || !isVictorie(i, j)) {
                    isOver = false;
                } else {
                    if (isVictorie(i, j))
                        return true;
                }
        if (isOver == false)
            return false;
        else
            return true;
    }

}


