import java.io.Serializable;
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String team;
    private int runs;
    private int fours;
    private int sixes;

    public Player(String name,String team) {
        this.name = name;
        this.team=team;
    }

    public String getName() {
        return name;
    }

    public int getRuns() {
        return runs;
    }
    public int getFours() {
        return fours;
    }

    public int getSixes() {
        return sixes;
    }
}
