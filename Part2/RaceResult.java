
public class RaceResult {

    private String typistName;
    private int position;
    private double wpm;
    private double accuracyPercentage;
    private int burnoutCount;
    private double accuracyBefore;
    private double accuracyAfter;

    public RaceResult(String typistName, int position, double wpm,
            double accuracyPercentage, int burnoutCount,
            double accuracyBefore, double accuracyAfter) {
        this.typistName = typistName;
        this.position = position;
        this.wpm = wpm;
        this.accuracyPercentage = accuracyPercentage;
        this.burnoutCount = burnoutCount;
        this.accuracyBefore = accuracyBefore;
        this.accuracyAfter = accuracyAfter;
    }

    public String getTypistName() {
        return typistName;
    }

    public int getPosition() {
        return position;
    }

    public double getWpm() {
        return wpm;
    }

    public double getAccuracyPercentage() {
        return accuracyPercentage;
    }

    public int getBurnoutCount() {
        return burnoutCount;
    }

    public double getAccuracyBefore() {
        return accuracyBefore;
    }

    public double getAccuracyAfter() {
        return accuracyAfter;
    }
}
