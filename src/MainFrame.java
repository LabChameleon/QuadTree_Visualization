import javafx.scene.shape.Circle;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Julian on 11.03.2015.
 */
public class MainFrame {

    private JFrame mainFrame = new JFrame();
    private FlowLayout mainLayout = new FlowLayout();
    private JPanel buttonPanel = new JPanel(null);
    private DrawPanel mainPanel = new DrawPanel();
    private JPanel masterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JCheckBox insertGridCheck = new JCheckBox();
    private JCheckBox pointGridCheck = new JCheckBox();
    private CollisionDetection collisionDetection = new CollisionDetection(4);
    private int rectangleSize = 5;
    private JLabel isCollisionPossible = new JLabel("");

    public MainFrame() {

        mainFrame.setResizable(false);
        mainFrame.setTitle("QuadTreeCollision");
        mainFrame.setPreferredSize(new Dimension(800, 600));
        mainFrame.addWindowListener(new TestWindowListener());

        initFrame();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void initFrame(){

        mainFrame.setLayout(mainLayout);

        mainPanel.setPreferredSize(new Dimension(590,600));
        buttonPanel.setPreferredSize(new Dimension(200,600));

        insertGridCheck.setBounds(10, 20, 200, 20);
        insertGridCheck.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.repaint();
            }
        });
        insertGridCheck.setText("Show Insertion Grid");
        insertGridCheck.setSelected(true);
        buttonPanel.add(insertGridCheck);

        pointGridCheck.setBounds(10, 40, 200, 20);
        pointGridCheck.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.repaint();
            }
        });
        pointGridCheck.setText("Show Mouseposition Grid");
        pointGridCheck.setSelected(true);
        buttonPanel.add(pointGridCheck);

        JLabel treeHeightLabel = new JLabel("Height of the Quadtree:");
        treeHeightLabel.setBounds(15, 60, 200, 50);
        buttonPanel.add(treeHeightLabel);

        final JSlider treeHeight = new JSlider(1,10,4);
        treeHeight.setBounds(10, 95, 170, 50);
        treeHeight.setMajorTickSpacing(1);
        treeHeight.setMinorTickSpacing(1);
        treeHeight.setPaintTicks(true);
        treeHeight.setPaintLabels(true);
        treeHeight.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                collisionDetection = new CollisionDetection(treeHeight.getValue());
                mainFrame.repaint();
            }
        });
        buttonPanel.add(treeHeight);

        JLabel rectangleSizeLabel = new JLabel("Size of the Rectangle:");
        rectangleSizeLabel.setBounds(15, 140, 200, 50);
        buttonPanel.add(rectangleSizeLabel);

        final JSlider rectangleSizeSlider = new JSlider(1,10,5);
        rectangleSizeSlider.setBounds(10, 175, 170, 50);
        rectangleSizeSlider.setMajorTickSpacing(1);
        rectangleSizeSlider.setMinorTickSpacing(1);
        rectangleSizeSlider.setPaintTicks(true);
        rectangleSizeSlider.setPaintLabels(true);
        rectangleSizeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                rectangleSize = rectangleSizeSlider.getValue();
                mainFrame.repaint();
            }
        });
        buttonPanel.add(rectangleSizeSlider);

        Font font = new Font("arial", Font.BOLD, 17 );

        isCollisionPossible.setBounds(10, 250, 200, 50);
        isCollisionPossible.setFont(font);
        buttonPanel.add(isCollisionPossible);

        masterPanel.add(buttonPanel);
        masterPanel.add(mainPanel);

        mainFrame.add(masterPanel);

    }



    class TestWindowListener extends WindowAdapter {

        public void windowClosing(WindowEvent e) {

            e.getWindow().dispose();
            System.exit(0);
        }
    }

    class DrawPanel extends JPanel {

        private boolean allowInsertion = false;

        private int MouseXPos = 0;
        private int MouseYPos = 0;

        private int MouseXPos2 = 0;
        private int MouseYPos2 = 0;

        public DrawPanel() {

            super(null);
            this.setDoubleBuffered(true);

            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        MouseXPos = e.getX();
                        MouseYPos = e.getY();

                        allowInsertion = false;

                        repaint();
                        mainFrame.repaint();
                    }
                    else if(SwingUtilities.isRightMouseButton(e)){
                        MouseXPos2 = e.getX();
                        MouseYPos2 = e.getY();

                        allowInsertion = true;

                        repaint();
                        mainFrame.repaint();
                    }
                }
            });
        }


        public void paintComponent(Graphics g) {

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, this.getBounds().width-11, this.getBounds().height-47);

            g.setColor(Color.RED);
            Rectangle rectangle = new Rectangle(MouseXPos, MouseYPos, rectangleSize * 16, rectangleSize * 16);

            g.setColor(Color.GREEN);
            collisionDetection.insertInTree(g, insertGridCheck.isSelected(), rectangle, new Rectangle(0, 0, this.getBounds().width-11, this.getBounds().height-47), 0);

            if(allowInsertion == true) {
                g.setColor(Color.BLUE);
                if(collisionDetection.searchInTree(g, pointGridCheck.isSelected(), new Point(MouseXPos2, MouseYPos2), new Rectangle(0, 0, this.getBounds().width - 11, this.getBounds().height - 47), 0) != null)
                    isCollisionPossible.setText("Collision possible");
                else
                    isCollisionPossible.setText("Collision not possible");
            }
            else{
                isCollisionPossible.setText("");
            }
            g.setColor(Color.RED);
            g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }
}
