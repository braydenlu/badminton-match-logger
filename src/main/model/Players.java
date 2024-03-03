package model;

// Represents a player or pair of players with names and a nationality
public class Players {

    private String player1;
    private String player2;
    private String nationality;
    private final boolean pair;

    // EFFECTS: Creates a player with a name
    public Players(String name) {
        constructorHelper(name);
        pair = false;
    }

    // EFFECTS: Creates a pair with given names
    public Players(String name1, String name2) {
        constructorHelper(name1);
        this.player2 = name2;
        pair = true;
    }

    // EFFECTS: Sets player1 name and players nationality. Prevents code duplication in constructors
    private void constructorHelper(String name1) {
        this.player1 = name1;
        nationality = null;
    }

    // MODIFIES: This
    // EFFECTS: Sets player one's name
    public void setPlayer1(String name) {
        this.player1 = name;
    }

    // MODIFIES: This
    // EFFECTS: Sets player two's name if this is a pair, nothing otherwise
    public void setPlayer2(String name) {
        if (pair) {
            this.player2 = name;
        }
    }

    // EFFECTS: Gets the player/pair's name
    public String getName() {
        if (pair) {
            return player1 + " and " + player2;
        }
        return player1;
    }

    // MODIFIES: This
    // EFFECTS: Sets the players' nationality
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    // REQUIRES: Nationality is not null
    // EFFECTS: Gets the players' nationality
    public String getNationality() {
        return nationality;
    }

    // EFFECTS: Returns if players are a pair
    public boolean isPair() {
        return pair;
    }
}
