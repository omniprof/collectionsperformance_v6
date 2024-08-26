package com.kenfogel.performance;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import com.kenfogel.performance.loaders.MapTests;
import com.kenfogel.performance.loaders.SequenceTests;
import com.kenfogel.performance.models.MapSpeedTableModel;
import com.kenfogel.performance.models.SequenceSpeedTableModel;
import com.kenfogel.performance.panels.MapSpeedTablePanel;
import com.kenfogel.performance.panels.SequenceSpeedTablePanel;
import java.io.IOException;

/**
 * Updated to Java 21 standards
 * 
 * GUI for performance testing of various data structures
 *
 * Corrected error where I was using the copy constructor of the ArrayList.
 * Needed to be replaced with a for loop. Updated to use Java 1.8 syntax
 *
 * @author Ken Fogel
 * @version 6.0
 *
 */
public class CollectionPerformanceApp extends JFrame {

    private final SequenceSpeedTableModel sequenceSpeedTableModel;
    private final SequenceTests sequenceTests;
    private final MapSpeedTableModel mapSpeedTableModel;
    private final MapTests mapTests;

    private JButton button[];
    private final String[] buttonText = {"Array", "ArrayList", "Deque", "Linked List",
        "Hash Map", "Tree Map", "Clear"};
    private final String[] buttonAction = {"A", "B", "C", "D", "E", "F", "G"};

    /**
     * Constructor
     */
    public CollectionPerformanceApp() {
        super("Collection Performance Test V6.0");
        sequenceSpeedTableModel = new SequenceSpeedTableModel();
        sequenceTests = new SequenceTests(sequenceSpeedTableModel);
        mapSpeedTableModel = new MapSpeedTableModel();
        mapTests = new MapTests(mapSpeedTableModel);
    }

    /**
     * Call upon the methods that create the GUI
     */
    public void perform() {
        initialize();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Build the GUI
     */
    public void initialize() {
        // Add a toolbar
        add(createToolBar(), BorderLayout.NORTH);

        // Build the results panels
        SequenceSpeedTablePanel sequenceSpeedTablePanel = new SequenceSpeedTablePanel(sequenceSpeedTableModel);
        MapSpeedTablePanel mapSpeedTablePanel = new MapSpeedTablePanel(mapSpeedTableModel);

        // Build a panel to contain the two results panels
        JPanel testResultsPanel = new JPanel();
        testResultsPanel.setLayout(new GridLayout(2, 1));

        testResultsPanel.add(sequenceSpeedTablePanel);
        testResultsPanel.add(mapSpeedTablePanel);

        add(testResultsPanel, BorderLayout.CENTER);
    }

    /**
     * Create the tool bar
     *
     * @return the fully formed tool bar
     */
    private JToolBar createToolBar() {

        button = new JButton[buttonText.length];

        ToolBarEventHandler tbeh = new ToolBarEventHandler();

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        for (int x = 0; x < button.length - 1; ++x) {
            button[x] = new JButton(buttonText[x]);
            button[x].setActionCommand(buttonAction[x]);
            button[x].addActionListener(tbeh);
            toolBar.add(button[x]);
        }

        toolBar.addSeparator();
        button[button.length - 1] = new JButton(buttonText[button.length - 1]);
        button[button.length - 1]
                .setActionCommand(buttonAction[button.length - 1]);
        button[button.length - 1].addActionListener(tbeh);
        toolBar.add(button[button.length - 1]);

        setButtonsWidthAndFont();

        return toolBar;
    }

    /**
     * Set up the buttons in the toolbar
     */
    private void setButtonsWidthAndFont() {

        Font originalFont = button[0].getFont();
        Font boldFont = new Font(originalFont.getName(), Font.BOLD, 14);
        for (JButton button1 : button) {
            button1.setFont(boldFont);
        }

        Dimension newSize = button[0].getPreferredSize();
        Dimension currentSize;

        // Get the size of each button and preserve the widest
        for (JButton button1 : button) {
            currentSize = button1.getPreferredSize();
            if (currentSize.width > newSize.width) {
                newSize.width = currentSize.width;
            }
        }

        // Set the new preferred size for each button
        for (JButton button1 : button) {
            button1.setPreferredSize(newSize);
            button1.setMinimumSize(newSize);
            button1.setMaximumSize(newSize);
        }
    }

    /**
     * Rather than have a single event handler for all menu items here is an
     * inner class specific to the tool bar
     *
     * @author neon
     *
     */
    class ToolBarEventHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object actionSource = e.getSource();
            if (actionSource instanceof JButton) {
                JButton source = (JButton) (actionSource);

                char c = source.getActionCommand().charAt(0);
                switch (c) {
                    case 'A' ->
                        sequenceTests.doArrayTests();
                    case 'B' ->
                        sequenceTests.doArrayListTests();
                    case 'C' ->
                        sequenceTests.doDequeTests();
                    case 'D' ->
                        sequenceTests.doLinkedListTests();
                    case 'E' ->
                        mapTests.doHashMapTests();
                    case 'F' ->
                        mapTests.doTreeMapTests();
                    case 'G' -> {
                        sequenceSpeedTableModel.clearResults();
                        mapSpeedTableModel.clearResults();
                    }
                    default ->
                        JOptionPane.showMessageDialog(null,
                                "Unexpected button pressed.", "Bad Button Error",
                                JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    /**
     * Where it all begins
     *
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // creating and showing this application's GUI.
        SwingUtilities.invokeLater(() -> {
            CollectionPerformanceApp frame = new CollectionPerformanceApp();
            frame.perform();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
