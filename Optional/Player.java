package ServerApplication;

/**
 * An instance of the ServerApplication.Player class will represent a player.
 */
public class Player {
    private static String name;
    private int symbol;

    public Player(String name, int symbol) {
        this.name = name;
        this.symbol= symbol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getName() {
        return name;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }
}
