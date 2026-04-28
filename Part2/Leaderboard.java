
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Leaderboard {

    private HashMap<String, Integer> points;
    private HashMap<String, Integer> wins;
    private HashMap<String, Integer> cleanRaces;

    public Leaderboard() {
        points = new HashMap<>();
        wins = new HashMap<>();
        cleanRaces = new HashMap<>();
    }

    public void addRacePoints(String name, int position, double wpm, int burnoutCount) {
        int earned = 0;

        if (position == 1) {
            earned = 5;
            wins.put(name, wins.getOrDefault(name, 0) + 1);
        } else if (position == 2) {
            earned = 3;
        } else if (position == 3) {
            earned = 1;
        }

        if (wpm >= 40) {
            earned += 2;
        }

        if (burnoutCount > 0) {
            earned -= 1;
        } else {
            cleanRaces.put(name, cleanRaces.getOrDefault(name, 0) + 1);
        }

        if (earned < 0) {
            earned = 0;
        }

        points.put(name, points.getOrDefault(name, 0) + earned);
    }

    public String getLeaderboardText() {
        ArrayList<String> names = new ArrayList<>(points.keySet());

        Collections.sort(names, (a, b) -> points.get(b) - points.get(a));

        String text = "Leaderboard and Badges\n\n";

        for (String name : names) {
            text += name + ": " + points.get(name) + " pts";

            String badge = getBadge(name);
            if (!badge.equals("")) {
                text += " | " + badge;
            }

            text += "\n";
        }

        return text;
    }

    private String getBadge(String name) {
        if (wins.getOrDefault(name, 0) >= 3) {
            return "Speed Demon";
        }

        if (cleanRaces.getOrDefault(name, 0) >= 5) {
            return "Iron Fingers";
        }

        return "";
    }
}
