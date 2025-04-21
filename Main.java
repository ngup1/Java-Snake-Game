import javax.swing.JFrame;

public class Main{

    public static final int HEIGHT = 600;
    public static final int WIDTH = 800;
    public static void main(String[] args){
        JFrame frame = new JFrame("Snake Game");
        frame.setSize(WIDTH, HEIGHT);

        SnakeGame game = new SnakeGame(WIDTH, HEIGHT);   
        frame.add(game);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.pack();

        game.StartGame(); // Start the game after the frame is visible

    }

}


