/**
 * Provides attributes and methods that defines Process Display object
 * Display neccessary messages of the actions on the GUI
 * @author U. UDOETTE
 * @version 12/24
 */
package Presentation;
import Logic.*;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ProcessDisplay{
    private Staff staff;
    private JPanel contentPane = new JPanel();
    private static JTextArea textArea = new JTextArea("Click on Load Parcels button then Load Customer button to get started!!!");
    private JButton log = new JButton("View Log");
    private JButton generateReport = new JButton("Gen. Report");

    //Constructor
    public ProcessDisplay(Staff staff){
        this.staff = staff;
        createSection();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        contentPane.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    /**
     * creates the Process Display pane
     * @return Prcoess Display pane
     */
    public JPanel createSection() {
        // Panel to hold the content pane and the button grid
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createTitledBorder("Process Display")); // Add a titled border

        // Create the content pane
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));


        contentPane.add(textArea);
        contentPane.add(Box.createVerticalStrut(10)); // Add spacing between buttons
        contentPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Add the content pane to a scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPane);

        // Create a grid of buttons below the content pane
        JPanel buttonGrid = new JPanel(new GridLayout(1, 1, 10, 10)); // 3x3 grid with gaps

        JButton[] parcelButtons = { log, generateReport };

        for(JButton button: parcelButtons){
            buttonGrid.add(button);
        }

        // Add the scroll pane and button grid to the section panel
        sectionPanel.add(scrollPane, BorderLayout.CENTER);
        sectionPanel.add(buttonGrid, BorderLayout.SOUTH);
        return sectionPanel;
    }

    /**
     * display information of the Process Display section
     * @param msg - message to be displayed
     */
    public static void  showMessage(String msg) {
        textArea.setText(msg);
    }

    //set action listener for a generate report button
    public void setLogReportActionListener(ActionListener actionListener){
        generateReport.addActionListener(actionListener);
    }
    //set action listener for a generate view log button
    public void setViewLogActionListener(ActionListener actionListener){
        log.addActionListener(actionListener);
    }
}