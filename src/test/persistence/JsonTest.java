package persistence;

import model.Game;
import model.Match;
import model.MatchLog;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkMatchLog(String name, ArrayList<Match> matchLogArraylist, MatchLog matchLog) {
        assertEquals(name, matchLog.getName());

        int i = 0;
        for (Match m: matchLogArraylist) {
            checkMatch(m.getEvent(), m.getScore1(), m.getScore2(), m.getGames(), m.isDoubles(),
                    m.getPlayersOne().getName(), m.getPlayersTwo().getName(), m.getPlayersOne().getNationality(),
                    m.getPlayersTwo().getNationality(), matchLog.getMatch(i));
            i++;
        }
    }

    protected void checkMatch(String event, int score1, int score2, ArrayList<Game> games,boolean doubles,
                              String playersOne, String playersTwo, String nationality1, String nationality2,
                              Match match) {
        assertEquals(event, match.getEvent());
        assertEquals(score1, match.getScore1());
        assertEquals(score2, match.getScore2());
        assertEquals(doubles, match.isDoubles());
        assertEquals(playersOne, match.getPlayersOne().getName());
        assertEquals(playersTwo, match.getPlayersTwo().getName());
        assertEquals(nationality1, match.getPlayersOne().getNationality());
        assertEquals(nationality2, match.getPlayersTwo().getNationality());

        int i = 0;
        for (Game g: games) {
            checkGame(g.getScore1(), g.getScore2(), g.getNotes(), match.getGames().get(i));
            i++;
        }
    }

    protected void checkGame(int score1, int score2, String notes, Game game) {
        assertEquals(score1, game.getScore1());
        assertEquals(score2, game.getScore2());
        assertEquals(notes, game.getNotes());
    }
}

