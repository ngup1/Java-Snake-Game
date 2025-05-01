import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;

public class SnakeGame extends JPanel implements ActionListener {
    private final int width;
    private final int height;
    private final int cellSize;
    private double speed;
    private double speedIncrease = 0.25;
    private final GameController controller;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private int highScore = 0;
    private int currentScore = 0;
    private Timer gameTimer;
    private boolean difficultySelected = false;

    public SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.cellSize = width / 40;
        this.controller = new GameController(width, height, cellSize);
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0, 0, 128));
    }

    public void startGame() {
        controller.reset();
        gameStarted = false;
        gameOver = false;
        difficultySelected = false;
        currentScore = 0;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(e.getKeyCode());
            }
        });
    }

    private void initializeTimer() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        int delay = (int) (1000 / speed); // Convert speed to delay in milliseconds
        gameTimer = new Timer(delay, this);
        gameTimer.start();
    }

    private void handleKeyEvent(int keyCode) {
        if (!difficultySelected) {
            switch (keyCode) {
                case KeyEvent.VK_1 -> { // Normal difficulty
                    speed = 10;
                    difficultySelected = true;
                    gameStarted = true;
                    initializeTimer();
                }
                case KeyEvent.VK_2 -> { // Hard difficulty
                    speed = 20;
                    difficultySelected = true;
                    gameStarted = true;
                    initializeTimer();
                }
            }
        } else if (!gameOver) {
            switch (keyCode) {
                case KeyEvent.VK_UP -> controller.changeDirection(Direction.UP);
                case KeyEvent.VK_DOWN -> controller.changeDirection(Direction.DOWN);
                case KeyEvent.VK_LEFT -> controller.changeDirection(Direction.LEFT);
                case KeyEvent.VK_RIGHT -> controller.changeDirection(Direction.RIGHT);
            }
        } else if (keyCode == KeyEvent.VK_SPACE) {
            startGame();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!difficultySelected) {
            printMessage(g, "Select Difficulty:\n1 - Normal\n2 - Hard");
        } else {
            drawGame(g);
            // Draw current score and high score
            drawScores(g);
        }
    }

    private void drawScores(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Score: " + currentScore, 10, 30);
        g.drawString("High Score: " + highScore, 10, 60);
    }

    private void drawGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0, 0, 64));
        g2d.setStroke(new BasicStroke(1));

        for (int x = 0; x < getWidth(); x += controller.getCellSize()) {
            g2d.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += controller.getCellSize()) {
            g2d.drawLine(0, y, getWidth(), y);
        }

        g.setColor(Color.RED);
        GamePoint food = controller.getFood();
        g.fillRect(food.x(), food.y(), cellSize, cellSize);

        Color snakeColor = Color.GREEN;
        for (GamePoint point : controller.getSnake().getBody()) {
            g.setColor(snakeColor);
            g.fillRect(point.x(), point.y(), cellSize, cellSize);
            int newGreen = (int) (snakeColor.getGreen() * 0.95);
            snakeColor = new Color(0, newGreen, 0);
        }

        if (gameOver) {
            if (currentScore > highScore) {
                highScore = currentScore;
            }
            printMessage(g, "Game Over!\nYour Score: " + currentScore + 
                          "\nHigh Score: " + highScore + 
                          "\nPress Space Bar to Reset");
        }
    }

    private void printMessage(Graphics g, String message) {
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(30f));
        int y = height / 3;
        Graphics2D g2 = (Graphics2D) g;
        var frc = g2.getFontRenderContext();

        for (String line : message.split("\n")) {
            TextLayout layout = new TextLayout(line, g.getFont(), frc);
            var bounds = layout.getBounds();
            float x = (float) (width - bounds.getWidth()) / 2;
            layout.draw(g2, x, y);
            y += g.getFontMetrics().getHeight();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStarted && !gameOver) {
            if (!controller.move()) {
                gameOver = true;
            } else if (controller.hasEatenFood()) {
                currentScore++;
                speed += speedIncrease;
                initializeTimer(); // Update timer with new speed
            }
        }
        repaint();
    }
}
