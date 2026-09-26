/**
 * Handles the simulation logic for a typing race.
 *
 * The GUI is responsible for displaying the race, while RaceEngine
 * is responsible for advancing typists and applying race rules.
 *
 * Separating the simulation from the Swing interface improves
 * separation of concerns and makes the race logic easier to maintain.
 *
 * @author Umer Liaquat
 * @version 2
 */
public class RaceEngine {

    private int passageLength;
    private int turnNumber;

    private static final int SLIDE_BACK_AMOUNT = 2;
    private static final int BURNOUT_DURATION = 3;

    /**
     * Creates a race engine for a passage of the given length.
     *
     * @param passageLength number of characters in the race passage
     */
    public RaceEngine(int passageLength) {
        this.passageLength = passageLength;
        this.turnNumber = 0;
    }

    /**
     * Advances the race by one turn.
     */
    public void nextTurn() {
        turnNumber++;
    }

    /**
     * Returns the current turn number.
     *
     * @return current race turn
     */
    public int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Advances one typist according to the current race rules.
     *
     * @param typist typist taking the turn
     * @param autocorrect whether autocorrect mode is enabled
     * @param caffeine whether caffeine mode is enabled
     */
    public void advanceTypist(
            GUITypist typist,
            boolean autocorrect,
            boolean caffeine) {

        if (typist.isBurntOut()) {
            typist.recoverFromBurnout();
            return;
        }

        double effectiveAccuracy = typist.getCurrentAccuracy();

        /*
         * Caffeine improves performance during the first ten turns.
         */
        if (caffeine && turnNumber <= 10) {
            effectiveAccuracy += 0.10;
        }

        /*
         * Energy Drink gives an early advantage but becomes a
         * disadvantage after the halfway point.
         */
        if (typist.getAccessory().equals("Energy Drink")) {
            if (typist.getProgress() < passageLength / 2) {
                effectiveAccuracy += 0.08;
            } else {
                effectiveAccuracy -= 0.08;
            }
        }

        effectiveAccuracy = clampAccuracy(effectiveAccuracy);

        /*
         * Attempt to type a character.
         */
        if (Math.random() < effectiveAccuracy) {
            typist.typeCharacter();

            /*
             * Caffeine can provide an additional character
             * during the early-race boost.
             */
            if (caffeine
                    && turnNumber <= 10
                    && Math.random() < 0.25) {

                typist.typeCharacter();
            }
        }

        /*
         * Autocorrect reduces the penalty for a mistype.
         */
        int slideAmount = SLIDE_BACK_AMOUNT;

        if (autocorrect) {
            slideAmount = 1;
        }

        double mistypeChance =
                (1 - effectiveAccuracy) * 0.25;

        /*
         * Noise-cancelling headphones reduce mistype probability.
         */
        if (typist.getAccessory()
                .equals("Noise-Cancelling Headphones")) {

            mistypeChance -= 0.05;
        }

        if (mistypeChance < 0.0) {
            mistypeChance = 0.0;
        }

        if (Math.random() < mistypeChance) {
            typist.slideBack(slideAmount);
        }

        /*
         * More accurate typists have a small burnout risk.
         */
        double burnoutChance =
                0.03 * effectiveAccuracy * effectiveAccuracy;

        /*
         * Caffeine increases burnout risk after the initial boost.
         */
        if (caffeine && turnNumber > 10) {
            burnoutChance += 0.03;
        }

        if (typist.getStyle().equals("Touch Typist")) {
            burnoutChance += 0.01;
        }

        /*
         * Wrist support reduces burnout probability.
         */
        if (typist.getAccessory().equals("Wrist Support")) {
            burnoutChance -= 0.01;
        }

        if (burnoutChance < 0.0) {
            burnoutChance = 0.0;
        }

        if (Math.random() < burnoutChance) {

            int duration = BURNOUT_DURATION;

            if (typist.getAccessory().equals("Wrist Support")) {
                duration = 2;
            }

            typist.burnOut(duration);
        }
    }

    /**
     * Ensures that an accuracy value stays between 0.0 and 1.0.
     *
     * @param value accuracy value to constrain
     * @return constrained accuracy
     */
    private double clampAccuracy(double value) {

        if (value < 0.0) {
            return 0.0;
        }

        if (value > 1.0) {
            return 1.0;
        }

        return value;
    }
}
