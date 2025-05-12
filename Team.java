import java.io.Serializable;

public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int matchesPlayed;
    private int wins;
    private int losses;
    private int draws;
    private int points;
    public Player[] players = new Player[11];

    // NEW FIELDS for NRR
    private double totalRunsScored = 0;
    private double totalOversFaced = 0;
    private double totalRunsConceded = 0;
    private double totalOversBowled = 0;

    public Team(String name) {
        this.name = name;
        this.matchesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.points = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getdraws() {
        return draws;
    }

    public int getPoints() {
        return points;
    }

    // Match Increment Methods
    public void incrementMatchesPlayed() {
        matchesPlayed++;
    }

    public void incrementWins() {
        wins++;
        points += 2;
    }

    public void incrementLosses() {
        losses++;
        // 0 points for loss
    }

    public void incrementdraws() {
        draws++;
        points += 1;
    }

    public void updateStats(int runsScored, double oversFaced, int runsConceded, double oversBowled) {
        this.totalRunsScored += runsScored;
        this.totalOversFaced += oversFaced;
        this.totalRunsConceded += runsConceded;
        this.totalOversBowled += oversBowled;
    }

    public double getNRR() {
        double runRateFor = totalOversFaced > 0 ? totalRunsScored / totalOversFaced : 0;
        double runRateAgainst = totalOversBowled > 0 ? totalRunsConceded / totalOversBowled : 0;
        return runRateFor - runRateAgainst;
    }


    public Player[] getPlayers() {
    return players;
}

}
