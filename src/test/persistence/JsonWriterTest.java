package persistence;

import model.Match;
import model.MatchLog;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // All good
        }
    }

    @Test
    void testWriterNoMatchLogs() {
        ArrayList<MatchLog> matchLogArrayList = new ArrayList<>();
        JsonWriter writer = new JsonWriter("./data/testWriterNoMatchLogs");

        try {
            writer.open();
            writer.write(matchLogArrayList);
            writer.close();
        } catch (FileNotFoundException e){
            fail("Unable to write to file");
        }

        JsonReader reader = new JsonReader("./data/testWriterNoMatchLogs");
        try {
            ArrayList<MatchLog> readerMatchLogs = reader.read();
            assertTrue(readerMatchLogs.isEmpty());
        } catch (IOException e) {
            fail("Unable to read file");
        }
    }

    @Test
    void testWriterEmptyMatchLogs() {
        ArrayList<MatchLog> matchLogArrayList = new ArrayList<>();
        JsonWriter writer = new JsonWriter("./data/testWriterEmptyMatchLogs");

        MatchLog myMatches = new MatchLog("My Matches");
        MatchLog otherMatches = new MatchLog("Other Matches");

        matchLogArrayList.add(myMatches);
        matchLogArrayList.add(otherMatches);

        try {
            writer.open();
            writer.write(matchLogArrayList);
            writer.close();
        } catch (FileNotFoundException e){
            fail("Unable to write to file");
        }

        JsonReader reader = new JsonReader("./data/testWriterEmptyMatchLogs");

        ArrayList<Match> emptyMatches = new ArrayList<>();

        try {
            ArrayList<MatchLog> readerMatchLogs = reader.read();
            assertEquals(2, readerMatchLogs.size());
            checkMatchLog("My Matches", emptyMatches, readerMatchLogs.get(0));
            checkMatchLog("Other Matches", emptyMatches, readerMatchLogs.get(1));
        } catch (IOException e) {
            fail("Unable to read file");
        }
    }

    @Test
    void testWriterGeneralMatchLogs() {
        MatchLog myMatches = new MatchLog("My Matches");

        Match ethanVsBrayden = new Match("Ethan", "Brayden");
        ethanVsBrayden.setEvent("Singles");
        ethanVsBrayden.addGame(0, 21, "Game 1");
        ethanVsBrayden.addGame(0, 21, "Game 2");

        Match ethanAndBraydenVsGraceAndEmma = new Match("Ethan", "Brayden","Grace", "Emma");
        ethanAndBraydenVsGraceAndEmma.setEvent("Doubles");
        ethanAndBraydenVsGraceAndEmma.addGame(21, 0, "");
        ethanAndBraydenVsGraceAndEmma.addGame(21, 0, "");

        myMatches.addMatch(ethanVsBrayden);
        myMatches.addMatch(ethanAndBraydenVsGraceAndEmma);

        MatchLog otherMatches = new MatchLog("Other Matches");

        Match kentoMomotaVsLohKeanYew = new Match("Kento Momota", "Loh Kean Yew");
        kentoMomotaVsLohKeanYew.setEvent("Men's Singles");
        kentoMomotaVsLohKeanYew.addGame(18, 21, "");
        kentoMomotaVsLohKeanYew.addGame(26, 24, "");
        kentoMomotaVsLohKeanYew.addGame(21, 19, "");

        otherMatches.addMatch(kentoMomotaVsLohKeanYew);

        ArrayList<MatchLog> matchLogArrayList = new ArrayList<>();
        matchLogArrayList.add(myMatches);
        matchLogArrayList.add(otherMatches);

        try {
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralMatchLogs.json");
            writer.open();
            writer.write(matchLogArrayList);
            writer.close();
        } catch (FileNotFoundException e) {
            fail("Unable to write to file");
        }

        JsonReader reader = new JsonReader("./data/testWriterGeneralMatchLogs.json");
        try {
            ArrayList<MatchLog> readerMatchLogs = reader.read();
            MatchLog readerMyMatches = readerMatchLogs.get(0);
            MatchLog readerOtherMatches = readerMatchLogs.get(1);

            checkMatchLog("My Matches", myMatches.getMatchLog(), readerMyMatches);
            checkMatchLog("Other Matches", otherMatches.getMatchLog(), readerOtherMatches);

        } catch (IOException e) {
            fail("Failed to read from file");
        }
    }
}
