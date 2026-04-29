
/**
 * Tests all features of the Typist class, including typing characters, sliding back, burnout mechanics, accuracy adjustments, and resetting to start.
 * Each test case checks the expected behavior against the actual state of the Typist instance after performing specific actions.
 *
 * @author Umer Liaquat
 * @version 1
 */
public class TypistTest {

    public static void main(String[] args) {
        Typist test = new Typist('£', "Test Typist", 0.75);

        // Test 1: typeCharacter
        test.typeCharacter();
        test.typeCharacter();
        test.typeCharacter();
        System.out.println("Progress after 3 characters should be 3. Current Progress: " + test.getProgress());

        // Test 2: slideback shouldn't be less than zero
        test.slideBack(5);
        System.out.println("Progress after sliding back 5 should be 0. Current Progress: " + test.getProgress());

        // Test 3: countdown for burnout
        test.burnOut(3);
        System.out.println("Is typist burnt out? Should be true. Burnt out: " + test.isBurntOut());
        System.out.println("Burnout turns remaining should be 3. Current burnout turns remaining: " + test.getBurnoutTurnsRemaining());

        test.recoverFromBurnout();
        System.out.println("After 1 recovery, burnout turns remaining should be 2. Current burnout turns remaining: " + test.getBurnoutTurnsRemaining());
        test.recoverFromBurnout();
        System.out.println("After 2 recoveries, burnout turns remaining should be 1. Current burnout turns remaining: " + test.getBurnoutTurnsRemaining());
        test.recoverFromBurnout();
        System.out.println("After 3 recoveries, typist should no longer be burnt out. Burnt out: " + test.isBurntOut());
        System.out.println("Burnout turns remaining should be 0. Current burnout turns remaining: " + test.getBurnoutTurnsRemaining());

        // Test 4: testing accuracy in range
        test.setAccuracy(1.2);
        System.out.println("Accuracy should be capped at 1.0. Current accuracy: " + test.getAccuracy());
        test.setAccuracy(-0.7);
        System.out.println("Accuracy should be floored at 0.0. Current accuracy: " + test.getAccuracy());
        test.setAccuracy(0.5);
        System.out.println("Accuracy should be set to 0.5. Current accuracy: " + test.getAccuracy());

        // Test 5: resetToStart()
        test.typeCharacter();
        test.typeCharacter();
        test.burnOut(2);
        test.resetToStart();

        System.out.println("After reset, progress should be 0. Current Progress: " + test.getProgress());
        System.out.println("After reset, typist should not be burnt out. Burnt out: " + test.isBurntOut());
        System.out.println("After reset, burnout turns remaining should be 0. Current burnout turns remaining: " + test.getBurnoutTurnsRemaining());

    }
}
