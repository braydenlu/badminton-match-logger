package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class MatchLogTest {

    MatchLog matchLog;
    Match match1;
    Match linDanVsLeeChongWei;
    Match kentoMomotaVsLohKeanYew;
    Match gideonAndSukamuljoVsEndoAndWatanabe;

    @BeforeEach
    public void setup() {
        matchLog = new MatchLog("name");

        match1 = new Match("player1", "player2");

        linDanVsLeeChongWei = new Match("Lin Dan", "Lee Chong Wei");

        kentoMomotaVsLohKeanYew = new Match("Kento Momota", "Loh Kean Yew");

        gideonAndSukamuljoVsEndoAndWatanabe = new Match("Marcus Fernaldi Gideon",
                "Kevin Sanjaya Sukamuljo", "Hiroyuki Endo", "Yuta Watanabe");
    }

    @Test
    public void addAndRemoveMatchTest() {
        assertEquals("name", matchLog.getName());
        matchLog.addMatch(match1);
        assertEquals(match1, matchLog.getMatch(0));
        assertEquals(1, matchLog.getMatchLog().size());

        matchLog.addMatch(linDanVsLeeChongWei);
        assertEquals(linDanVsLeeChongWei, matchLog.getMatch(1));
        assertEquals(2, matchLog.getMatchLog().size());

        matchLog.addMatch(kentoMomotaVsLohKeanYew);
        assertEquals(kentoMomotaVsLohKeanYew, matchLog.getMatch(2));
        assertEquals(3, matchLog.getMatchLog().size());

        matchLog.removeMatch(linDanVsLeeChongWei);
        assertEquals(kentoMomotaVsLohKeanYew, matchLog.getMatch(1));
        assertEquals(2, matchLog.getMatchLog().size());

        matchLog.removeMatch(match1);
        assertEquals(kentoMomotaVsLohKeanYew, matchLog.getMatch(0));
        assertEquals(1, matchLog.getMatchLog().size());

        matchLog.addMatch(gideonAndSukamuljoVsEndoAndWatanabe);
        assertEquals(gideonAndSukamuljoVsEndoAndWatanabe, matchLog.getMatch(1));
        assertEquals(2, matchLog.getMatchLog().size());
    }

    @Test
    public void findMatchTest() {
        matchLog.addMatch(match1);
        matchLog.addMatch(linDanVsLeeChongWei);
        matchLog.addMatch(kentoMomotaVsLohKeanYew);
        matchLog.addMatch(gideonAndSukamuljoVsEndoAndWatanabe);

        assertNull(matchLog.findMatch(""));
        assertNull(matchLog.findMatch("Kento Momota vs Loh Kean"));
        assertNull(matchLog.findMatch("Gideon and Sukamuljo vs Endo And Watanabe"));
        assertEquals(linDanVsLeeChongWei, matchLog.findMatch("Lin Dan vs Lee Chong Wei"));
        assertEquals(match1, matchLog.findMatch("player1 vs player2"));
        assertEquals(gideonAndSukamuljoVsEndoAndWatanabe,
                matchLog.findMatch("Marcus Fernaldi Gideon and Kevin Sanjaya Sukamuljo vs " +
                        "Hiroyuki Endo and Yuta Watanabe"));
    }

    @Test
    public void toJsonTest() {
        JSONObject jsonMatchLog = matchLog.toJson();
        assertEquals(0, jsonMatchLog.getJSONArray("matches").length());
        assertEquals("name", jsonMatchLog.get("name"));

        matchLog.addMatch(match1);
        matchLog.addMatch(linDanVsLeeChongWei);
        jsonMatchLog = matchLog.toJson();
        assertEquals(2, jsonMatchLog.getJSONArray("matches").length());
    }
}