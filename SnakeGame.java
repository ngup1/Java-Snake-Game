import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;

public class SnakeGame extends JPanel implements ActionListener {
    private final int width;
    private final int height;
    private final int cellSize;
    private static final int FRAME_RATE = 20;
    private final GameController controller;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private int highScore;

    public SnakeGame(int width, int height) {
        this.width = width;
        this.height = height;
        this.cellSize = width / (FRAME_RATE * 2);
        this.controller = new GameController(width, height, cellSize);
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0, 0, 128));
    }

    public void startGame() {
        controller.reset();
        gameStarted = false;
        gameOver = false;
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(e.getKeyCode());
            }
        });
        new Timer(1000 / FRAME_RATE, this).start();
    }

    private void handleKeyEvent(int keyCode) {
        if (!gameStarted && keyCode == KeyEvent.VK_SPACE) {
            gameStarted = true;
        } else if (!gameOver) {
            switch (keyCode) {
                case KeyEvent.VK_UP -> controller.changeDirection(Direction.UP);
                case KeyEvent.VK_DOWN -> controller.changeDirection(Direction.DOWN);
                case KeyEvent.VK_LEFT -> controller.changeDirection(Direction.LEFT);
                case KeyEvent.VK_RIGHT -> controller.changeDirection(Direction.RIGHT);
            }
        } else if (keyCode == KeyEvent.VK_SPACE) {
            gameStarted = false;
            gameOver = false;
            controller.reset();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameStarted) {
            printMessage(g, "Press Space Bar to Begin Game");
        } else {
            drawGame(g);
        }
    }

    private void drawGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(new Color(0, 0, 64)); // Slightly lighter than background
    g2d.setStroke(new BasicStroke(1));

    // Draw vertical lines
    for (int x = 0; x < getWidth(); x += controller.getCellSize()) {
        g2d.drawLine(x, 0, x, getHeight());
    }

    // Draw horizontal lines
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
            int score = controller.getSnake().size();
            if (score > highScore) highScore = score;
            printMessage(g, "Your Score: " + score + "\nHigh Score: " + highScore + "\nPress Space Bar to Reset");
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
            gameOver = !controller.move();
        }
        repaint();
    }
}
