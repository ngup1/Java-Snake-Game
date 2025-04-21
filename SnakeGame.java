import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;






public class SnakeGame extends JPanel{

    private final int height;
    private final int width;

    public SnakeGame(int width, int height){
        super();
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setBackground(new Color(0, 0, 128));
    }

public void StartGame(){
    repaint(); // Call repaint to trigger the paintComponent method

}


    
}
