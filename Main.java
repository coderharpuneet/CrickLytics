import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    static ArrayList<Match> matchHistory = FileManager.loadMatches();
    static ArrayList<Team> teams = FileManager.loadTeams();
    static HashSet<String> teamNames = new HashSet<>();
    static {
        for (Team t : teams) {
            teamNames.add(t.getName());
        }
    }
    public static void simulateMatch(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Team 1 Name: ");
        String team1 = sc.nextLine();
        if (!teamNames.contains(team1)) {
            System.out.println("Team 1 not found! Please add the team first.");
            return;
        }
    
        System.out.println("Enter Team 2 Name: ");
        String team2 = sc.nextLine();
        if (!teamNames.contains(team2)) {
            System.out.println("Team 2 not found! Please add the team first.");
            return;
        }
    
        System.out.println("Who won the Toss? (1 for Team 1, 2 for Team 2): ");
        int toss = sc.nextInt();
        sc.nextLine();
    
        if (toss == 2) {
            String temp = team1;
            team1 = team2;
            team2 = temp;
        }
    
        System.out.println("Enter " + team1 + " Score: ");
        String team1Score = sc.nextLine();
        System.out.println("Enter " + team1 + " Wickets: ");
        int team1Wickets = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter " + team1 + " Overs: ");
        double team1Overs = sc.nextDouble();
        sc.nextLine();
    
        System.out.println("Enter " + team2 + " Score: ");
        String team2Score = sc.nextLine();
        System.out.println("Enter " + team2 + " Wickets: ");
        int team2Wickets = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter " + team2 + " Overs: ");
        double team2Overs = sc.nextDouble();
        sc.nextLine();
    
        if (team1Wickets > 10 || team2Wickets > 10) {
            System.out.println("Invalid Wickets!");
            return;
        }
        if (team1Overs > 20 || team2Overs > 20) {
            System.out.println("Invalid Overs!");
            return;
        }
        if (team1Score.equals("") || team2Score.equals("")) {
            System.out.println("Invalid Score!");
            return;
        }
        if (team1.equals(team2)) {
            System.out.println("Invalid Teams!");
            return;
        }
    
        int score1 = Integer.parseInt(team1Score);
        int score2 = Integer.parseInt(team2Score);
    
        Team t1 = null, t2 = null;
        for (Team t : teams) {
            if (t.getName().equals(team1)) t1 = t;
            if (t.getName().equals(team2)) t2 = t;
        }
    
        if (t1 == null || t2 == null) {
            System.out.println("Team(s) not found in list!");
            return;
        }
    
        // Update matches played for both teams
        t1.incrementMatchesPlayed();
        t2.incrementMatchesPlayed();
    
        if (score1 == score2) {
            System.out.println("Match Draw!");
            t1.incrementdraws();
            t2.incrementdraws();
        } else if (score1 > score2) {
            System.out.println(team1 + " Won!");
            t1.incrementWins();
            t2.incrementLosses();
        } else {
            System.out.println(team2 + " Won!");
            t2.incrementWins();
            t1.incrementLosses();
        }
    
        Match match = new Match(team1, team2, team1Score, team2Score, team1Wickets, team2Wickets, team1Overs, team2Overs);
        matchHistory.add(match);
    
        FileManager.saveMatches(matchHistory);
        FileManager.saveTeams(teams); // Important to save updated team stats
    
        System.out.println("Match Simulated Successfully!");
    }
    
    
    public static void addNewTeam() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the new Team Name: ");
        String teamName = sc.nextLine();
    
        if (teamNames.contains(teamName)) {
            System.out.println("Team already exists!");
            return;
        }
    
        Team team = new Team(teamName);
        for (int i = 0; i < 11; i++) {
            System.out.println("Enter Player " + (i + 1) + " Name: ");
            String playerName = sc.nextLine();
            team.players[i] = new Player(playerName, teamName);
        }
    
        teams.add(team);
        teamNames.add(teamName); // add here
        FileManager.saveTeams(teams);
        System.out.println("Team added successfully!");
    }
    

    public static void viewTeams() {
        System.out.println("Teams: ");
        for (Team team : teams) {
            System.out.println(team.getName());
        }
    }

    public static void viewMatchHistory() {
        System.out.println("Match History: ");
        for (Match match : matchHistory) {
            System.out.println(match.getTeam1() + " vs " + match.getTeam2() + ": " + match.getTeam1Score() + " - " + match.getTeam2Score());
        }
    }
    
    public static void viewPointsTable() {
    PriorityQueue<Team> pq = new PriorityQueue<>(new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            // Descending order of points, then wins as tiebreaker
            if (t2.getPoints() != t1.getPoints()) {
                return t2.getPoints() - t1.getPoints();
            } else {
                return t2.getWins() - t1.getWins();
            }
        }
    });

    pq.addAll(teams);

    System.out.println("------ Points Table ------");
    System.out.printf("%-15s %-5s %-5s %-5s %-5s %-6s%n", "Team", "P", "W", "L", "D", "Points");
    while (!pq.isEmpty()) {
        Team team = pq.poll();
        System.out.printf("%-15s %-5d %-5d %-5d %-5d %-6d%n",
                team.getName(),
                team.getMatchesPlayed(),
                team.getWins(),
                team.getLosses(),
                team.getdraws(),
                team.getPoints());
    }
}
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Login As:");
        System.out.println("1. Admin");
        System.out.println("2. User");
        int choice=sc.nextInt();
        sc.nextLine();
        if(choice==1){
            System.out.print("Enter Admin Password: ");
            String adminPassword=sc.nextLine();
            if(adminPassword.equals("admin123")){
                System.out.println("Welcome Admin!");
                System.out.println("1. Simulate Match");
                System.out.println("2. Add New Team");
                System.out.println("3. View Teams");
                System.out.println("4. View Points Table");
                System.out.println("5. Match History");
                int adminChoice=sc.nextInt();
                sc.nextLine();
                switch(adminChoice){
                    case 1:
                        simulateMatch();
                        break;
                    case 2:
                        addNewTeam();
                        break;
                    case 3:
                        viewTeams();
                        break;
                    case 4:
                        viewPointsTable();
                        break;
                    case 5:
                        viewMatchHistory();
                        break;
                    default:
                        System.out.println("Invalid choice! Please try again.");
                }
            } else {
                System.out.println("Incorrect Password!");
            }
        }
        else{
            System.out.println("Welcome User!");
            System.out.println("1. View Teams");
            System.out.println("2. View Points Table");
            System.out.println("4. Match History");
            int userChoice=sc.nextInt();
            sc.nextLine();
            switch(userChoice){
                case 1:
                    viewTeams();
                    break;
                case 2:
                    viewPointsTable();
                    break;
                case 3:
                    viewMatchHistory();
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        }
        
    }
}