package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of badminton matches
public class MatchLog implements Writable {

    String name;
    ArrayList<Match> matchLog;

    // EFFECTS: Creates a new empty match log
    public MatchLog(String name) {
        matchLog = new ArrayList<>();
        this.name = name;
    }

    // EFFECTS: Gets name of the match log
    public String getName() {
        return name;
    }

    // MODIFIES: This
    // EFFECTS: Adds a match to the match log
    public void addMatch(Match match) {
        matchLog.add(match);
    }

    // REQUIRES: Match is in match log
    // MODIFIES: This
    // EFFECTS: Removes a match from the match log
    public void removeMatch(Match match) {
        matchLog.remove(match);
    }

    // REQUIRES: -1 < position < matchLog.size() - 1
    // EFFECTS: Returns match of given position starting from beginning of list
    public Match getMatch(int position) {
        return matchLog.get(position);
    }

    // EFFECTS: Returns the match log
    public ArrayList<Match> getMatchLog() {
        return matchLog;
    }

    // EFFECTS: Returns first match with given name or null if not found
    public Match findMatch(String name) {
        for (Match m: matchLog) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    // EFFECTS: Turns a match log into a JSONObject
    public JSONObject toJson() {
        JSONObject jsonMatchLog = new JSONObject();
        JSONArray matchLogContents = new JSONArray();

        for (Match m : matchLog) {
            matchLogContents.put(m.toJson());
        }

        jsonMatchLog.put("name", name);
        jsonMatchLog.put("matches", matchLogContents);

        return jsonMatchLog;
    }
}
