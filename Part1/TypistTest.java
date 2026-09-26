/**
 * Tests the behaviour of the Typist class.
 *
 * Each test checks an expected condition and reports whether
 * the behaviour passed or failed.
 *
 * @author Umer Liaquat
 * @version 2
 */
public class TypistTest {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        testTyping();
        testSlideBack();
        testBurnout();
        testAccuracyLimits();
        testReset();

        System.out.println();
        System.out.println("Test Summary");
        System.out.println("------------");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
    }

    /**
     * Checks whether a condition is true and prints the result.
     */
    private static void check(
            String testName,
            boolean condition) {

        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName);
            failed++;
        }
    }

    /**
     * Tests normal typing progress.
     */
    private static void testTyping() {

        Typist typist =
                new Typist('£', "Test Typist", 0.75);

        typist.typeCharacter();
        typist.typeCharacter();
        typist.typeCharacter();

        check(
                "Typing three characters increases progress to 3",
                typist.getProgress() == 3
        );
    }

    /**
     * Tests that sliding backwards cannot produce
     * negative progress.
     */
    private static void testSlideBack() {

        Typist typist =
                new Typist('£', "Test Typist", 0.75);

        typist.typeCharacter();
        typist.typeCharacter();
        typist.typeCharacter();

        typist.slideBack(5);

        check(
                "Progress cannot fall below zero",
                typist.getProgress() == 0
        );

        check(
                "Mistype flag is recorded after slideBack",
                typist.hasJustMistyped()
        );

        typist.clearMistypeFlag();

        check(
                "Mistype flag can be cleared",
                !typist.hasJustMistyped()
        );
    }

    /**
     * Tests burnout and recovery behaviour.
     */
    private static void testBurnout() {

        Typist typist =
                new Typist('£', "Test Typist", 0.75);

        typist.burnOut(3);

        check(
                "Typist enters burnout state",
                typist.isBurntOut()
        );

        check(
                "Burnout begins with three turns remaining",
                typist.getBurnoutTurnsRemaining() == 3
        );

        typist.recoverFromBurnout();

        check(
                "One recovery reduces burnout to two turns",
                typist.getBurnoutTurnsRemaining() == 2
        );

        typist.recoverFromBurnout();

        check(
                "Two recoveries leave one burnout turn",
                typist.getBurnoutTurnsRemaining() == 1
        );

        typist.recoverFromBurnout();

        check(
                "Typist recovers after final burnout turn",
                !typist.isBurntOut()
        );

        check(
                "Recovered typist has zero burnout turns",
                typist.getBurnoutTurnsRemaining() == 0
        );
    }

    /**
     * Tests that accuracy remains in the valid
     * range from 0.0 to 1.0.
     */
    private static void testAccuracyLimits() {

        Typist typist =
                new Typist('£', "Test Typist", 0.75);

        typist.setAccuracy(1.2);

        check(
                "Accuracy is capped at 1.0",
                typist.getAccuracy() == 1.0
        );

        typist.setAccuracy(-0.7);

        check(
                "Accuracy is floored at 0.0",
                typist.getAccuracy() == 0.0
        );

        typist.setAccuracy(0.5);

        check(
                "Valid accuracy is stored unchanged",
                typist.getAccuracy() == 0.5
        );
    }

    /**
     * Tests resetting a typist before a new race.
     */
    private static void testReset() {

        Typist typist =
                new Typist('£', "Test Typist", 0.75);

        typist.typeCharacter();
        typist.typeCharacter();
        typist.burnOut(2);

        typist.resetToStart();

        check(
                "Reset returns progress to zero",
                typist.getProgress() == 0
        );

        check(
                "Reset clears burnout state",
                !typist.isBurntOut()
        );

        check(
                "Reset clears burnout countdown",
                typist.getBurnoutTurnsRemaining() == 0
        );

        check(
                "Reset clears mistype state",
                !typist.hasJustMistyped()
        );
    }
}
