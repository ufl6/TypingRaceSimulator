
import javax.swing.*;
import java.awt.*;

public class TypingRaceGUI {

    private JFrame frame;
    private JTextArea raceOutput;
    private JButton startButton;

    public TypingRaceGUI() {
        frame = new JFrame("Typing Race Simulator");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout
        frame.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Typing Race Simulator", JLabel.CENTER);
        frame.add(title, BorderLayout.NORTH);

        // Output area
        raceOutput = new JTextArea();
        raceOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raceOutput);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start Race");
        buttonPanel.add(startButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Button event
        startButton.addActionListener(e -> startRace());

        frame.setVisible(true);
    }

    private void startRace() {
        raceOutput.setText("");

        // Create race
        TypingRace race = new TypingRace(40);
        race.addTypist(new Typist('%', "Mohammed", 0.85), 1);
        race.addTypist(new Typist('*', "Aisha", 0.60), 2);
        race.addTypist(new Typist('$', "Omar", 0.30), 3);

        // Run race in separate thread to keep GUI responsive
        new Thread(() -> {
            race.startRaceGUI(raceOutput);
        }).start();
    }

    public static void main(String[] args) {
        new TypingRaceGUI();
    }
}
