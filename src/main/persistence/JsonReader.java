package persistence;

import model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
import org.json.*;

// Represents a reader that reads a badminton match log from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file (copied from JsonSerializationDemo)
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: Reads a match log from a file and returns it;
    // throws IOException if an error occurs reading data from file (copied mostly from JsonSerializationDemo)
    public ArrayList<MatchLog> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        ArrayList<MatchLog> matchLogArrayList = new ArrayList<>();

        for (Object json : jsonObject.getJSONArray("match logs")) {
            JSONObject jsonMatchLog = (JSONObject) json;
            MatchLog matchLog = parseMatchLog(jsonMatchLog);
            matchLogArrayList.add(matchLog);
        }

        return matchLogArrayList;
    }

    // EFFECTS: Parses workroom from JSON object and returns it (copied mostly from JsonSerializationDemo)
    private MatchLog parseMatchLog(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        MatchLog matchLog = new MatchLog(name);
        addMatches(matchLog, jsonObject);
        return matchLog;
    }

    // MODIFIES: Match log
    // EFFECTS: Parses matches from JSON object and adds them to the match log (mostly from JsonSerializationDemo)
    private void addMatches(MatchLog matchLog, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("matches");
        for (Object jsonMatch : jsonArray) {
            JSONObject nextMatch = (JSONObject) jsonMatch;
            addMatch(matchLog, nextMatch);
        }
    }

    // MODIFIES: Match Log
    // EFFECTS: Parses match from JSON object and adds it to the match log
    private void addMatch(MatchLog ml, JSONObject jsonObject) {
        Match match;

        JSONArray playerArray = jsonObject.getJSONArray("players");
        String playersOneString = playerArray.getJSONObject(0).getString("name");
        String playersTwoString = playerArray.getJSONObject(1).getString("name");

        if (jsonObject.getBoolean("doubles")) {
            ArrayList<String> playersOneList = new ArrayList<>(Arrays.asList(playersOneString.split(" and ")));
            ArrayList<String> playersTwoList = new ArrayList<>(Arrays.asList(playersTwoString.split(" and ")));

            String playerOne = playersOneList.get(0);
            String playerTwo = playersOneList.get(1);
            String playerThree = playersTwoList.get(0);
            String playerFour = playersTwoList.get(1);

            match = new Match(playerOne, playerTwo, playerThree, playerFour);

        } else {
            match = new Match(playersOneString, playersTwoString);
        }

        match.setEvent(jsonObject.getString("event"));

        JSONArray gameArray = jsonObject.getJSONArray("games");

        for (Object jsonGame : gameArray) {
            JSONObject nextGame = (JSONObject) jsonGame;
            addGame(match, nextGame);
        }

        ml.addMatch(match);
    }

    // MODIFIES: Match in match log
    // EFFECTS: Parses game from JSON object and adds it to match
    private void addGame(Match match, JSONObject jsonObject) {
        int score1 = jsonObject.getInt("score1");
        int score2 = jsonObject.getInt("score2");
        String notes = jsonObject.getString("notes");
        match.addGame(score1, score2, notes);
    }


    // EFFECTS: reads source file as string and returns it (copied mostly from JsonSerializationDemo)
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

}
