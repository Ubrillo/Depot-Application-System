/**
 * Provides attributes and methods that defines the StaffGUI
 * Display the Staff GUI application interface
 * @author U. UDOETTE
 * @version 12/24
 */

package Presentation;
import Logic.Staff;
import java.awt.event.ActionListener;
import java.awt.*;
import javax.swing.*;

public class StaffGUI{
    private JTextArea Screen = new JTextArea();
    private JLabel label = new JLabel();

    JFrame frame = new JFrame("Staff Portal");
    Staff operator;

    private  ParcelDisplay leftSection;
    private  CustomerDisplay centerSection;
    private  ProcessDisplay rightSection;

    //Constructor
    public StaffGUI(Staff staff){
        this.operator = staff;
        leftSection = new ParcelDisplay(staff);
        centerSection = new CustomerDisplay(staff);
        rightSection = new  ProcessDisplay(staff);
        makeFrame();
        makeMenuBar();
    }

    //creakes the GUI display
    public void makeFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Add the parcel sections to the main panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 3));

        JPanel parcelScreen = leftSection.createSection();
        JPanel customerScreen = centerSection.createSection();
        JPanel processDisplay = rightSection.createSection();

        mainPanel.add(parcelScreen);
        mainPanel.add(customerScreen);
        mainPanel.add(processDisplay);

        // Add the main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Make the frame visible
        frame.setVisible(true);
    }

    //Adds  menu bar to the frame
    private void makeMenuBar()
    {
        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);

        // create the File menu
        // Champions menu for operations related to champions
        JMenu fileMenu = new JMenu("File");
        menubar.add(fileMenu);

        JMenu editMenu = new JMenu("Edit");
        menubar.add(editMenu);

        // Challenges menu for operations related to challenges
        JMenu optionMenu = new JMenu("Options");
        menubar.add(optionMenu);

        // Add menu items under champions menu and their handlers

    }

    //set action listeners to the load parcel button
    public void setLoadParcelActionListener(ActionListener actionListener){
        leftSection.setLoadParcelActionListener(actionListener);
    }
    //set action listeners to the add parcel button
    public void setAddParcelActionListener(ActionListener actionListener){
        leftSection.setAddParcelActionListener(actionListener);
    }

    //set action listeners to the waiting parcels button
    public void setWaitingParcelsActionListener(ActionListener actionListener){
        leftSection.setWaitingParcelsActionListener(actionListener);
    }
    //set action listeners to the collected parcel button
    public void setCollectedParcelsActionListener(ActionListener actionListener){
        leftSection.setCollectedParcelsActionListener(actionListener);
    }

    //set action listeners to the find parcel button
    public void setFindParcelActionListener(ActionListener actionListener){
        leftSection.setFindParcelActionListener(actionListener);
    }

    //set action listeners to the check Fee button
    public void setParcelFeeActionListener(ActionListener actionListener){
        leftSection.setParcelFeeActionListener(actionListener);
    }

    //set action listeners to the load customer  button
    public void setLoadCustomerActionListener(ActionListener actionListener){
        centerSection.setLoadCustomerActionListener(actionListener);
    }

    //set action listeners to the remove customer  button
    public void setRemoveCustomerActionListener(ActionListener actionListener) {
        centerSection.setRemoveCustomerActionListener(actionListener);
    }

    //set action listeners to the add customer  button
    public void setAddCustomerActionListener(ActionListener actionListener){
        centerSection.setAddCustomerActionListener(actionListener);
    }

    //set action listeners to the find customer  button
    public void setFindCustomerActionListener(ActionListener actionListener){
        centerSection.setFindCustomerActionListener(actionListener);
    }

    ////set action listeners to the process customer  button
    public void setProcessCustomerActionListener(ActionListener actionListener){
        centerSection.setProcessCustomerActionListener(actionListener);
    }

    //set action listeners to the generate report  button
    public void setLogReportActionListener(ActionListener actionListener){
        rightSection.setLogReportActionListener(actionListener);
    }

    //set action listeners to the View Log   button
    public void setViewLogActionListener(ActionListener actionListener){
        rightSection.setViewLogActionListener(actionListener);
    }
}
