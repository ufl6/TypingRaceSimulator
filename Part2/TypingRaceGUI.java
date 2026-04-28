
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.*;

public class TypingRaceGUI extends JFrame {

    private JComboBox<String> passageBox;
    private JTextArea customPassageArea;
    private JComboBox<Integer> seatCountBox;

    private JCheckBox autocorrectBox;
    private JCheckBox caffeineBox;
    private JCheckBox nightShiftBox;

    private JTextField[] nameFields;
    private JTextField[] symbolFields;
    private JComboBox<String>[] styleBoxes;
    private JComboBox<String>[] keyboardBoxes;
    private JComboBox<String>[] accessoryBoxes;
    private JButton[] colorButtons;
    private Color[] selectedColors;

    private JPanel racePanel;
    private JPanel typistPanel;
    private JPanel[] typistRows;

    private JTextArea statsArea;
    private JTextArea leaderboardArea;
    private JTextArea historyArea;
    private JTextArea comparisonArea;
    private JComboBox<String> comparisonBox;

    private ArrayList<GUITypist> typists;
    private ArrayList<JProgressBar> progressBars;
    private ArrayList<JLabel> passageLabels;

    private Timer timer;
    private Leaderboard leaderboard;

    private String passage;
    private int passageLength;
    private int globalTurn;
    private long raceStartTime;
    private boolean raceRunning;

    private static final int MAX_TYPISTS = 6;
    private static final int SLIDE_BACK_AMOUNT = 2;
    private static final int BURNOUT_DURATION = 3;

    public TypingRaceGUI() {
        leaderboard = new Leaderboard();
        typists = new ArrayList<>();
        progressBars = new ArrayList<>();
        passageLabels = new ArrayList<>();

        setTitle("Typing Race Simulator GUI");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buildConfigurationPanel();
        buildRacePanel();
        buildInformationPanel();

        setVisible(true);
    }

    public void startRaceGUI() {
        setVisible(true);
    }

    private void buildConfigurationPanel() {
        JPanel configPanel = new JPanel();
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        configPanel.setBorder(BorderFactory.createTitledBorder("Race Configuration"));

        passageBox = new JComboBox<>(new String[]{
            "Short Passage", "Medium Passage", "Long Passage", "Custom Passage"
        });
        passageBox.setMaximumSize(new Dimension(620, 30));

        customPassageArea = new JTextArea("Custom passage goes here.");
        customPassageArea.setLineWrap(true);
        customPassageArea.setWrapStyleWord(true);

        JScrollPane customScroll = new JScrollPane(customPassageArea);
        customScroll.setMaximumSize(new Dimension(620, 90));

        seatCountBox = new JComboBox<>(new Integer[]{2, 3, 4, 5, 6});
        seatCountBox.setMaximumSize(new Dimension(620, 30));
        seatCountBox.addActionListener(e -> updateTypistVisibility());

        autocorrectBox = new JCheckBox("Autocorrect: slide-back is halved");
        caffeineBox = new JCheckBox("Caffeine Mode: speed boost first 10 turns, then burnout risk");
        nightShiftBox = new JCheckBox("Night Shift: lower accuracy");

        autocorrectBox.setMaximumSize(new Dimension(620, 30));
        caffeineBox.setMaximumSize(new Dimension(620, 30));
        nightShiftBox.setMaximumSize(new Dimension(620, 30));

        configPanel.add(new JLabel("Passage Selection:"));
        configPanel.add(passageBox);
        configPanel.add(Box.createVerticalStrut(8));

        configPanel.add(new JLabel("Custom Passage:"));
        configPanel.add(customScroll);
        configPanel.add(Box.createVerticalStrut(8));

        configPanel.add(new JLabel("Number of Typists:"));
        configPanel.add(seatCountBox);
        configPanel.add(Box.createVerticalStrut(8));

        configPanel.add(autocorrectBox);
        configPanel.add(caffeineBox);
        configPanel.add(nightShiftBox);
        configPanel.add(Box.createVerticalStrut(8));

        nameFields = new JTextField[MAX_TYPISTS];
        symbolFields = new JTextField[MAX_TYPISTS];
        styleBoxes = new JComboBox[MAX_TYPISTS];
        keyboardBoxes = new JComboBox[MAX_TYPISTS];
        accessoryBoxes = new JComboBox[MAX_TYPISTS];
        colorButtons = new JButton[MAX_TYPISTS];
        typistRows = new JPanel[MAX_TYPISTS];

        selectedColors = new Color[]{
            Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN
        };

        typistPanel = new JPanel();
        typistPanel.setLayout(new BoxLayout(typistPanel, BoxLayout.Y_AXIS));

        configPanel.add(new JLabel("Typist Configuration:"));

        for (int i = 0; i < MAX_TYPISTS; i++) {
            JPanel row = new JPanel(new GridLayout(6, 1, 4, 4));
            row.setBorder(BorderFactory.createTitledBorder("Typist " + (i + 1)));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
            typistRows[i] = row;

            nameFields[i] = new JTextField("Typist " + (i + 1));
            symbolFields[i] = new JTextField("" + (char) ('A' + i));

            styleBoxes[i] = new JComboBox<>(new String[]{
                "Touch Typist", "Hunt & Peck", "Phone Thumbs", "Voice-to-Text"
            });

            keyboardBoxes[i] = new JComboBox<>(new String[]{
                "Mechanical", "Membrane", "Touchscreen", "Stenography"
            });

            accessoryBoxes[i] = new JComboBox<>(new String[]{
                "None", "Wrist Support", "Energy Drink", "Noise-Cancelling Headphones"
            });

            // styleBoxes[i].setPreferredSize(new Dimension(170, 30));
            // keyboardBoxes[i].setPreferredSize(new Dimension(170, 30));
            // accessoryBoxes[i].setPreferredSize(new Dimension(170, 30));
            final int index = i;
            colorButtons[i] = new JButton("Choose Colour");
            colorButtons[i].setBackground(selectedColors[i]);
            colorButtons[i].addActionListener(e -> chooseColour(index));

            JPanel nameSymbolPanel = new JPanel(new GridLayout(1, 2, 5, 0));
            nameSymbolPanel.add(nameFields[i]);
            nameSymbolPanel.add(symbolFields[i]);

            JPanel optionsPanel = new JPanel(new GridLayout(1, 3, 5, 0));
            optionsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
            optionsPanel.add(styleBoxes[i]);
            optionsPanel.add(keyboardBoxes[i]);
            optionsPanel.add(accessoryBoxes[i]);

            row.add(new JLabel("Name / Symbol:"));
            row.add(nameSymbolPanel);

            row.add(new JLabel("Style / Keyboard / Accessory:"));
            row.add(optionsPanel);

            row.add(new JLabel("Progress Colour:"));
            row.add(colorButtons[i]);

            typistPanel.add(row);
        }

        configPanel.add(typistPanel);
        configPanel.add(Box.createVerticalStrut(8));

        JButton startButton = new JButton("Start Race");
        startButton.setMaximumSize(new Dimension(620, 35));
        startButton.addActionListener((ActionEvent e) -> startRace());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(startButton);
        configPanel.add(buttonPanel);

        JScrollPane configScroll = new JScrollPane(configPanel);
        configScroll.setPreferredSize(new Dimension(720, 700));
        configScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(configScroll, BorderLayout.WEST);

        updateTypistVisibility();
    }

    private void updateTypistVisibility() {
        if (typistRows == null || typistPanel == null) {
            return;
        }

        int seats = (Integer) seatCountBox.getSelectedItem();

        for (int i = 0; i < MAX_TYPISTS; i++) {
            typistRows[i].setVisible(i < seats);
        }

        typistPanel.revalidate();
        typistPanel.repaint();
    }

    private void buildRacePanel() {
        racePanel = new JPanel();
        racePanel.setLayout(new BoxLayout(racePanel, BoxLayout.Y_AXIS));
        racePanel.setBorder(BorderFactory.createTitledBorder("Race Display"));

        JScrollPane raceScroll = new JScrollPane(racePanel);
        raceScroll.setPreferredSize(new Dimension(650, 700));

        add(raceScroll, BorderLayout.CENTER);
    }

    private void buildInformationPanel() {
        JPanel infoPanel = new JPanel(new GridLayout(2, 2));

        statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setBorder(BorderFactory.createTitledBorder("Race Statistics"));

        leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        leaderboardArea.setBorder(BorderFactory.createTitledBorder("Leaderboard"));

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBorder(BorderFactory.createTitledBorder("Historical Data"));

        JPanel comparisonPanel = new JPanel(new BorderLayout());
        comparisonPanel.setBorder(BorderFactory.createTitledBorder("Comparison View"));

        comparisonBox = new JComboBox<>(new String[]{"WPM", "Accuracy", "Burnouts", "Points"});
        comparisonBox.addActionListener(e -> updateComparisonView());

        comparisonArea = new JTextArea();
        comparisonArea.setEditable(false);

        comparisonPanel.add(comparisonBox, BorderLayout.NORTH);
        comparisonPanel.add(new JScrollPane(comparisonArea), BorderLayout.CENTER);

        infoPanel.add(new JScrollPane(statsArea));
        infoPanel.add(new JScrollPane(leaderboardArea));
        infoPanel.add(new JScrollPane(historyArea));
        infoPanel.add(comparisonPanel);

        add(infoPanel, BorderLayout.SOUTH);
    }

    private void chooseColour(int index) {
        Color chosen = JColorChooser.showDialog(this, "Choose Typist Colour", selectedColors[index]);

        if (chosen != null) {
            selectedColors[index] = chosen;
            colorButtons[index].setBackground(chosen);
        }
    }

    private void startRace() {
        typists.clear();
        progressBars.clear();
        passageLabels.clear();
        racePanel.removeAll();

        statsArea.setText("");
        comparisonArea.setText("");

        passage = getSelectedPassage();
        passageLength = passage.length();

        int seats = (Integer) seatCountBox.getSelectedItem();

        JLabel mainPassageLabel = new JLabel("<html><b>Passage:</b> " + escapeHtml(passage) + "</html>");
        racePanel.add(mainPassageLabel);
        racePanel.add(Box.createVerticalStrut(10));

        for (int i = 0; i < seats; i++) {
            String name = nameFields[i].getText();
            String symbol = symbolFields[i].getText();

            if (symbol.length() == 0) {
                symbol = "?";
            }

            String style = (String) styleBoxes[i].getSelectedItem();
            String keyboard = (String) keyboardBoxes[i].getSelectedItem();
            String accessory = (String) accessoryBoxes[i].getSelectedItem();

            double accuracy = calculateStartingAccuracy(style, keyboard, accessory);

            GUITypist typist = new GUITypist(
                    name,
                    symbol,
                    selectedColors[i],
                    accuracy,
                    style,
                    keyboard,
                    accessory
            );

            typists.add(typist);

            JLabel infoLabel = new JLabel(name + " " + symbol
                    + " | Style: " + style
                    + " | Keyboard: " + keyboard
                    + " | Accessory: " + accessory
                    + " | Starting Accuracy: " + String.format("%.2f", accuracy));

            JProgressBar bar = new JProgressBar(0, passageLength);
            bar.setStringPainted(true);
            bar.setForeground(selectedColors[i]);

            JLabel typedPassageLabel = new JLabel(buildPassageHTML(typist));

            racePanel.add(infoLabel);
            racePanel.add(bar);
            racePanel.add(typedPassageLabel);
            racePanel.add(Box.createVerticalStrut(10));

            progressBars.add(bar);
            passageLabels.add(typedPassageLabel);
        }

        racePanel.revalidate();
        racePanel.repaint();

        globalTurn = 0;
        raceRunning = true;
        raceStartTime = System.currentTimeMillis();

        timer = new Timer(200, e -> raceTurn());
        timer.start();
    }

    private String getSelectedPassage() {
        String selected = (String) passageBox.getSelectedItem();

        if (selected.equals("Short Passage")) {
            return "The quick brown fox jumps over the lazy dog.";
        } else if (selected.equals("Medium Passage")) {
            return "Object oriented programming uses classes and objects to organise code clearly.";
        } else if (selected.equals("Long Passage")) {
            return "A typing race simulator demonstrates encapsulation, event handling, inheritance, and graphical interface design in Java.";
        } else {
            return customPassageArea.getText();
        }
    }

    private double calculateStartingAccuracy(String style, String keyboard, String accessory) {
        double accuracy = 0.60;

        if (style.equals("Touch Typist")) {
            accuracy += 0.20;
        } else if (style.equals("Hunt & Peck")) {
            accuracy -= 0.12;
        } else if (style.equals("Phone Thumbs")) {
            accuracy -= 0.05;
        } else if (style.equals("Voice-to-Text")) {
            accuracy += 0.10;
        }

        if (keyboard.equals("Mechanical")) {
            accuracy += 0.05;
        } else if (keyboard.equals("Touchscreen")) {
            accuracy -= 0.08;
        } else if (keyboard.equals("Stenography")) {
            accuracy += 0.12;
        }

        if (accessory.equals("Noise-Cancelling Headphones")) {
            accuracy += 0.05;
        }

        if (nightShiftBox.isSelected()) {
            accuracy -= 0.10;
        }

        return clampAccuracy(accuracy);
    }

    private void raceTurn() {
        if (!raceRunning) {
            return;
        }

        globalTurn++;

        GUITypist winner = null;

        for (GUITypist typist : typists) {
            advanceTypist(typist);

            if (typist.getProgress() >= passageLength && winner == null) {
                winner = typist;
            }
        }

        updateRaceDisplay();

        if (winner != null) {
            finishRace(winner);
        }
    }

    private void advanceTypist(GUITypist typist) {
        if (typist.isBurntOut()) {
            typist.recoverFromBurnout();
            return;
        }

        double effectiveAccuracy = typist.getCurrentAccuracy();

        if (caffeineBox.isSelected() && globalTurn <= 10) {
            effectiveAccuracy += 0.10;
        }

        if (typist.getAccessory().equals("Energy Drink")) {
            if (typist.getProgress() < passageLength / 2) {
                effectiveAccuracy += 0.08;
            } else {
                effectiveAccuracy -= 0.08;
            }
        }

        effectiveAccuracy = clampAccuracy(effectiveAccuracy);

        if (Math.random() < effectiveAccuracy) {
            typist.typeCharacter();

            if (caffeineBox.isSelected() && globalTurn <= 10 && Math.random() < 0.25) {
                typist.typeCharacter();
            }
        }

        int slideAmount = SLIDE_BACK_AMOUNT;

        if (autocorrectBox.isSelected()) {
            slideAmount = 1;
        }

        double mistypeChance = (1 - effectiveAccuracy) * 0.25;

        if (typist.getAccessory().equals("Noise-Cancelling Headphones")) {
            mistypeChance -= 0.05;
        }

        if (mistypeChance < 0.0) {
            mistypeChance = 0.0;
        }

        if (Math.random() < mistypeChance) {
            typist.slideBack(slideAmount);
        }

        double burnoutChance = 0.03 * effectiveAccuracy * effectiveAccuracy;

        if (caffeineBox.isSelected() && globalTurn > 10) {
            burnoutChance += 0.03;
        }

        if (typist.getStyle().equals("Touch Typist")) {
            burnoutChance += 0.01;
        }

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

    private void updateRaceDisplay() {
        for (int i = 0; i < typists.size(); i++) {
            GUITypist typist = typists.get(i);
            JProgressBar bar = progressBars.get(i);

            int progress = typist.getProgress();

            if (progress > passageLength) {
                progress = passageLength;
            }

            bar.setValue(progress);

            String text = typist.getSymbol() + " " + progress + "/" + passageLength;

            if (typist.isBurntOut()) {
                text += " | BURNT OUT (" + typist.getBurnoutTurnsRemaining() + ")";
            }

            bar.setString(text);
            passageLabels.get(i).setText(buildPassageHTML(typist));
        }
    }

    private void finishRace(GUITypist winner) {
        raceRunning = false;
        timer.stop();

        long timeTaken = System.currentTimeMillis() - raceStartTime;
        double minutes = timeTaken / 60000.0;

        ArrayList<GUITypist> finishingOrder = new ArrayList<>(typists);

        Collections.sort(finishingOrder, (a, b) -> b.getProgress() - a.getProgress());

        String statsText = "Winner: " + winner.getName() + "\n\n";
        String historyText = "Race History\n\n";

        for (int i = 0; i < finishingOrder.size(); i++) {
            GUITypist typist = finishingOrder.get(i);
            int position = i + 1;

            double words = passageLength / 5.0;
            double wpm = words / minutes;

            int totalAttempts = typist.getCorrectKeystrokes() + typist.getMistypes();
            double accuracyPercentage = 100.0;

            if (totalAttempts > 0) {
                accuracyPercentage = (typist.getCorrectKeystrokes() * 100.0) / totalAttempts;
            }

            double before = typist.getCurrentAccuracy();
            typist.updateAccuracyAfterRace(typist == winner);
            double after = typist.getCurrentAccuracy();

            RaceResult result = new RaceResult(
                    typist.getName(),
                    position,
                    wpm,
                    accuracyPercentage,
                    typist.getBurnoutCount(),
                    before,
                    after
            );

            typist.addResult(result);
            leaderboard.addRacePoints(typist.getName(), position, wpm, typist.getBurnoutCount());

            statsText += typist.getName()
                    + " | Position: " + position
                    + " | WPM: " + String.format("%.2f", wpm)
                    + " | Accuracy: " + String.format("%.2f", accuracyPercentage) + "%"
                    + " | Burnouts: " + typist.getBurnoutCount()
                    + " | Accuracy Change: " + String.format("%.2f", before)
                    + " -> " + String.format("%.2f", after)
                    + " | Best WPM: " + String.format("%.2f", typist.getBestWPM())
                    + "\n";

            historyText += typist.getName()
                    + " finished position " + position
                    + " with " + String.format("%.2f", wpm)
                    + " WPM and " + typist.getBurnoutCount()
                    + " burnouts.\n";
        }

        statsArea.setText(statsText);
        leaderboardArea.setText(leaderboard.getLeaderboardText());
        historyArea.setText(historyText);
        updateComparisonView();

        JOptionPane.showMessageDialog(this, winner.getName() + " wins the race!");
    }

    private void updateComparisonView() {
        if (typists == null || typists.size() == 0) {
            return;
        }

        String metric = (String) comparisonBox.getSelectedItem();
        String text = "Comparison by " + metric + "\n\n";

        for (GUITypist typist : typists) {
            if (metric.equals("WPM")) {
                text += typist.getName() + " best WPM: " + String.format("%.2f", typist.getBestWPM()) + "\n";
            } else if (metric.equals("Accuracy")) {
                text += typist.getName() + " current accuracy: " + String.format("%.2f", typist.getCurrentAccuracy()) + "\n";
            } else if (metric.equals("Burnouts")) {
                text += typist.getName() + " burnouts last race: " + typist.getBurnoutCount() + "\n";
            } else {
                text += typist.getName() + " see leaderboard points above\n";
            }
        }

        comparisonArea.setText(text);
    }

    private String buildPassageHTML(GUITypist typist) {
        int progress = typist.getProgress();

        if (progress > passageLength) {
            progress = passageLength;
        }

        String completed = escapeHtml(passage.substring(0, progress));
        String remaining = escapeHtml(passage.substring(progress));

        String colorHex = String.format("#%02x%02x%02x",
                typist.getColor().getRed(),
                typist.getColor().getGreen(),
                typist.getColor().getBlue());

        return "<html><span style='color:" + colorHex + "; font-weight:bold;'>"
                + completed
                + "</span><span style='background-color:yellow;'>"
                + typist.getSymbol()
                + "</span><span style='color:gray;'>"
                + remaining
                + "</span></html>";
    }

    private String escapeHtml(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private double clampAccuracy(double value) {
        if (value < 0.0) {
            return 0.0;
        }

        if (value > 1.0) {
            return 1.0;
        }

        return value;
    }

    public static void main(String[] args) {
        new TypingRaceGUI();
    }
}
