import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFrame extends JFrame {
    private JPanel listsPanel;
    private JList leftList, rightList;
    private JButton moveToRight, moveToLeft;
    private JPanel tablePanel;
    private JPanel buttonsPanel;
    private DefaultListModel<String> modelLeft, modelRight;
    public static final int SIZE = 3;
    public static final int NUMBER_OF_IMAGES = 4;
    public static final String[] IMAGES_PATHS = {"Cancel.png", "Ok.jpeg", "Select.jpeg", "Pressed.jpeg"};

    public MainFrame() {
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

    private void createTablePanel() {
        tablePanel = new JPanel(new GridLayout(SIZE, SIZE));
        MouseListener ml = new MouseAdapter() {
            private String capturing;
            private final Color BACKGROUND_COLOR = UIManager.getColor(new JButton());

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    JButton button = (JButton) e.getSource();
                    capturing = button.getText();
                    button.setText("Pressed");
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                JButton button = (JButton) e.getSource();
                button.setText(capturing);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                JButton button = (JButton) e.getSource();
                button.setBackground(new Color(255, 255, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                JButton button = (JButton) e.getSource();
                button.setBackground(BACKGROUND_COLOR);
            }
        };
        for (int i = 0; i < SIZE * SIZE; i++) {
            JButton button = new JButton("Button " + i);
            button.addMouseListener(ml);
            tablePanel.add(button);
        }
    }

    private void createButtonsPanel() {
        buttonsPanel = new JPanel(new GridLayout(SIZE, 2));
        ButtonGroup buttonGroup = new ButtonGroup();

        ImageIcon[] imageIcons = new ImageIcon[4];
        try {
            for (int i = 0; i < NUMBER_OF_IMAGES; i++) {
                BufferedImage buff = ImageIO.read(new File(IMAGES_PATHS[i]));
                imageIcons[i] = new ImageIcon(buff);
            }
            for (int i = 0; i < SIZE * 2; i++) {
                JRadioButton radioButton = new JRadioButton();
                radioButton.setIcon(imageIcons[0]);
                radioButton.setSelectedIcon(imageIcons[1]);
                radioButton.setRolloverIcon(imageIcons[2]);
                radioButton.setPressedIcon(imageIcons[3]);
                buttonGroup.add(radioButton);
                buttonsPanel.add(radioButton);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Images loading error");
        }
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
