import javax.swing.JFrame;
import java.awt.GridLayout;

public class Window extends JFrame {

    Screen screen;

    public Window(int width, int height, String title) {
        setTitle(title);
        setSize(width, height);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setGridLayout();
    }

    void setGridLayout() {
        screen = new Screen(this);

        add(screen);
        setLayout(new GridLayout(1,1,0,0));
        setVisible(true);
    }
}