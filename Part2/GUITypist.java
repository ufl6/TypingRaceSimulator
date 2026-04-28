
import java.awt.Color;
import java.util.ArrayList;

public class GUITypist {

    private String name;
    private String symbol;
    private Color color;

    private double startingAccuracy;
    private double currentAccuracy;

    private int progress;
    private int correctKeystrokes;
    private int mistypes;
    private int burnoutCount;
    private boolean burntOut;
    private int burnoutTurnsRemaining;

    private String style;
    private String keyboard;
    private String accessory;

    private double bestWPM;
    private ArrayList<RaceResult> history;

    public GUITypist(String name, String symbol, Color color, double accuracy,
            String style, String keyboard, String accessory) {
        this.name = name;
        this.symbol = symbol;
        this.color = color;
        this.startingAccuracy = accuracy;
        this.currentAccuracy = accuracy;
        this.style = style;
        this.keyboard = keyboard;
        this.accessory = accessory;
        this.history = new ArrayList<>();
        resetForRace();
    }

    public void resetForRace() {
        progress = 0;
        correctKeystrokes = 0;
        mistypes = 0;
        burnoutCount = 0;
        burntOut = false;
        burnoutTurnsRemaining = 0;
    }

    public void typeCharacter() {
        progress++;
        correctKeystrokes++;
    }

    public void slideBack(int amount) {
        progress -= amount;
        mistypes++;

        if (progress < 0) {
            progress = 0;
        }
    }

    public void burnOut(int turns) {
        burntOut = true;
        burnoutTurnsRemaining = turns;
        burnoutCount++;
    }

    public void recoverFromBurnout() {
        if (burntOut) {
            burnoutTurnsRemaining--;

            if (burnoutTurnsRemaining <= 0) {
                burnoutTurnsRemaining = 0;
                burntOut = false;
            }
        }
    }

    public void updateAccuracyAfterRace(boolean winner) {
        if (winner) {
            currentAccuracy += 0.02;
        }

        currentAccuracy -= burnoutCount * 0.01;

        if (currentAccuracy < 0.0) {
            currentAccuracy = 0.0;
        }

        if (currentAccuracy > 1.0) {
            currentAccuracy = 1.0;
        }
    }

    public void addResult(RaceResult result) {
        history.add(result);

        if (result.getWpm() > bestWPM) {
            bestWPM = result.getWpm();
        }
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public Color getColor() {
        return color;
    }

    public double getStartingAccuracy() {
        return startingAccuracy;
    }

    public double getCurrentAccuracy() {
        return currentAccuracy;
    }

    public int getProgress() {
        return progress;
    }

    public int getCorrectKeystrokes() {
        return correctKeystrokes;
    }

    public int getMistypes() {
        return mistypes;
    }

    public int getBurnoutCount() {
        return burnoutCount;
    }

    public boolean isBurntOut() {
        return burntOut;
    }

    public int getBurnoutTurnsRemaining() {
        return burnoutTurnsRemaining;
    }

    public String getStyle() {
        return style;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public String getAccessory() {
        return accessory;
    }

    public double getBestWPM() {
        return bestWPM;
    }

    public ArrayList<RaceResult> getHistory() {
        return history;
    }
}
