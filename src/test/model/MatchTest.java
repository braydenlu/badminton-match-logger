package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchTest {

    Players ethan;
    Players brayden;
    Players grace;
    Players emma;

    Players ethanAndBrayden;
    Players graceAndEmma;

    Match ethanVsBrayden;
    Game ethanVsBrayden1;
    Game ethanVsBrayden2;
    Game ethanVsBrayden3;

    Match graceVsEmma;
    Game graceVsEmma1;
    Game graceVsEmma2;

    Match pairVsPair;
    Game pairVsPair1;
    Game pairVsPair2;

    @BeforeEach
    public void setup() {
        ethan = new Players("Ethan");
        brayden = new Players("Brayden");
        grace = new Players("Grace");
        emma = new Players("Emma");

        ethanAndBrayden = new Players("Ethan", "Brayden");
        graceAndEmma = new Players("Grace", "Emma");

        ethanVsBrayden = new Match("Ethan", "Brayden");
        ethanVsBrayden1 = new Game(ethan, brayden, 0, 21, "Ethan was getting bubble tea");
        ethanVsBrayden2 = new Game(ethan, brayden, 21, 0, "Brayden was getting bubble tea");
        ethanVsBrayden3 = new Game(ethan, brayden, 19, 21, "Fair and square");

        graceVsEmma = new Match("Grace", "Emma");
        graceVsEmma1 = new Game(grace, emma, 21, 17, "");
        graceVsEmma2 = new Game(grace, emma, 21, 15, "");

        pairVsPair = new Match("Ethan", "Brayden", "Grace", "Emma");
        pairVsPair1 = new Game(ethanAndBrayden, graceAndEmma, 21, 12, "");
        pairVsPair2 = new Game(ethanAndBrayden, graceAndEmma, 21, 16, "");
    }

    @Test
    public void matchTest() {
        assertEquals("Ethan", ethanVsBrayden.getPlayersOne().getName());
        assertEquals("Brayden", ethanVsBrayden.getPlayersTwo().getName());
        assertFalse(ethanVsBrayden.isDoubles());
        assertTrue(ethanVsBrayden.getGames().isEmpty());

        assertEquals("Ethan and Brayden", pairVsPair.getPlayersOne().getName());
        assertTrue(pairVsPair.isDoubles());
        assertEquals("Grace and Emma", pairVsPair.getPlayersTwo().getName());
        assertTrue(pairVsPair.getGames().isEmpty());
    }

    @Test
    public void addGameTest() {
        ethanVsBrayden.addGame(0, 21, "Ethan was getting bubble tea");
        assertTrue(ethanVsBrayden1.gameEquals(ethanVsBrayden.getGame(1)));
        assertEquals("Brayden", ethanVsBrayden.getWinners().getName());
        assertEquals(1, ethanVsBrayden.getGames().size());
        assertEquals("0 - 1", ethanVsBrayden.getScore());

        ethanVsBrayden.addGame(21, 0, "Brayden was getting bubble tea");
        assertTrue(ethanVsBrayden2.gameEquals(ethanVsBrayden.getGame(2)));
        assertNull(ethanVsBrayden.getWinners());
        assertEquals(2, ethanVsBrayden.getGames().size());
        assertEquals("1 - 1", ethanVsBrayden.getScore());

        ethanVsBrayden.addGame(19, 21, "Fair and square");
        assertTrue(ethanVsBrayden3.gameEquals(ethanVsBrayden.getGame(3)));
        assertEquals("Brayden", ethanVsBrayden.getWinners().getName());
        assertEquals(3, ethanVsBrayden.getGames().size());
        assertEquals("1 - 2", ethanVsBrayden.getScore());

        pairVsPair.addGame(21, 12, "");
        assertTrue(pairVsPair1.gameEquals(pairVsPair.getGame(1)));
        assertEquals("Ethan and Brayden", pairVsPair.getWinners().getName());
        assertEquals(1, pairVsPair.getGames().size());

        pairVsPair.addGame(21, 16, "");
        assertTrue(pairVsPair2.gameEquals(pairVsPair.getGame(2)));
        assertEquals("Ethan and Brayden", pairVsPair.getWinners().getName());
        assertEquals(2, pairVsPair.getGames().size());
    }

    @Test
    public void removeGameTest() {
        ethanVsBrayden.addGame(0, 21, "Ethan was getting bubble tea");
        assertEquals(0, ethanVsBrayden.getScore1());
        assertEquals(1, ethanVsBrayden.getScore2());
        ethanVsBrayden.removeGame();
        assertNull(ethanVsBrayden.getWinners());
        assertEquals(0, ethanVsBrayden.getScore1());
        assertEquals(0, ethanVsBrayden.getScore2());
        assertTrue(ethanVsBrayden.getGames().isEmpty());

        graceVsEmma.addGame(1, 0, "");
        graceVsEmma.addGame(0, 1,"");
        graceVsEmma.addGame(1, 0, "");

        graceVsEmma.removeGame();
        assertEquals(2, graceVsEmma.getGames().size());
        assertNull(graceVsEmma.getWinners());
        assertEquals(1, graceVsEmma.getScore1());
        assertEquals(1, graceVsEmma.getScore2());

        graceVsEmma.removeGame();
        assertEquals(1, graceVsEmma.getGames().size());
        assertEquals("Grace", graceVsEmma.getWinners().getName());
        assertEquals(1, graceVsEmma.getScore1());
        assertEquals(0, graceVsEmma.getScore2());

        graceVsEmma.removeGame();
        assertTrue(graceVsEmma.getGames().isEmpty());
    }

    @Test
    public void eventAndNameTest() {
        assertEquals("None", ethanVsBrayden.getEvent());
        assertEquals("Ethan vs Brayden", ethanVsBrayden.getName());
        assertEquals("Grace vs Emma", graceVsEmma.getName());
        assertEquals("Ethan and Brayden vs Grace and Emma", pairVsPair.getName());

        ethanVsBrayden.setEvent("Men's Singles");
        assertEquals("Men's Singles", ethanVsBrayden.getEvent());

        pairVsPair.setEvent("Doubles");
        assertEquals("Doubles", pairVsPair.getEvent());
    }

    @Test
    public void getGameTest() {
        assertNull(ethanVsBrayden.getGame(1));

        ethanVsBrayden.addGame(0, 21, "Ethan was getting bubble tea");
        assertNull(ethanVsBrayden.getGame(2));
        assertTrue(ethanVsBrayden1.gameEquals(ethanVsBrayden.getGame(1)));

        ethanVsBrayden.addGame(21, 0, "Brayden was getting bubble tea");
        assertTrue(ethanVsBrayden2.gameEquals(ethanVsBrayden.getGame(2)));

        assertNull(ethanVsBrayden.getGame(3));
        assertNull(ethanVsBrayden.getGame(0));
    }

    @Test
    public void toJsonTest() {
        ethanVsBrayden.addGame(0, 21, "Ethan was getting bubble tea");
        ethanVsBrayden.addGame(21, 0, "Brayden was getting bubble tea");
        ethanVsBrayden.addGame(19, 21, "Fair and square");

        JSONObject ethanVsBraydenJson = ethanVsBrayden.toJson();

        assertFalse(ethanVsBraydenJson.getBoolean("doubles"));
        assertEquals(2, ethanVsBraydenJson.getJSONArray("players").length());
        assertEquals("Ethan",
                ethanVsBraydenJson.getJSONArray("players").getJSONObject(0).get("name"));
        assertEquals("Brayden",
                ethanVsBraydenJson.getJSONArray("players").getJSONObject(1).get("name"));
        assertEquals(3, ethanVsBraydenJson.getJSONArray("games").length());
        assertEquals("None", ethanVsBraydenJson.get("event"));
    }
}
