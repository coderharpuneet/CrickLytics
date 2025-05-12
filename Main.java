import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
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

    public static void simulateMatch() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n======= Simulate Match =======");
        System.out.print("Enter Team 1 Name: ");
        String team1 = sc.nextLine();
        if (!teamNames.contains(team1)) {
            System.out.println("Team 1 not found! Please add the team first.");
            return;
        }

        System.out.print("Enter Team 2 Name: ");
        String team2 = sc.nextLine();
        if (!teamNames.contains(team2)) {
            System.out.println("Team 2 not found! Please add the team first.");
            return;
        }

        System.out.print("Who won the Toss? (1 for Team 1, 2 for Team 2): ");
        int toss = sc.nextInt();
        sc.nextLine();

        if (toss == 2) {
            String temp = team1;
            team1 = team2;
            team2 = temp;
        }

        System.out.print("Enter " + team1 + " Score: ");
        String team1Score = sc.nextLine();
        System.out.print("Enter " + team1 + " Wickets: ");
        int team1Wickets = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter " + team1 + " Overs: ");
        double team1Overs = sc.nextDouble();
        sc.nextLine();

        System.out.print("Enter " + team2 + " Score: ");
        String team2Score = sc.nextLine();
        System.out.print("Enter " + team2 + " Wickets: ");
        int team2Wickets = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter " + team2 + " Overs: ");
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

        t1.incrementMatchesPlayed();
        t2.incrementMatchesPlayed();

        if (score1 == score2) {
            System.out.println("\n🏏 Match Result: It's a Draw!");
            t1.incrementdraws();
            t2.incrementdraws();
        } else if (score1 > score2) {
            System.out.println("\n🏆 " + team1 + " won the match!");
            t1.incrementWins();
            t2.incrementLosses();
        } else {
            System.out.println("\n🏆 " + team2 + " won the match!");
            t2.incrementWins();
            t1.incrementLosses();
        }

        t1.updateStats(score1, team1Overs, score2, team2Overs);
        t2.updateStats(score2, team2Overs, score1, team1Overs);

        Match match = new Match(team1, team2, team1Score, team2Score, team1Wickets, team2Wickets, team1Overs, team2Overs);
        matchHistory.add(match);

        FileManager.saveMatches(matchHistory);
        FileManager.saveTeams(teams);

        System.out.println("\n✅ Match Simulated and Saved Successfully!");
    }

    public static void addNewTeam() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n====== Add New Team ======");
        System.out.print("Enter the new Team Name: ");
        String teamName = sc.nextLine();

        if (teamNames.contains(teamName)) {
            System.out.println("Team already exists!");
            return;
        }

        Team team = new Team(teamName);
        for (int i = 0; i < 11; i++) {
            System.out.print("Enter Player " + (i + 1) + " Name: ");
            String playerName = sc.nextLine();
            team.players[i] = new Player(playerName, teamName);
        }

        teams.add(team);
        teamNames.add(teamName);
        FileManager.saveTeams(teams);
        System.out.println("✅ Team added successfully!");
    }

    public static void viewTeams() {
        System.out.println("\n======= Available Teams =======");
        for (Team team : teams) {
            System.out.println("- " + team.getName());
        }
        System.out.println("================================");
    }

    public static void viewMatchHistory() {
        System.out.println("\n=========== Match History ===========");
        for (Match match : matchHistory) {
            System.out.printf("%s vs %s\nScore: %s/%d (%.1f overs) - %s/%d (%.1f overs)\n",
                match.getTeam1(), match.getTeam2(),
                match.getTeam1Score(), match.getTeam1Wickets(), match.getTeam1Overs(),
                match.getTeam2Score(), match.getTeam2Wickets(), match.getTeam2Overs());
            System.out.println("--------------------------------------");
        }
    }

    public static void viewPointsTable() {
        PriorityQueue<Team> pq = new PriorityQueue<>(new Comparator<Team>() {
            public int compare(Team t1, Team t2) {
                if (t2.getPoints() != t1.getPoints())
                    return t2.getPoints() - t1.getPoints();
                else if (t2.getWins() != t1.getWins())
                    return t2.getWins() - t1.getWins();
                else
                    return Double.compare(t2.getNRR(), t1.getNRR());
            }
        });

        pq.addAll(teams);

        System.out.println("\n=========== Points Table ===========");
        System.out.printf("%-15s %-5s %-5s %-5s %-5s %-7s %-6s\n", "Team", "P", "W", "L", "D", "Points", "NRR");
        System.out.println("---------------------------------------------");

        while (!pq.isEmpty()) {
            Team team = pq.poll();
            System.out.printf("%-15s %-5d %-5d %-5d %-5d %-7d %-6.2f\n",
                team.getName(),
                team.getMatchesPlayed(),
                team.getWins(),
                team.getLosses(),
                team.getdraws(),
                team.getPoints(),
                team.getNRR());
        }
        System.out.println("==============================================");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("==================================");
        System.out.println("      Welcome to IPL Console      ");
        System.out.println("==================================");

        System.out.println("\nChoose Login Type:");
        System.out.println("------------------");
        System.out.println("1. Admin");
        System.out.println("2. User");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            System.out.print("Enter Admin Password: ");
            String adminPassword = sc.nextLine();

            if (adminPassword.equals("admin123")) {
                System.out.println("\n====== Admin Dashboard ======");
                System.out.println("1. Simulate Match");
                System.out.println("2. Add New Team");
                System.out.println("3. View Teams");
                System.out.println("4. View Points Table");
                System.out.println("5. Match History");
                System.out.println("==============================");

                int adminChoice = sc.nextInt();
                sc.nextLine();

                switch (adminChoice) {
                    case 1: simulateMatch(); break;
                    case 2: addNewTeam(); break;
                    case 3: viewTeams(); break;
                    case 4: viewPointsTable(); break;
                    case 5: viewMatchHistory(); break;
                    default: System.out.println("Invalid choice! Please try again.");
                }
            } else {
                System.out.println("Incorrect Password!");
            }
        } else {
            System.out.println("\n======= User Dashboard =======");
            System.out.println("1. View Teams");
            System.out.println("2. View Points Table");
            System.out.println("3. Match History");
            System.out.println("================================");

            int userChoice = sc.nextInt();
            sc.nextLine();

            switch (userChoice) {
                case 1: viewTeams(); break;
                case 2: viewPointsTable(); break;
                case 3: viewMatchHistory(); break;
                default: System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
