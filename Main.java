import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
        if (toss == 1) {
            System.out.println("Toss won by " + team1);
            System.out.println("what did " + team1 + " choose? (1 for Bat, 2 for Bowl): ");
            int tossChoice = sc.nextInt();
            sc.nextLine();
            if (tossChoice == 1) {
                System.out.println(team1 + " chose to Bat first.");
            } else if (tossChoice == 2) {
                System.out.println(team1 + " chose to Bowl first.");
                String temp = team1;
                team1 = team2;
                team2 = temp;
            } else {
                System.out.println("Invalid choice!");
                return;
            }
        } else if (toss == 2) {
            System.out.println("Toss won by " + team2);
            System.out.println("what did " + team2 + " choose? (1 for Bat, 2 for Bowl): ");
            int tossChoice = sc.nextInt();
            sc.nextLine();
            if (tossChoice == 1) {
                System.out.println(team2 + " chose to Bat first.");
                String temp = team1;
                team1 = team2;
                team2 = temp;
            } else if (tossChoice == 2) {
                System.out.println(team2 + " chose to Bowl first.");
            } else {
                System.out.println("Invalid choice!");
                return;
            }
        } else {
            System.out.println("Invalid Toss!");
            return;
        }

        System.out.print("Enter " + team1 + " Score: ");
        String team1Score = sc.nextLine();
        System.out.print("Enter " + team1 + " Wickets: ");
        int team1Wickets = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter " + team1 + " Overs: ");
        double team1Overs = sc.nextDouble();
        sc.nextLine();

        Team t1 = null, t2 = null;
        for (Team t : teams) {
            if (t.getName().equals(team1))
                t1 = t;
            if (t.getName().equals(team2))
                t2 = t;
        }
        if (t1 == null || t2 == null) {
            System.out.println("Team(s) not found in list!");
            return;
        }

        HashMap<String, Integer> team1Batting = new HashMap<>();
        System.out.println("Enter names and scores for " + (team1Wickets + 2) + " batsmen of " + team1 + ":");
        for (int i = 0; i < team1Wickets + 2;) {
            System.out.print("Player " + (i + 1) + " Name: ");
            String playerName = sc.nextLine();
            boolean playerExists = Arrays.stream(t1.getPlayers())
                    .anyMatch(p -> p != null && p.getName().equalsIgnoreCase(playerName));

            if (!playerExists) {
                System.out.println("❌ Player not found in " + t1.getName() + " squad. Try again.");
                continue;
            }
            System.out.print("Score of " + playerName + ": ");
            int score = sc.nextInt();
            sc.nextLine();
            team1Batting.put(playerName, score);
            for (Player p : t1.getPlayers()) {
                if (p != null && p.getName().equalsIgnoreCase(playerName)) {
                    p.addRuns(score);
                    break;
                }
            }

            i++;
        }

        System.out.println("Enter number of bowlers from " + team2 + ": ");
        int bowlersCountTeam2 = sc.nextInt();
        sc.nextLine();
        System.out.println(
                "Enter names, wickets, and runs conceded for " + bowlersCountTeam2 + " bowlers of " + team2 + ":");
        for (int i = 0; i < bowlersCountTeam2;) {
            System.out.print("Bowler " + (i + 1) + " Name: ");
            String bowlerName = sc.nextLine();
            boolean bowlerExists = t2.getPlayers() != null && Arrays.stream(t2.getPlayers())
                    .anyMatch(p -> p != null && p.getName().equalsIgnoreCase(bowlerName));
            if (!bowlerExists) {
                System.out.println("❌ Bowler not found in " + t2.getName() + " squad. Try again.");
                continue;
            }

            System.out.print("Wickets taken by " + bowlerName + ": ");
            int wkts = sc.nextInt();
            System.out.print("Runs conceded by " + bowlerName + ": ");
            int runs = sc.nextInt();
            sc.nextLine();

            for (Player p : t2.getPlayers()) {
                if (p != null && p.getName().equalsIgnoreCase(bowlerName)) {
                    p.addWickets(wkts);
                    p.addRunsConceded(runs);
                    break;
                }
            }
            i++;
        }

        System.out.print("Enter " + team2 + " Score: ");
        String team2Score = sc.nextLine();
        System.out.print("Enter " + team2 + " Wickets: ");
        int team2Wickets = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter " + team2 + " Overs: ");
        double team2Overs = sc.nextDouble();
        sc.nextLine();

        HashMap<String, Integer> team2Batting = new HashMap<>();
        System.out.println("Enter names and scores for " + (team2Wickets + 2) + " batsmen of " + team2 + ":");
        for (int i = 0; i < team2Wickets + 2;) {
            System.out.print("Player " + (i + 1) + " Name: ");
            String playerName = sc.nextLine();
            boolean playerExists = Arrays.stream(t2.getPlayers())
                    .anyMatch(p -> p != null && p.getName().equalsIgnoreCase(playerName));
            if (!playerExists) {
                System.out.println("Player not found in " + t2.getName() + " squad. Try again.");
                continue;
            }
            System.out.print("Score of " + playerName + ": ");
            int score = sc.nextInt();
            sc.nextLine();
            team2Batting.put(playerName, score);
            for (Player p : t2.getPlayers()) {
                if (p != null && p.getName().equalsIgnoreCase(playerName)) {
                    p.addRuns(score);
                    break;
                }
            }
            i++;
        }

        System.out.println("Enter number of bowlers from " + team1 + ": ");
        int bowlersCountTeam1 = sc.nextInt();
        sc.nextLine();
        System.out.println(
                "Enter names, wickets, and runs conceded for " + bowlersCountTeam1 + " bowlers of " + team1 + ":");
        for (int i = 0; i < bowlersCountTeam1;) {
            System.out.print("Bowler " + (i + 1) + " Name: ");
            String bowlerName = sc.nextLine();
            boolean bowlerExists = t1.getPlayers() != null && Arrays.stream(t1.getPlayers())
                    .anyMatch(p -> p != null && p.getName().equalsIgnoreCase(bowlerName));
            if (!bowlerExists) {
                System.out.println("❌ Bowler not found in " + t1.getName() + " squad. Try again.");
                continue;
            }

            System.out.print("Wickets taken by " + bowlerName + ": ");
            int wkts = sc.nextInt();
            System.out.print("Runs conceded by " + bowlerName + ": ");
            int runs = sc.nextInt();
            sc.nextLine();

            for (Player p : t1.getPlayers()) {
                if (p != null && p.getName().equalsIgnoreCase(bowlerName)) {
                    p.addWickets(wkts);
                    p.addRunsConceded(runs);
                    break;
                }
            }
            i++;
        }

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

        Match match = new Match(team1, team2, team1Score, team2Score, team1Wickets, team2Wickets, team1Overs,
                team2Overs, team1Batting, team2Batting);

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
        System.out.println("Team added successfully!");
    }

    public static void viewTeams() {
        System.out.println("\n======= Available Teams =======");
        for (Team team : teams) {
            System.out.println("- " + team.getName());
        }
        System.out.println("================================");
    }

    public static void viewMatchHistory() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n=========== Match History ===========");
        System.out.println("Enter Match Number to view details: ");
        int matcNo = sc.nextInt();
        if (matcNo > matchHistory.size()) {
            System.out.println("Invalid Match Number");
            return;
        }
        Match match = matchHistory.get(matcNo - 1);
        System.out.printf("\n%s vs %s\nScore: %s/%d (%.1f overs) - %s/%d (%.1f overs)\n",
                match.getTeam1(), match.getTeam2(),
                match.getTeam1Score(), match.getTeam1Wickets(), match.getTeam1Overs(),
                match.getTeam2Score(), match.getTeam2Wickets(), match.getTeam2Overs());

        System.out.println("\nTop 2 Batsmen for " + match.getTeam1() + ":");
        match.getTeam1Batting().entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(2)
                .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue() + " runs"));

        System.out.println("\nTop 2 Batsmen for " + match.getTeam2() + ":");
        match.getTeam2Batting().entrySet().stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .limit(2)
                .forEach(e -> System.out.println(e.getKey() + " - " + e.getValue() + " runs"));

        System.out.println("--------------------------------------");

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

    public static void viewPlayerStats() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n======= Player Stats =======");
        System.out.print("Enter Player Name: ");
        String playerName = sc.nextLine();
        boolean playerFound = false;

        for (Team team : teams) {
            for (Player player : team.getPlayers()) {
                if (player != null && player.getName().equalsIgnoreCase(playerName)) {
                    System.out.println("Player: " + player.getName());
                    System.out.println("Team: " + team.getName());
                    System.out.println("Runs: " + player.getRuns());
                    System.out.println("Wickets: " + player.getWickets());
                    System.out.println("Runs Conceded: " + player.getRunsConceded());

                    playerFound = true;
                    break;
                }
            }
            if (playerFound)
                break;
        }

        if (!playerFound) {
            System.out.println("Player not found!");
        }
    }

    public static void orangeCap() {
        ArrayList<Player> allPlayers = new ArrayList<>();
        for (Team t : teams) {
            for (Player p : t.getPlayers()) {
                if (p != null)
                    allPlayers.add(p);
            }
        }

        mergeSort(allPlayers, 0, allPlayers.size() - 1);

        System.out.println("\n🏏 Orange Cap - Top 5 Run Scorers:");
        for (int i = 0; i < Math.min(5, allPlayers.size()); i++) {
            Player p = allPlayers.get(i);
            System.out.println((i + 1) + ". " + p.getName() + " (" + p.getTeamName() + ") - " + p.getRuns() + " runs");
        }
    }

    public static void mergeSort(ArrayList<Player> players, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(players, left, mid);
            mergeSort(players, mid + 1, right);
            merge(players, left, mid, right);
        }
    }

    public static void merge(ArrayList<Player> players, int left, int mid, int right) {
        ArrayList<Player> leftList = new ArrayList<>(players.subList(left, mid + 1));
        ArrayList<Player> rightList = new ArrayList<>(players.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;

        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).getRuns() >= rightList.get(j).getRuns()) {
                players.set(k++, leftList.get(i++));
            } else {
                players.set(k++, rightList.get(j++));
            }
        }

        while (i < leftList.size()) {
            players.set(k++, leftList.get(i++));
        }
        while (j < rightList.size()) {
            players.set(k++, rightList.get(j++));
        }
    }

    public static void purpleCap() {
        ArrayList<Player> allPlayers = new ArrayList<>();
        for (Team t : teams) {
            for (Player p : t.getPlayers()) {
                if (p != null)
                    allPlayers.add(p);
            }
        }

        quickSort(allPlayers, 0, allPlayers.size() - 1);

        System.out.println("\n🎯 Purple Cap - Top 5 Wicket Takers:");
        for (int i = 0; i < Math.min(5, allPlayers.size()); i++) {
            Player p = allPlayers.get(i);
            System.out.println(
                    (i + 1) + ". " + p.getName() + " (" + p.getTeamName() + ") - " + p.getWickets() + " wickets");
        }
    }

    public static void quickSort(ArrayList<Player> players, int low, int high) {
        if (low < high) {
            int pi = partition(players, low, high);
            quickSort(players, low, pi - 1);
            quickSort(players, pi + 1, high);
        }
    }

    public static int partition(ArrayList<Player> players, int low, int high) {
        int pivot = players.get(high).getWickets();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (players.get(j).getWickets() >= pivot) {
                i++;
                Collections.swap(players, i, j);
            }
        }
        Collections.swap(players, i + 1, high);
        return i + 1;
    }

    public static void highestTeamTotal() {
        System.out.println("\n======= Highest Team Total =======");
        Team highestTeam = null;
        int highestScore = 0;

        for (Match match : matchHistory) {
            int score1 = Integer.parseInt(match.getTeam1Score());
            int score2 = Integer.parseInt(match.getTeam2Score());

            if (score1 > highestScore) {
                highestScore = score1;
                highestTeam = new Team(match.getTeam1());
            }
            if (score2 > highestScore) {
                highestScore = score2;
                highestTeam = new Team(match.getTeam2());
            }
        }

        System.out.println("Highest Team Total: " + highestTeam.getName() + " - " + highestScore + " runs");
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
        } else {
            System.out.println("\n======= User Dashboard =======");
            System.out.println("1. View Teams");
            System.out.println("2. View Points Table");
            System.out.println("3. Match History");
            System.out.println("4. View Stats");
            System.out.println("================================");

            int userChoice = sc.nextInt();
            sc.nextLine();

            switch (userChoice) {
                case 1:
                    viewTeams();
                    break;
                case 2:
                    viewPointsTable();
                    break;
                case 3:
                    viewMatchHistory();
                    break;
                case 4:
                    System.out.println("1. View a player Stats");
                    System.out.println("2. Orange Cap");
                    System.out.println("3. Purple Cap");
                    System.out.println("4. Highest Team Total");
                    int statsChoice = sc.nextInt();
                    sc.nextLine();
                    switch (statsChoice) {
                        case 1:
                            viewPlayerStats();
                            break;
                        case 2:
                            orangeCap();
                            break;
                        case 3:
                            purpleCap();
                            break;
                        case 4:
                            highestTeamTotal();
                            break;
                        default:
                            System.out.println("Invalid choice! Please try again.");
                            break;

                    }
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }
}
