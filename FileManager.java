import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private static final String MATCH_FILE = "matches.ser";
    private static final String TEAM_FILE = "teams.ser";

    public static void saveMatches(ArrayList<Match> matches) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(MATCH_FILE))) {
            oos.writeObject(matches);
        } catch (IOException e) {
            System.out.println("Error saving match history: " + e.getMessage());
        }
    }

    public static ArrayList<Match> loadMatches() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(MATCH_FILE))) {
            return (ArrayList<Match>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public static void saveTeams(ArrayList<Team> teams) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TEAM_FILE))) {
            oos.writeObject(teams);
        } catch (IOException e) {
            System.out.println("Error saving teams: " + e.getMessage());
        }
    }

    public static ArrayList<Team> loadTeams() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(TEAM_FILE))) {
            return (ArrayList<Team>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
