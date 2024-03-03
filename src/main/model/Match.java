package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

// Represents a full badminton match with a duration, event type, date, players, and one to three games
public class Match {

    private String event;
    private int score1;
    private int score2;
    private Players winners;

    private ArrayList<Game> games;
    private final boolean doubles;

    private Players playersOne;
    private Players playersTwo;

    // EFFECTS: Creates a doubles match/match with four players
    public Match(String playerOne, String playerTwo, String playerThree, String playerFour) {
        setUpMatch(playerOne, playerTwo, playerThree, playerFour);
        doubles = true;
    }

    // EFFECTS: Creates a singles match/match with two players
    public Match(String playerOne, String playerTwo) {
        setUpMatch(playerOne, playerTwo, null, null);
        doubles = false;
    }

    // EFFECTS: Sets up match. Here to avoid code duplication
    private void setUpMatch(String playerOne, String playerTwo, String playerThree, String playerFour) {
        if (playerThree == null) {
            playersOne = new Players(playerOne);
            playersTwo = new Players(playerTwo);
        } else {
            playersOne = new Players(playerOne, playerTwo);
            playersTwo = new Players(playerThree, playerFour);
        }
        games = new ArrayList<>();
        event = "None";
    }

    // MODIFIES: This
    // EFFECTS: Adds a game to the match and sets the match winner; null if tie
    public void addGame(int score1, int score2, String notes) {
        Game game = new Game(playersOne, playersTwo, score1, score2, notes);
        games.add(game);

        if (score1 > score2) {
            this.score1++;
        } else {
            this.score2++;
        }

        setWinners();
    }

    // EFFECTS: Returns match score
    public String getScore() {
        return score1 + " - " + score2;
    }

    // EFFECTS: Returns game at position or null if it does not exist
    public Game getGame(int position) {
        if (games.size() == 0) {
            return null;
        } else if (position <= games.size() && position >= 1) {
            return games.get(position - 1);
        } else {
            return null;
        }
    }

    // REQUIRES: Games is not empty
    // MODIFIES: This
    // EFFECTS: Removes last game in games
    public void removeGame() {
        if (games.get(games.size() - 1).getWinner() == playersOne) {
            score1--;
        } else {
            score2--;
        }

        setWinners();

        games.remove(games.size() - 1);
    }

    // EFFECTS: Returns the games in the match
    public ArrayList<Game> getGames() {
        return games;
    }

    // EFFECTS: Returns players one
    public Players getPlayersOne() {
        return playersOne;
    }

    // EFFECTS: Returns players two
    public Players getPlayersTwo() {
        return playersTwo;
    }

    // EFFECTS: Returns winning players, null if tie
    public Players getWinners() {
        return winners;
    }


    // EFFECTS: Gets the match name
    public String getName() {
        return playersOne.getName() + " vs " + playersTwo.getName();
    }

    // MODIFIES: This
    // EFFECTS: Sets the event string
    public void setEvent(String event) {
        this.event = event;
    }

    // EFFECTS: Returns the event
    public String getEvent() {
        return event;
    }

    // MODIFIES: This
    // EFFECTS: Sets winner of game
    public void setWinners() {
        if (this.score1 > this.score2) {
            this.winners = playersOne;
        } else if (this.score2 > this.score1) {
            this.winners = playersTwo;
        } else {
            this.winners = null;
        }
    }

    // EFFECTS: Returns score1
    public int getScore1() {
        return score1;
    }

    // EFFECTS: Returns score2
    public int getScore2() {
        return score2;
    }

    // EFFECTS: Returns doubles
    public boolean isDoubles() {
        return doubles;
    }

    // EFFECTS: Turns a match into a JSONObject
    public JSONObject toJson() {
        JSONObject jsonMatch = new JSONObject();

        JSONArray jsonPlayers = new JSONArray();
        JSONObject jsonPlayer1 = new JSONObject();
        JSONObject jsonPlayer2 = new JSONObject();

        jsonMatch.put("event", event);
        jsonMatch.put("doubles", doubles);

        // Creating players and adding to player JSONArray
        jsonPlayer1.put("name", playersOne.getName());
        jsonPlayer1.put("nationality", playersOne.getNationality());
        jsonPlayer2.put("name", playersTwo.getName());
        jsonPlayer2.put("nationality", playersTwo.getNationality());

        jsonPlayers.put(jsonPlayer1);
        jsonPlayers.put(jsonPlayer2);

        // Creating games and adding to games JSONArray
        JSONArray jsonGames = new JSONArray();

        for (Game g : games) {
            jsonGames.put(g.toJson());
        }

        jsonMatch.put("players", jsonPlayers);
        jsonMatch.put("games", jsonGames);

        return jsonMatch;
    }

    /* TODO LATER FUNCTIONALITY

    private int duration;
    private int date;
    private int sideOneRank;
    private int sideTwoRank;

    // REQUIRES: Ranks > 0 and not equal
    // MODIFIES: This
    // EFFECTS: Sets player ranks
    public void setRanks(int sideOneRank, int sideTwoRank) {
        this.sideOneRank = sideOneRank;
        this.sideTwoRank = sideTwoRank;
    }

    // MODIFIES: This
    // EFFECTS: TODO
    public void editDuration() {

    }

    // MODIFIES: This
    // EFFECTS: TODO
    public void editDate() {

    }

    // EFFECTS: Gets side one's rank
    public int getSideOneRank() {
        return sideOneRank;
    }

    // EFFECTS: Gets side two's rank
    public int getSideTwoRank() {
        return sideTwoRank;
    }

    */
}
