import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String team;
    private int runs;
    private int sixes;
    private int fours;
    private int wickets;
    private int runsConceded;

    public Player(String name, String team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public String getTeamName(){
        return team;
    }

    public int getRuns() {
        return runs;
    }
    public int getSixes(){
        return sixes;
    }
    public int getFours(){
        return fours;
    }

    public void addRuns(int runs) {
        this.runs += runs;
    }

    public int getWickets() {   
        return wickets;
    }

    public void addWickets(int wkts) {
        this.wickets += wkts;
    }

    public int getRunsConceded() {
        return runsConceded;
    }

    public void addRunsConceded(int runs) {
        this.runsConceded += runs;
    }
}
