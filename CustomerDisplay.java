/**
 * Provides attributes and methods that defines Customer Display
 * Display customers in the queue
 * @author U. UDOETTE
 * @version 12/24
 */

package Presentation;
import Logic.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerDisplay implements Observer{
    private Notifier notifier = Notifier.getInstance();
    private  JPanel sectionPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane();
    private Staff staff;
    JButton loadCustomer = new JButton("Load Customer"); // Load Custome Button
    JButton processCustomer = new JButton("Process Customer"); //Process Customer Button
    JButton addCustomer =  new JButton("Add Customer"); //Add Customer Button
    JButton removeCustomer = new JButton("Remove Customer"); //Remove Customer Button
    JButton findCustomer = new JButton("Find Customer"); //Find Customer Button

    //Constructor
    public CustomerDisplay(Staff staff){
        this.staff = staff;
        notifier.registerObserver(this);
        createSection();
    }

    /**
     * trigger the update method when change is made
     */
    public void update(){
        updateContent();
    }

    /**
     * Creates a GUI section for Customer Display
     * Creates Customer operational buttons
     * @return panel object
     */
    public JPanel createSection() {
        //JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createTitledBorder("Queue")); // Add a titled border

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Add the content pane to a scroll pane
        scrollPane = new JScrollPane(contentPane);

        // Create a grid of buttons below the content pane
        JPanel buttonGrid = new JPanel(new GridLayout(3, 3, 10, 10)); // 3x3 grid with gaps

        JButton[] parcelButtons = {
                loadCustomer,
                processCustomer,
                findCustomer,
                addCustomer,
                removeCustomer
        };

        for(JButton button: parcelButtons){
            button.setSize(150, 40);
            buttonGrid.add(button);
        }

        // Add the scroll pane and button grid to the section panel
        sectionPanel.add(scrollPane, BorderLayout.CENTER);
        sectionPanel.add(buttonGrid, BorderLayout.SOUTH);
        return sectionPanel;
    }

    /**
     * Updates GUI contents when a change is made
     * @return the JScroll object after an update is made
     */
    private JScrollPane updateContent(){
        String data = "";
        data += staff.viewAllCustomersInQueue();

        String[] items = data.split("\n");
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        int i = 1;
        for (String item : items) {
            JButton button = new JButton(i+". "+item);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(Color.LIGHT_GRAY); // Light-gray background
            button.setOpaque(true);
            button.setBorderPainted(false);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] name = item.split(" ");
                    String fname = name[1].trim();
                    String lname = name[0].trim();
                    String data = staff.viewACustomer(fname, lname);
                    int seq = staff.getSequenceNumber(fname, lname);
                    double fees = staff.calculateAllFees(fname, lname);
                    data += "\nSeq. No: "+seq+
                            "\nFees: £"+fees+
                            "\n\nDo you want to process customer?";
                    ProcessDisplay.showMessage(data);

                    String[] options = {"Process Customer", "Cancel"};
                    int choice = JOptionPane.showOptionDialog(
                            button,
                            data,
                            "Customer Processing",        // Title of the popup
                            JOptionPane.DEFAULT_OPTION,         // Option type
                            JOptionPane.INFORMATION_MESSAGE,    // Message type
                            null,                               // Icon (null for default)
                            options,                            // Options to display
                            null                          // Default selected option
                    );
                    if (choice == 0){
                        staff.processACustomer(fname, lname);
                        String msg = "Customer processed successfully";
                        ProcessDisplay.showMessage(msg);
                        JOptionPane.showMessageDialog(null, msg);
                    }
                }
            });
            contentPane.add(button);
            contentPane.add(Box.createVerticalStrut(10)); // Add spacing between buttons
            contentPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 5, 5));
            i++;
        }

        //displays graphics with time delay
        Timer timer = new Timer(500, e -> {
            // Remove the existing JScrollPane
            sectionPanel.remove(scrollPane);
            scrollPane = new JScrollPane(contentPane);
            // Add the content pane to a scroll pane
            sectionPanel.add(scrollPane, BorderLayout.CENTER);
            // Revalidate and repaint the panel to update the UI
            sectionPanel.revalidate();
            sectionPanel.repaint();
        });
        timer.setRepeats(false); // Make it execute only once
        timer.start();

        // Replace the existing JScrollPane with a new one after a delay (simulating a dynamic update)
        return scrollPane;
    }

    //set action listener for the Load Customer button
    public void setLoadCustomerActionListener(ActionListener actionListener){
        loadCustomer.addActionListener(actionListener);
    }

    //set action listener for the remove customer button
    public void setRemoveCustomerActionListener(ActionListener actionListener) {
        removeCustomer.addActionListener(actionListener);
    }

    //set action listener for the add Customer button
    public void setAddCustomerActionListener(ActionListener actionListener){
        addCustomer.addActionListener(actionListener);
    }

    //set Find Customer Action Listener
    public void setFindCustomerActionListener(ActionListener actionListener){
        findCustomer.addActionListener(actionListener);
    }

    //set Process Customer Action Listener
    public void setProcessCustomerActionListener(ActionListener actionListener){
        processCustomer.addActionListener(actionListener);
    }

}