// Main.java
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Snake Game");
        frame.setSize(WIDTH, HEIGHT);

        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(
            null,
            "Select Difficulty:",
            "Difficulty",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        int delay;
        switch (choice) {
            case 0 -> delay = 150;  // Easy
            case 1 -> delay = 100;  // Medium
            case 2 -> delay = 60;   // Hard
            default -> delay = 100; // Default to Medium
        }

        final SnakeGame game = new SnakeGame(WIDTH, HEIGHT, delay);
        frame.add(game);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        game.startGame();
    }
} 