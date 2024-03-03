package ui;

import model.Game;
import model.Match;
import model.MatchLog;
import model.Players;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Represents a badminton match logging app
public class MatchLogApp {
    private static final String JSON_FILE_LOCATION = "./data/matchLog.json";
    private MatchLog myMatches;
    private MatchLog otherMatches;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Runs match log app
    public MatchLogApp() {
        jsonWriter = new JsonWriter(JSON_FILE_LOCATION);
        jsonReader = new JsonReader(JSON_FILE_LOCATION);
        runMatchLog();
    }

    // MODIFIES: This
    // EFFECTS: Handles user input
    private void runMatchLog() {
        boolean runningApp = true;
        String command;

        initializeApp();

        while (runningApp) {
            printSelectOptions("A: Add match", "V: View match log", "S: Save match logs",
                    "L: Load match logs", "Q: Quit");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                runningApp = false;
            } else {
                handleInputs(command);
            }
        }
    }

    // MODIFIES: This
    // EFFECTS: Initializes the match log app
    private void initializeApp() {
        myMatches = new MatchLog("My Matches");
        otherMatches = new MatchLog("Other Matches");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void initializeMatches(MatchLog matchLog) {
        Players ethan = new Players("Ethan");
        Players brayden = new Players("Brayden");
        Players grace = new Players("Grace");
        Players emma = new Players("Emma");

        Players ethanAndBrayden = new Players("Ethan", "Brayden");
        Players graceAndEmma = new Players("Grace", "Emma");

        Match ethanVsBrayden = new Match("Ethan", "Brayden");
        ethanVsBrayden.addGame(0, 21, "Ethan was getting bubble tea");
        ethanVsBrayden.addGame(21, 0, "Brayden was getting bubble tea");
        ethanVsBrayden.addGame(19, 21, "Fair and square");

        Match graceVsEmma = new Match("Grace", "Emma");
        graceVsEmma.addGame(21, 17, "");
        graceVsEmma.addGame(21, 15, "");

        Match pairVsPair = new Match("Ethan", "Brayden", "Grace", "Emma");
        pairVsPair.addGame(21, 12, "");
        pairVsPair.addGame(21, 16, "");

        matchLog.addMatch(ethanVsBrayden);
        matchLog.addMatch(graceVsEmma);
        matchLog.addMatch(pairVsPair);
    }

    // MODIFIES: This
    // EFFECTS: Handles main menu options for the app
    private void handleInputs(String command) {
        if (command.equals("a")) {
            doAddMatch();
        } else if (command.equals("v")) {
            doViewMatchLog();
        } else if (command.equals("s")) {
            doSaveMatchLogs();
        } else if (command.equals("l")) {
            doLoadMatchLogs();
        } else {
            printInvalidOption();
        }
    }

    // MODIFIES: This
    // EFFECTS: Handles adding a match
    private void doAddMatch() {
        MatchLog selected = selectMatchLog();
        System.out.println("How many players in the match?");
        String numPlayers = input.next();

        if (numPlayers.equals("2")) {
            doCreateMatch(selected, false);
        } else if (numPlayers.equals("4")) {
            doCreateMatch(selected, true);
        } else {
            System.out.println("Invalid number of players entered");
        }

        printMatchLog(selected);
    }

    // MODIFIES: This
    // EFFECTS: Handles viewing the user's match log
    private void doViewMatchLog() {
        boolean runningMenu = true;
        MatchLog selected = selectMatchLog();
        printMatchLog(selected);

        if (!selected.getMatchLog().isEmpty()) {
            while (runningMenu) {
                printSelectOptions("R: Remove a match", "V: View a match", "B: Go back");
                String selection = input.next();
                selection = selection.toLowerCase();

                if (selection.equals("b")) {
                    runningMenu = false;
                } else if (selection.equals("r")) {
                    doRemoveMatch(selected);
                } else if (selection.equals("v")) {
                    doViewMatch(selected);
                } else {
                    printInvalidOption();
                }
            }
        }
    }

    // EFFECTS: saves the match logs to file
    private void doSaveMatchLogs() {

        ArrayList<MatchLog> matchLogs = new ArrayList<>();
        matchLogs.add(myMatches);
        matchLogs.add(otherMatches);

        try {
            jsonWriter.open();
            jsonWriter.write(matchLogs);
            jsonWriter.close();
            System.out.println("Saved match logs to " + JSON_FILE_LOCATION);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file path: " + JSON_FILE_LOCATION);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void doLoadMatchLogs() {
        try {
            ArrayList<MatchLog> matchLogs = jsonReader.read();

            // Must be changed when match log list functionality is added
            myMatches = matchLogs.get(0);
            otherMatches = matchLogs.get(1);

            System.out.println("Loaded match logs from " + JSON_FILE_LOCATION);
        } catch (IOException e) {
            System.out.println("Unable to read from file path: " + JSON_FILE_LOCATION);
        }
    }

    // MODIFIES: This
    // EFFECTS: Handles removing a match
    private void doRemoveMatch(MatchLog matchLog) {
        String selection = "";

        while (matchLog.findMatch(selection) == null) {
            System.out.println("Please enter the name of the match you would like to remove");
            selection = input.next();
        }

        matchLog.removeMatch(matchLog.findMatch(selection));
        System.out.println("Match has been removed!");
    }

    // EFFECTS: Handles viewing a match from the match log
    private void doViewMatch(MatchLog matchLog) {
        boolean runningMenu = true;

        while (runningMenu) {
            System.out.println("Please enter the name of the match you would like to view or B to go back");
            String matchName = input.next();

            if (matchName.equals("B") || matchName.equals("b")) {
                runningMenu = false;

            } else {
                Match match = matchLog.findMatch(matchName);

                if (match != null) {
                    printMatchInfo(match);
                    System.out.println("Type E to edit the match details or B to go back");

                    String selection = input.next();
                    selection = selection.toLowerCase();

                    if (selection.equals("e")) {
                        doEditMatch(match);
                    } else if (selection.equals("b")) {
                        runningMenu = false;
                    }
                } else {
                    System.out.println("Match does not exist");
                }
            }
        }
    }

    // EFFECTS: Prompts user to select either their match log or the log of others
    private MatchLog selectMatchLog() {
        String selection = "";

        while (!(selection.equals("m") || selection.equals("o"))) {
            System.out.println("M for my match log");
            System.out.println("O for other match log");
            selection = input.next();
            selection = selection.toLowerCase();
        }

        if (selection.equals("m")) {
            return myMatches;
        } else {
            return otherMatches;
        }
    }

    // MODIFIES: This
    // EFFECTS: Adds a match to the match log
    private void doCreateMatch(MatchLog matchLog, boolean pair) {
        Match match;

        System.out.println("Please type the name of the first player");
        String playerOneName = input.next();

        System.out.println("Please type the name of the second player");
        String playerTwoName = input.next();

        if (pair) {
            System.out.println("Please type the name of the third player");
            String playerThreeName = input.next();

            System.out.println("Please type the name of the fourth player");
            String playerFourName = input.next();

            matchLog.addMatch(match = new Match(playerOneName, playerTwoName, playerThreeName, playerFourName));
        } else {
            matchLog.addMatch(match = new Match(playerOneName, playerTwoName));
        }

        System.out.println("Match successfully added!");

        doEditMatch(match);
    }

    // EFFECTS: Handles editing a match
    private void doEditMatch(Match match) {
        boolean runningMenu = true;

        while (runningMenu) {
            printSelectOptions("A: Add a game", "R: Remove the last game", "G: Edit a game", "P: Edit players",
                    "E: Edit event", "B: Go back");

            String selection = input.next();
            selection = selection.toLowerCase();

            if (selection.equals("a")) {
                doAddGame(match);
            } else if (selection.equals("r")) {
                doRemoveGame(match);
            } else if (selection.equals("p")) {
                doEditPlayers(match);
            } else if (selection.equals("e")) {
                doEditEvent(match);
            } else if (selection.equals("b")) {
                runningMenu = false;
            } else if (selection.equals("g")) {
                doEditGame(match);
            } else {
                printInvalidOption();
            }
            printMatchInfo(match);
        }
    }

    // MODIFIES: This
    // EFFECTS: Handles adding a game to a match
    private void doAddGame(Match match) {
        System.out.println("Please enter the first side's score");
        int score1 = input.nextInt();

        System.out.println("Please enter the second side's score");
        int score2 = input.nextInt();

        System.out.println("Please enter any notes about the game");
        String notes = input.next();

        match.addGame(score1, score2, notes);
        System.out.println("Game successfully added!");
    }

    // MODIFIES: This
    // EFFECTS: Removes the last game
    private void doRemoveGame(Match match) {
        match.removeGame();
        System.out.println("Game successfully removed!");
    }

    // MODIFIES: This
    // EFFECTS: Handles editing a match's player info
    private void doEditPlayers(Match match) {
        printEditPlayerMenu(match);
        String selection = input.next();

        if (match.getPlayersOne().isPair()) {
            if (selection.equals("1")) {
                doEditPlayer(match.getPlayersOne(), 1);
            } else if (selection.equals("2")) {
                doEditPlayer(match.getPlayersOne(), 2);
            } else if (selection.equals("3")) {
                doEditPlayer(match.getPlayersTwo(), 1);
            } else if (selection.equals("4")) {
                doEditPlayer(match.getPlayersTwo(), 2);
            } else {
                printInvalidOption();
            }
        } else {
            if (selection.equals("1")) {
                doEditPlayer(match.getPlayersOne(), 1);
            } else if (selection.equals("2")) {
                doEditPlayer(match.getPlayersTwo(), 1);
            } else {
                printInvalidOption();
            }
        }
    }

    // MODIFIES: This
    // EFFECTS: Handles editing player info
    private void doEditPlayer(Players players, int playerNumber) {
        printSelectOptions("1: Edit player name", "2: Edit player nationality");
        String selection = input.next();

        if (selection.equals("1")) {
            System.out.println("Please enter the player's new name");

            String name = input.next();

            if (playerNumber == 1) {
                players.setPlayer1(name);
            } else {
                players.setPlayer2(name);
            }

            System.out.println("Name successfully changed!");
        } else if (selection.equals("2")) {

            if (players.isPair()) {
                System.out.println("Please enter the pair's new nationality");
            } else {
                System.out.println("Please enter the player's new nationality");
            }

            String nationality = input.next();
            players.setNationality(nationality);

            System.out.println("Nationality successfully changed!");

        } else {
            printInvalidOption();
        }
    }

    // MODIFIES: This
    // EFFECTS: Changes match event
    private void doEditEvent(Match match) {
        System.out.println("Please enter the new event name");
        String selection = input.next();
        match.setEvent(selection);
        System.out.println("Event successfully changed!");
    }

    // MODIFIES: This
    // EFFECTS: Handles editing game details
    private void doEditGame(Match match) {
        System.out.println("Enter the number of the game you would like to edit");
        int gameNumber = input.nextInt();

        if (match.getGame(gameNumber) != null) {
            printSelectOptions("S: Edit score", "N: Edit notes");
            String selection = input.next();
            selection = selection.toLowerCase();

            if (selection.equals("s")) {
                System.out.println("Please enter the first side's new score");
                int score1 = input.nextInt();
                System.out.println("Please enter the second side's new score");
                int score2 = input.nextInt();
                match.getGame(gameNumber).setScore(score1, score2);
                System.out.println("Score successfully changed!");
            } else if (selection.equals("n")) {
                System.out.println("Please enter the game's new notes");
                String notes = input.next();
                match.getGame(gameNumber).setNotes(notes);
            } else {
                printInvalidOption();
            }

        } else {
            System.out.println("Game does not exist");
        }
    }

    // EFFECTS: Prints out all matches in the given match log
    private void printMatchLog(MatchLog matchLog) {
        System.out.println("\n" + matchLog.getName() + ":");
        if (matchLog.getMatchLog().isEmpty()) {
            System.out.println("Empty");
        }
        for (Match m: matchLog.getMatchLog()) {
            System.out.println(m.getName());
        }
    }

    private void printEditPlayerMenu(Match match) {
        printSelectOptions("1: Edit player 1's info", "2: Edit player 2's info");
        if (match.getPlayersOne().isPair()) {
            System.out.println("\t3: Edit player 3's info");
            System.out.println("\t4: Edit player 4's info");
        }
    }

    // EFFECTS: Prints all the info about a match
    private void printMatchInfo(Match match) {
        System.out.println("\n" + match.getName());
        System.out.println("Event: " + match.getEvent());
        System.out.println(match.getScore());
        if (match.getWinners() != null) {
            System.out.println("Winner: " + match.getWinners().getName());
        } else {
            System.out.println("Winner: None");
        }
        printGameInfo(match);
    }

    // EFFECTS: Prints all the info about the games in a match
    private void printGameInfo(Match match) {
        int counter = 1;
        for (Game g: match.getGames()) {
            System.out.println("\nGame " + counter);
            System.out.println("Score: " + g.getScore());
            System.out.println("Winner: " + g.getWinner().getName());
            System.out.println("Notes: " + g.getNotes());
            counter++;
        }
    }

    // EFFECTS: Prints out invalid option message
    private void printInvalidOption() {
        System.out.println("Invalid option entered");
    }

    // EFFECTS: Prints out select option message
    private void printSelectOptions(String... options) {
        System.out.println("\nPlease select an option:");
        for (String o: options) {
            System.out.println("\t" + o);
        }
    }
}
