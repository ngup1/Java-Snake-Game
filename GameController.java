import java.util.Random;

public class GameController {
    private final int width;
    private final int height;
    private final int cellSize;
    private final Random random = new Random();
    private final Snake snake;
    private GamePoint food;
    private Direction direction = Direction.RIGHT;
    private Direction newDirection = Direction.RIGHT;

    public GameController(int width, int height, int cellSize) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.snake = new Snake(new GamePoint(width / 2, height / 2));
        generateFood();
    }
    
    public int getCellSize() {
        return cellSize;
    }
    

    public void reset() {
        snake.reset(new GamePoint(width / 2, height / 2));
        direction = Direction.RIGHT;
        newDirection = Direction.RIGHT;
        generateFood();
    }

    public void changeDirection(Direction dir) {
        if ((direction == Direction.UP && dir != Direction.DOWN) ||
            (direction == Direction.DOWN && dir != Direction.UP) ||
            (direction == Direction.LEFT && dir != Direction.RIGHT) ||
            (direction == Direction.RIGHT && dir != Direction.LEFT)) {
            newDirection = dir;
        }
    }

    public boolean move() {
        direction = newDirection;
        GamePoint head = snake.getHead();
        GamePoint newHead = switch (direction) {
            case UP -> new GamePoint(head.x(), head.y() - cellSize);
            case DOWN -> new GamePoint(head.x(), head.y() + cellSize);
            case LEFT -> new GamePoint(head.x() - cellSize, head.y());
            case RIGHT -> new GamePoint(head.x() + cellSize, head.y());
        };
    
        // Check for wall collision
        if (newHead.x() < 0 || newHead.x() >= width || newHead.y() < 0 || newHead.y() >= height) {
            return false;
        }
    
        // Check for self-collision
        if (snake.contains(newHead)) {
            return false;
        }
    
        // Handle food consumption or normal move
        if (newHead.equals(food)) {
            snake.grow(newHead);
            generateFood();
        } else {
            snake.move(newHead);
        }
    
        return true;
    }
    

    public GamePoint getFood() {
        return food;
    }

    public Snake getSnake() {
        return snake;
    }

    private void generateFood() {
        do {
            food = new GamePoint(random.nextInt(width / cellSize) * cellSize,
                                 random.nextInt(height / cellSize) * cellSize);
        } while (snake.contains(food));
    }
}
