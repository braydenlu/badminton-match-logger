package model;

import org.json.JSONObject;

// Represents a single badminton game with a score, sides, winner, and notes
public class Game {

    private int score1;
    private int score2;
    private String notes;

    private Players sideOne;
    private Players sideTwo;
    private Players winner;

    // EFFECTS: Creates a new game with players, score, and notes
    public Game(Players sideOne, Players sideTwo, int score1, int score2, String notes) {
        this.sideOne = sideOne;
        this.sideTwo = sideTwo;
        setScore(score1, score2);
        this.notes = notes;
    }

    // REQUIRES: Score order is the same as player/pair order
    // MODIFIES: This
    // EFFECTS: Sets scores for each side of badminton game
    public void setScore(int score1, int score2) {
        this.score1 = score1;
        this.score2 = score2;

        if (score1 > score2) {
            this.winner = sideOne;
        } else {
            this.winner = sideTwo;
        }
    }

    // EFFECTS: Returns game score as a string in the format "score1 - score2"
    public String getScore() {
        return score1 + " - " + score2;
    }

    // EFFECTS: Returns game score1
    public int getScore1() {
        return score1;
    }

    // EFFECTS: Returns game score2
    public int getScore2() {
        return score2;
    }

    // EFFECTS: Returns winner of game
    public Players getWinner() {
        return winner;
    }

    // EFFECTS: Returns game notes
    public String getNotes() {
        return notes;
    }

    // MODIFIES: This
    // EFFECTS: Changes the game notes to the inputted text
    public void setNotes(String notes) {
        this.notes = notes;
    }

    // MODIFIES: This
    // EFFECTS: Sets side one to given players
    public void setSideOne(Players sideOne) {
        this.sideOne = sideOne;
    }

    // EFFECTS: Returns side one
    public Players getSideOne() {
        return sideOne;
    }

    // MODIFIES: This
    // EFFECTS: Sets side two to given players
    public void setSideTwo(Players sideTwo) {
        this.sideTwo = sideTwo;
    }

    // EFFECTS: Returns side two
    public Players getSideTwo() {
        return sideTwo;
    }


    // EFFECTS: Returns true if all scores, notes, and player names of given game are identical to this
    public Boolean gameEquals(Game game) {
        return score1 == game.score1 && score2 == game.score2
                && notes.equals(game.notes)
                && sideOne.getName().equals(game.sideOne.getName())
                && sideTwo.getName().equals(game.sideTwo.getName());
    }

    // EFFECTS: Turns a game into a JSON Object
    public JSONObject toJson() {
        JSONObject jsonGame = new JSONObject();

        jsonGame.put("score1", score1);
        jsonGame.put("score2", score2);
        jsonGame.put("notes", notes);

        return jsonGame;
    }
}