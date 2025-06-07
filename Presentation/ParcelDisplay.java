/**
 * Provides attributes and methods that defines Parcel Display object
 * Display Parcel of customers in the queue
 * @author U. UDOETTE
 * @version 12/24
 */

package Presentation;
import Logic.Notifier;
import Logic.Staff;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class ParcelDisplay implements Observer{
    private JButton loadParcels = new JButton("Load Parcels"); //Load Parcel Button
    private JButton addParcel = new JButton("Add Parcel"); // Add Parcel Button
    private JButton waitingParcels = new JButton("waiting Parcels"); // Waiting Parcel Button
    private JButton collectedParcels = new JButton("collected Parcels"); //Collected Parcel Button
    private JButton findParcel = new JButton("Find Parcel"); //find pacel button
    private JButton parcelFee = new JButton("Check Price"); //check Price button
    private Staff staff;
    private Notifier notifier = Notifier.getInstance();
    private  JPanel sectionPanel = new JPanel();
    private JScrollPane scrollPane = new JScrollPane();

    //Constructor
    public ParcelDisplay(Staff staff){
        //super();
        this.staff = staff;
        notifier.registerObserver(this);
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createTitledBorder("Parcels")); // Add a titled border
    }

    /**
     * updates the Parcel Display when a change is made
     */
    public void update (){
        updateContent();
    }

    /**
     * create the Parcel Display Pane
     * create Parcel operational buttons
     * @return Parcel Display pane as JPanel
     */
    public JPanel createSection() {
        //JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BorderLayout());
        sectionPanel.setBorder(BorderFactory.createTitledBorder("Parcels")); // Add a titled border

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        // Add the content pane to a scroll pane
        scrollPane = new JScrollPane(contentPane);

        // Create a grid of buttons below the content pane
        JPanel buttonGrid = new JPanel(new GridLayout(3, 3, 10, 10)); // 3x3 grid with gaps
        JButton[] parcelButtons = {
                loadParcels,  addParcel,
                waitingParcels, collectedParcels,
                findParcel, parcelFee
        };

        for(JButton button: parcelButtons){
            buttonGrid.add(button);
        }

        // Add the scroll pane and button grid to the section panel
        sectionPanel.add(scrollPane, BorderLayout.CENTER);
        sectionPanel.add(buttonGrid, BorderLayout.SOUTH);

        return sectionPanel;
    }

    /**
     * update the Parcel Display when a change is made
     * @return
     */
    private JScrollPane updateContent(){
        String data = "";
        data += staff.viewAllQueueParcels();
        String[] items = data.split("\n");
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        for (String item : items) {
            JButton button = new JButton(item);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(Color.LIGHT_GRAY); // Light-gray background
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String data = staff.viewParcel(item);
                    double fees = staff.calculateFee(item);
                    data = data+"\nFees: £"+fees+"\n\nDo you want to release parcel?";
                    ProcessDisplay.showMessage(data);

                    String[] options = {"Release parcel", "Cancel"};
                    int choice = JOptionPane.showOptionDialog(
                        button,
                        data,
                        "Parcel Processing",        // Title of the popup
                        JOptionPane.DEFAULT_OPTION,         // Option type
                        JOptionPane.INFORMATION_MESSAGE,    // Message type
                        null,                               // Icon (null for default)
                        options,                            // Options to display
                        null                          // Default selected option
                    );
                    if (choice == 0){
                        int result = staff.processAParcel(item);
                        String msg = "Parcel released successfully";
                        ProcessDisplay.showMessage(msg);
                        JOptionPane.showMessageDialog(null, msg);
                    }
                }
            });

            contentPane.add(button);
            contentPane.add(Box.createVerticalStrut(10)); // Add spacing between buttons
            contentPane.setBorder(BorderFactory.createEmptyBorder(7, 7, 5, 5));

        }
        //enable transistions in graphic display
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

    //set action listener for load parcel button
    public void setLoadParcelActionListener(ActionListener actionListener){
        loadParcels.addActionListener(actionListener);
    }

    //set action listener for add parcel button
    public void setAddParcelActionListener(ActionListener actionListener){
    addParcel.addActionListener(actionListener);
    }

    //set action listener for waiting parcel button
    public void setWaitingParcelsActionListener(ActionListener actionListener){
        waitingParcels.addActionListener(actionListener);
    }

    //set action listener for collected parcel button
    public void setCollectedParcelsActionListener(ActionListener actionListener){
        collectedParcels.addActionListener(actionListener);
    }

    //set action listener for find parcel button
    public void setFindParcelActionListener(ActionListener actionListener){
        findParcel.addActionListener(actionListener);
    }

    //set action listener for set parcel button
    public void setParcelFeeActionListener(ActionListener actionListener){
        parcelFee.addActionListener(actionListener);
    }
}
