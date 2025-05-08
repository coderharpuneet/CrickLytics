import java.io.Serializable;
public class Team implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int matchesPlayed;
    private int wins;
    private int losses;
    private int draws;
    private int points;
    public Player[] players=new Player[11];

    public Team(String name) {
        this.name = name;
        this.matchesPlayed = 0;
        this.wins = 0;
        this.losses = 0;
        this.draws = 0;
        this.points = 0;
        
    }

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

    public void incrementMatchesPlayed() {
        matchesPlayed++;
    }

    public void incrementWins() {
        wins++;
        points += 2; 
    }

    public void incrementLosses() {
        losses++;
        points += 0; 
    }

    public void incrementdraws() {
        draws++;
        points += 1; 
    }
}