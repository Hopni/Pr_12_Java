import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MainFrame extends JFrame {
    private JPanel listsPanel;
    private JList leftList, rightList;
    private JButton moveToRight, moveToLeft;
    private JPanel tablePanel;
    private JPanel buttonsPanel;
    private DefaultListModel<String> modelLeft, modelRight;
    public static int SIZE = 3;

    public MainFrame(){
        super("Card app");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLayout(new BorderLayout());
        setBounds(500, 200, 500, 500);

        createListPanel();
        createTablePanel();
        createButtonsPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Lists", listsPanel);
        tabbedPane.addTab("Table", tablePanel);
        tabbedPane.addTab("Radio buttons", buttonsPanel);
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    private void createListPanel() {
        modelLeft = new DefaultListModel<>();
        modelRight = new DefaultListModel<>();
        for (int i = 0; i < 5; i++) {
            modelLeft.addElement("LeftList " + i);
            modelRight.addElement("RightList " + i);
        }
        listsPanel = new JPanel(new BorderLayout());
        JPanel centerListPanel = new JPanel(new BorderLayout());
        listsPanel.add(centerListPanel, BorderLayout.CENTER);
        leftList = new JList<String>(modelLeft);
        rightList = new JList<String>(modelRight);
        leftList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        rightList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        leftList.setLayoutOrientation(JList.VERTICAL);
        rightList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane leftScroller = new JScrollPane(leftList);
        leftScroller.setPreferredSize(new Dimension(150, 50));
        JScrollPane rightScroller = new JScrollPane(rightList);
        rightScroller.setPreferredSize(new Dimension(150, 50));
        listsPanel.add(leftScroller, BorderLayout.WEST);
        listsPanel.add(rightScroller, BorderLayout.EAST);
        moveToLeft = new JButton("<--");
        moveToRight = new JButton("-->");
        centerListPanel.add(moveToLeft, BorderLayout.SOUTH);
        centerListPanel.add(moveToRight, BorderLayout.NORTH);
        moveToRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!leftList.isSelectionEmpty()) {
                    int selections[] = leftList.getSelectedIndices();
                    for (int i = selections.length - 1; i > -1; i--) {
                        modelRight.addElement(modelLeft.get(selections[i]));
                        modelLeft.remove(selections[i]);
                    }
                    moveToLeft.setEnabled(true);
                    if (modelLeft.isEmpty()) {
                        moveToRight.setEnabled(false);
                    }
                }
            }
        });
        moveToLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!rightList.isSelectionEmpty()) {
                    int selections[] = rightList.getSelectedIndices();
                    for (int i = selections.length - 1; i > -1; i--) {
                        modelLeft.addElement(modelRight.get(selections[i]));
                        modelRight.remove(selections[i]);
                    }
                    moveToRight.setEnabled(true);

                    if (modelRight.isEmpty()) {
                        moveToLeft.setEnabled(false);
                    }
                }
            }
        });
    }

    private void createTablePanel(){
        tablePanel = new JPanel(new GridLayout(SIZE, SIZE));
        MouseListener ml = new MouseAdapter() {
            private String capturing;
            private final Color BACKGROUND_COLOR = UIManager.getColor(new JButton());
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if(e.getButton() == MouseEvent.BUTTON1){
                    JButton button = (JButton)e.getSource();
                    capturing = button.getText();
                    button.setText("Pressed");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                JButton button = (JButton)e.getSource();
                button.setText(capturing);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                JButton button = (JButton)e.getSource();
                button.setBackground(new Color(255, 255, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                JButton button = (JButton)e.getSource();
                button.setBackground(BACKGROUND_COLOR);
            }
        };
        for(int i = 0; i < SIZE*SIZE; i++){
            JButton button = new JButton("Button " + i);
            button.addMouseListener(ml);
            tablePanel.add(button);
        }
    }

    private void createButtonsPanel() {
        buttonsPanel = new JPanel(new GridLayout(SIZE, 2));
        ButtonGroup buttonGroup = new ButtonGroup();

        BufferedImage cancelImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        BasicStroke basicStroke = new BasicStroke(10);
        Graphics2D cancelImageGraphics = (Graphics2D)cancelImage.getGraphics();
        cancelImageGraphics.setColor(Color.WHITE);
        cancelImageGraphics.fillRect(0, 0, 50, 50);
        cancelImageGraphics.setColor(Color.GRAY);
        cancelImageGraphics.setStroke(basicStroke);
        cancelImageGraphics.drawLine(0, 0, 50, 50);
        cancelImageGraphics.drawLine(50, 0, 0, 50);

        BufferedImage okImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D okImageGraphics = (Graphics2D)okImage.getGraphics();
        okImageGraphics.setColor(Color.WHITE);
        okImageGraphics.fillRect(0, 0, 50, 50);
        okImageGraphics.setColor(new Color(255, 255, 0));
        okImageGraphics.setStroke(basicStroke);
        okImageGraphics.drawLine(0, 25, 25, 50);
        okImageGraphics.drawLine(25, 50, 50, 0);

        BufferedImage rolloverImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D rolloverImageGraphics = (Graphics2D)rolloverImage.getGraphics();
        rolloverImageGraphics.setColor(Color.WHITE);
        rolloverImageGraphics.fillRect(0, 0, 50, 50);
        rolloverImageGraphics.setColor(Color.BLUE);
        rolloverImageGraphics.setStroke(basicStroke);
        rolloverImageGraphics.drawOval(5, 5, 40, 40);

        BufferedImage pressedImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D pressedImageGraphics = (Graphics2D)pressedImage.getGraphics();
        pressedImageGraphics.setColor(Color.WHITE);
        pressedImageGraphics.fillRect(0, 0, 50, 50);
        pressedImageGraphics.setColor(Color.BLUE);
        pressedImageGraphics.fillOval(5, 5, 40, 40);

        for (int i = 0; i < SIZE * 2; i++) {
            JRadioButton radioButton = new JRadioButton();
            radioButton.setIcon(new ImageIcon(cancelImage));
            radioButton.setSelectedIcon(new ImageIcon(okImage));
            radioButton.setRolloverIcon(new ImageIcon(rolloverImage));
            radioButton.setPressedIcon(new ImageIcon(pressedImage));
            buttonGroup.add(radioButton);
            buttonsPanel.add(radioButton);
        }
    }

    private ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            return null;
        }
    }


    public static void main(String[] args){
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
