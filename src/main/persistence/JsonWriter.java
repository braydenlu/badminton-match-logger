package persistence;

import model.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

// Represents a writer that writes JSON representation of a match log to file (almost all code directly copied from
// JsonSerializationDemo)
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private final String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of match log to file
    public void write(ArrayList<MatchLog> matchLogArrayList) {
        JSONObject jsonOuterContainerObject = new JSONObject();

        JSONArray jsonMatchLogList = new JSONArray();

        for (MatchLog ml : matchLogArrayList) {
            JSONObject json = ml.toJson();
            jsonMatchLogList.put(json);
        }
        jsonOuterContainerObject.put("match logs", jsonMatchLogList);

        saveToFile(jsonOuterContainerObject.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
