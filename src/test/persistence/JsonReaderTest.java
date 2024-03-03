package persistence;

import model.Match;
import model.MatchLog;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNoFile() {
        JsonReader reader = new JsonReader("./data/fakeFile.json");
        try {
            ArrayList<MatchLog> matchLogs = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // All good
        }
    }

    @Test
    public void testReaderNoMatchLogs() {
        JsonReader reader = new JsonReader("./data/testReaderNoMatchLogs.json");
        try {
            ArrayList<MatchLog> matchLogs = reader.read();
            assertTrue(matchLogs.isEmpty());
        } catch (IOException e) {
            fail("Failed to read from file");
        }
    }

    @Test
    public void testReaderEmptyMatchLogs() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyMatchLogs.json");
        try {
            ArrayList<MatchLog> matchLogs = reader.read();

            ArrayList<Match> emptyMatches = new ArrayList<>();

            checkMatchLog("My Matches", emptyMatches, matchLogs.get(0));
            checkMatchLog("Other Matches", emptyMatches, matchLogs.get(1));
        } catch (IOException e) {
            fail("Failed to read from file");
        }
    }

    @Test
    public void testReaderGeneralMatchLogs() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralMatchLogs.json");
        try {
            ArrayList<MatchLog> matchLogs = reader.read();

            MatchLog myMatches = matchLogs.get(0);
            ArrayList<Match> myMatchesArrayList = new ArrayList<>();

            Match ethanVsBrayden = new Match("Ethan", "Brayden");
            ethanVsBrayden.setEvent("Singles");
            ethanVsBrayden.addGame(0, 21, "Game 1");
            ethanVsBrayden.addGame(0, 21, "Game 2");

            Match ethanAndBraydenVsGraceAndEmma = new Match("Ethan", "Brayden","Grace", "Emma");
            ethanAndBraydenVsGraceAndEmma.setEvent("Doubles");
            ethanAndBraydenVsGraceAndEmma.addGame(21, 0, "");
            ethanAndBraydenVsGraceAndEmma.addGame(21, 0, "");

            myMatchesArrayList.add(ethanVsBrayden);
            myMatchesArrayList.add(ethanAndBraydenVsGraceAndEmma);

            MatchLog otherMatches = matchLogs.get(1);
            ArrayList<Match> otherMatchesArrayList = new ArrayList<>();

            Match kentoMomotaVsLohKeanYew = new Match("Kento Momota", "Loh Kean Yew");
            kentoMomotaVsLohKeanYew.setEvent("Men's Singles");
            kentoMomotaVsLohKeanYew.addGame(18, 21, "");
            kentoMomotaVsLohKeanYew.addGame(26, 24, "");
            kentoMomotaVsLohKeanYew.addGame(21, 19, "");

            otherMatchesArrayList.add(kentoMomotaVsLohKeanYew);

            checkMatchLog("My Matches", myMatchesArrayList, myMatches);
            checkMatchLog("Other Matches", otherMatchesArrayList, otherMatches);

        } catch (IOException e) {
            fail("Failed to read from file");
        }
    }
}
