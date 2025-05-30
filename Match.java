import java.io.Serializable;
import java.util.HashMap;

public class Match implements Serializable {
    private static final long serialVersionUID = 1L;

    private String team1;
    private String team2;
    private String team1Score;
    private String team2Score;
    private int team1Wickets;
    private int team2Wickets;
    private double team1Overs;
    private double team2Overs;

    private HashMap<String, Integer> team1Batting;
    private HashMap<String, Integer> team2Batting;

    public Match(String team1, String team2, String team1Score, String team2Score,
        int team1Wickets, int team2Wickets, double team1Overs, double team2Overs,
        HashMap<String, Integer> team1Batting, HashMap<String, Integer> team2Batting) {
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
        this.team1Wickets = team1Wickets;
        this.team2Wickets = team2Wickets;
        this.team1Overs = team1Overs;
        this.team2Overs = team2Overs;
        this.team1Batting = team1Batting;
        this.team2Batting = team2Batting;
    }

    public String getTeam1() { return team1; }
    public String getTeam2() { return team2; }
    public String getTeam1Score() { return team1Score; }
    public String getTeam2Score() { return team2Score; }
    public int getTeam1Wickets() { return team1Wickets; }
    public int getTeam2Wickets() { return team2Wickets; }
    public double getTeam1Overs() { return team1Overs; }
    public double getTeam2Overs() { return team2Overs; }
    public HashMap<String, Integer> getTeam1Batting() { return team1Batting; }
    public HashMap<String, Integer> getTeam2Batting() { return team2Batting; }
}
