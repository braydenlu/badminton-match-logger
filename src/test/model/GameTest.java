package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    Game ethanVsBrayden;
    Game graceVsEmma;
    Game pairVsPair;
    Game changingGame;

    Players ethan;
    Players brayden;
    Players grace;
    Players emma;

    Players ethanAndBrayden;
    Players graceAndEmma;

    Game ethanVsBraydenCopy;
    Players ethanCopy;
    Players braydenCopy;

    @BeforeEach
    public void setup() {
        ethan = new Players("Ethan");
        brayden = new Players("Brayden");
        grace = new Players("Grace");
        emma = new Players("Emma");

        ethanVsBrayden = new Game(ethan, brayden, 0, 21, "Ethan was getting bubble tea");
        graceVsEmma = new Game(grace, emma, 19, 21, "");
        pairVsPair = new Game(ethanAndBrayden, graceAndEmma, 12, 21, "Ethan not back yet");
        changingGame = new Game(ethan, brayden, 21, 0, "");

        ethanCopy = new Players("Ethan");
        braydenCopy = new Players("Brayden");
        ethanVsBraydenCopy = new Game(ethanCopy, braydenCopy, 0, 21, "Ethan was getting bubble tea");
    }

    @Test
    public void setAndGetScoreAndWinnerTest() {
        assertEquals("0 - 21", ethanVsBrayden.getScore());
        assertEquals(0, ethanVsBrayden.getScore1());
        assertEquals(21, ethanVsBrayden.getScore2());
        assertEquals(brayden, ethanVsBrayden.getWinner());

        ethanVsBrayden.setScore(1, 21);

        assertEquals("1 - 21", ethanVsBrayden.getScore());
        assertEquals(1, ethanVsBrayden.getScore1());
        assertEquals(21, ethanVsBrayden.getScore2());
        assertEquals(brayden, ethanVsBrayden.getWinner());

        assertEquals("19 - 21", graceVsEmma.getScore());
        assertEquals(19, graceVsEmma.getScore1());
        assertEquals(21, graceVsEmma.getScore2());
        assertEquals(emma, graceVsEmma.getWinner());

        graceVsEmma.setScore(21, 19);

        assertEquals("21 - 19", graceVsEmma.getScore());
        assertEquals(grace, graceVsEmma.getWinner());
    }

    @Test
    public void setAndGetNotesTest() {
        assertEquals("", graceVsEmma.getNotes());

        graceVsEmma.setNotes("Hello");

        assertEquals("Hello", graceVsEmma.getNotes());
        assertEquals("Ethan was getting bubble tea", ethanVsBrayden.getNotes());
        assertEquals("Ethan not back yet", pairVsPair.getNotes());
    }

    @Test
    public void setAndGetSidesTest() {
        assertEquals(ethan, changingGame.getSideOne());
        assertEquals(brayden, changingGame.getSideTwo());

        changingGame.setSideOne(grace);

        assertEquals(grace, changingGame.getSideOne());
        assertEquals(brayden, changingGame.getSideTwo());

        changingGame.setSideTwo(emma);

        assertEquals(grace, changingGame.getSideOne());
        assertEquals(emma, changingGame.getSideTwo());
    }

    @Test
    public void gameEqualsTest() {
        Players player1 = new Players("player1");
        Players player2 = new Players("player2");
        Players player3 = new Players("player3");
        Players player4 = new Players("player4");

        Game game1 = new Game(player1, player2, 1, 2, "");
        Game game2 = new Game(player1, player3, 1, 2, "");
        Game game3 = new Game(player1, player2, 3, 2, "");
        Game game4 = new Game(player1, player2, 1, 2, "Hello");
        Game game5 = new Game(player1, player2, 200, 100, "");
        Game game6 = new Game(player1, player2, 1, 3, "");
        Game game7 = new Game(player4, player2, 1, 2, "");
        Game game8 = new Game(player2, player1, 1, 2, "");

        Game game1copy = new Game(player1, player2, 1, 2, "");

        assertTrue(game1.gameEquals(game1));
        assertTrue(game1.gameEquals(game1copy));

        assertFalse(game1.gameEquals(game2));
        assertFalse(game1.gameEquals(game3));
        assertFalse(game1.gameEquals(game4));
        assertFalse(game1.gameEquals(game5));
        assertFalse(game1.gameEquals(game6));
        assertFalse(game1.gameEquals(game7));
        assertFalse(game1.gameEquals(game8));
    }

    @Test
    public void toJsonTest() {
        assertEquals(0, ethanVsBrayden.toJson().get("score1"));
        assertEquals(21, ethanVsBrayden.toJson().get("score2"));
        assertEquals("Ethan was getting bubble tea", ethanVsBrayden.toJson().get("notes"));

        assertEquals(19, graceVsEmma.toJson().get("score1"));
        assertEquals(21, graceVsEmma.toJson().get("score2"));
        assertEquals("", graceVsEmma.toJson().get("notes"));
    }
}
