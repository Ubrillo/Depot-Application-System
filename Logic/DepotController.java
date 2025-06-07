/**
 * Provides attributes and methods that defines Depot or Manager class
 * handles events between StaffGUI and the Model classes
 * @author U. UDOETTE
 * @version 12/24
 */
package Logic;
import Presentation.StaffGUI;
import Presentation.ProcessDisplay;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class DepotController {
    private StaffGUI portal; // GUI
    private Staff staff;

    //Constructor
    public DepotController(Staff operator, StaffGUI portal){
        this.portal = portal;
        this.staff = operator;

        //Parcels Action Listeners
        portal.setLoadParcelActionListener(new loadParcelActionListener());
        portal.setFindParcelActionListener(new findParcelActionListener());
        portal.setCollectedParcelsActionListener(new collectedParcelsActionListener());
        portal.setAddParcelActionListener(new setAddParcelActionListener());
        portal.setWaitingParcelsActionListener(new waitingParcelsActionListener());
        portal.setParcelFeeActionListener(new calculateFeeActionListener());

        //Customers Action Listeners
        portal.setLoadCustomerActionListener(new loadCustomerActionListener());
        portal.setProcessCustomerActionListener(new processCustomerActionListener());
        portal.setFindCustomerActionListener(new findCustomerActionListener());
        portal.setAddCustomerActionListener(new addCustomerActionListener());
        portal.setRemoveCustomerActionListener(new removeCustomerActionListener());

        //Log Actions Listeners
        portal.setLogReportActionListener(new reportLogActionListener());
        portal.setViewLogActionListener(new viewLogActionListener());
    }

    //listener for Load Parcel Button
    public class loadParcelActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = staff.loadParcelData()+" parcel row read";
            ProcessDisplay.showMessage(msg);
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    //Listener for Find Parcel Button
    public class findParcelActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String userInput = JOptionPane.showInputDialog(null, "Enter Parcel ID:");
            String msg = staff.viewParcel(userInput);
            if (userInput != null){
                msg = staff.viewParcel(userInput);
                if (msg == null){
                    msg = "Parcel not found!!!";
                }
                ProcessDisplay.showMessage(msg);
                JOptionPane.showMessageDialog(null, msg);
            }
        }
    }

    //Action Listener for Waiting Parcels Button
    public class waitingParcelsActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String msg = staff.viewAllWaitingParcels();
            ProcessDisplay.showMessage(msg);
        }
    }
    //Action Listener for Collected Parcel Button
    public class collectedParcelsActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String msg = staff.viewAllCollectedParcels();
            ProcessDisplay.showMessage(msg);
        }
    }

    //Action Listener for Calculate Fee Button
    public class calculateFeeActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String userInput = JOptionPane.showInputDialog(null, "Enter Parcel ID:");
            double fee = staff.calculateFee(userInput);
            String msg = "Fees: £"+fee;
            if (fee < 0) {
                msg = "Parcel not found!!!";
            }
            ProcessDisplay.showMessage(msg);
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    //Action Listener for Add Parcel Button
    public class setAddParcelActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            // Create a panel with multiple text fields and labels
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(6, 2));  // 3 rows, 2 columns

            JLabel pid = new JLabel("Parcel ID:");
            JTextField pidInput = new JTextField();
            JLabel dip = new JLabel("Days In Depot:");
            JTextField dipInput = new JTextField();
            JLabel weight = new JLabel("Weight:");
            JTextField weightInput = new JTextField();
            JLabel length = new JLabel("Length:");
            JTextField lengthInput = new JTextField();
            JLabel width = new JLabel("Width:");
            JTextField widthInput = new JTextField();
            JLabel height = new JLabel("Height:");
            JTextField heightInput = new JTextField();

            panel.add(pid); panel.add(pidInput); panel.add(dip);
            panel.add(dipInput); panel.add(weight); panel.add(weightInput);
            panel.add(length); panel.add(lengthInput); panel.add(width);
            panel.add(widthInput); panel.add(height); panel.add(heightInput);

            // Create an option pane with the panel
            int option = JOptionPane.showConfirmDialog(null, panel, "Create Parcel Label",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (option == JOptionPane.OK_OPTION) {
                // Retrieve the entered values
                String pidText = pidInput.getText();
                String dipText = dipInput.getText();
                String weightText = weightInput.getText();
                String lengthText = lengthInput.getText();
                String widthText = widthInput.getText();
                String heightText = weightInput.getText();
                
                int result = staff.addParcelToWaitingList(pidText, dipText, weightText, lengthText, widthText, heightText);
                String msg = "error";
                switch(result){
                    case 0: msg = "Parcel added successfuly";break;
                    case 1: msg = "Invalid ID";break;
                }
                ProcessDisplay.showMessage(msg);
                JOptionPane.showMessageDialog(null,msg);
            }
        }
    }

    //Action Listener for Load Customer Button
    public class loadCustomerActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = staff.loadCustomerData() + " customers rows read";
            ProcessDisplay.showMessage(msg);
            JOptionPane.showMessageDialog(null, msg);
        }
    }

    //Action Listener for Find Customer Button
    public class findCustomerActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] options = {"By name", "By parcel ID", "Cancel"};
            int choice = JOptionPane.showOptionDialog(
                    null,                                // Parent component (null for center screen)
                    "Select an option",                // Message to display
                    "Find Customer",                 // Title of the popup
                    JOptionPane.DEFAULT_OPTION,         // Option type
                    JOptionPane.INFORMATION_MESSAGE,    // Message type
                    null,                               // Icon (null for default)
                    options,                            // Options to display
                    options[0]                          // Default selected option
            );

            if (choice == 1){
                String userInput = JOptionPane.showInputDialog(null, "Enter Parcel ID:");
                String msg = staff.viewACustomer(userInput);
                if (msg == null){
                    msg = "Parcel not found!!";
                }else{
                    int seq = staff.getSequenceNumber(userInput);
                    msg +="\nSeq. No: "+seq;
                }

                ProcessDisplay.showMessage(msg);
                JOptionPane.showMessageDialog(null, msg);
            }
            else if (choice == 0){
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(2, 2));  // 3 rows, 2 columns

                JLabel fnameLabel = new JLabel("First Name:");
                JTextField fnameText = new JTextField();
                JLabel lnameLabel = new JLabel("Last Name:");
                JTextField lnameText = new JTextField();

                panel.add(fnameLabel); panel.add(fnameText);
                panel.add(lnameLabel); panel.add(lnameText);

                int option = JOptionPane.showConfirmDialog(null, panel, "Input customer data",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    // Retrieve the entered values
                    String fnameInput = fnameText.getText().trim();
                    String lnameInput = lnameText.getText().trim();
                    String msg = staff.viewACustomer(fnameInput, lnameInput);
                    if (msg == null){
                        msg = "Parcel not found!!";
                    }
                    else{
                        int seq = staff.getSequenceNumber(fnameInput, lnameInput);
                        msg +="\nSeq. No: "+seq;
                    }
                    ProcessDisplay.showMessage(msg);
                    JOptionPane.showMessageDialog(null, msg);
                }
            }
        }
    }
    //Action Listener for Add Customer Button
    public class addCustomerActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));  // 3 rows, 2 columns

            JLabel fnameLabel = new JLabel("First Name:");
            JTextField fnameText = new JTextField();
            JLabel lnameLabel = new JLabel("Last Name:");
            JTextField lnameText = new JTextField();
            JLabel pidLabel = new JLabel("Parcel ID:");
            JTextField pidText = new JTextField();

            panel.add(fnameLabel); panel.add(fnameText);
            panel.add(lnameLabel); panel.add(lnameText);
            panel.add(pidLabel); panel.add(pidText);

            int option = JOptionPane.showConfirmDialog(null, panel, "Input customer data",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                // Retrieve the entered values
                String fnameInput = fnameText.getText().trim();
                String lnameInput = lnameText.getText().trim();
                String pidInput = pidText.getText();

                int result = staff.addCustomerToQueue(fnameInput, lnameInput, pidInput);
                String msg = "";
                switch(result){
                    case 0: msg = "Customer added successfuly";break;
                    case 1: msg = "Invalid name format";break;
                    case 2: msg = "Invalid parcel ID format. Parcel must begin with 'X' or 'C' followed by any 3 digits";break;
                    case 3: msg = "Parcel unavailable!!. Add parcel to list before assigning a customer"; break;
                    case 4: msg = "Parcel already exist, or is owned by another customer"; break;
                    default: msg = "Something else is wrong!!"; break;
                }
                ProcessDisplay.showMessage(msg);
                JOptionPane.showMessageDialog(null, msg);
            }
        }
    }

    //Action Listener for Remove Customer Button
    public class removeCustomerActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));  // 3 rows, 2 columns

            JLabel fnameLabel = new JLabel("First Name:");
            JTextField fnameText = new JTextField();
            JLabel lnameLabel = new JLabel("Last Name:");
            JTextField lnameText = new JTextField();

            panel.add(fnameLabel); panel.add(fnameText);
            panel.add(lnameLabel); panel.add(lnameText);

            int option = JOptionPane.showConfirmDialog(null, panel, "Input customer data",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                // Retrieve the entered values
                String fnameInput = fnameText.getText().trim();
                String lnameInput = lnameText.getText().trim();
                String msg = staff.removeACustomerFromQueue(fnameInput, lnameInput);

                if (msg == null){ msg = "Name does not exist in Queue";}
                ProcessDisplay.showMessage(msg);
                JOptionPane.showMessageDialog(null, msg);
            }
        }
    }

    //Action Listener for Process Customer Button
    public class processCustomerActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));  // 3 rows, 2 columns
            JLabel fnameLabel = new JLabel("First Name:");
            JTextField fnameText = new JTextField();
            JLabel lnameLabel = new JLabel("Last Name:");
            JTextField lnameText = new JTextField();

            panel.add(fnameLabel); panel.add(fnameText);
            panel.add(lnameLabel); panel.add(lnameText);

            int option = JOptionPane.showConfirmDialog(null, panel, "Input customer data",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                // Retrieve the entered values
                String fnameInput = fnameText.getText().trim();
                String lnameInput = lnameText.getText().trim();
                double fee = staff.calculateAllFees(fnameInput, lnameInput);
                String msg = "Customer not found!!!";
                if (fee < 0) {
                    ProcessDisplay.showMessage(msg);
                    JOptionPane.showMessageDialog(null, msg);
                } else {
                    String[] options = {"Proceed?", "Cancel"};
                    msg = "\nConfirm Payment for this Parcel(s)\nFees: £"+fee;
                    int choice = JOptionPane.showOptionDialog(
                            null,
                            msg,
                            "Confirm payment for ",        // Title of the popup
                            JOptionPane.DEFAULT_OPTION,         // Option type
                            JOptionPane.INFORMATION_MESSAGE,    // Message type
                            null,                               // Icon (null for default)
                            options,                            // Options to display
                            options[1]                          // Default selected option
                    );
                    // Handle the user's selection
                    if (choice == 0) {
                        msg = staff.processACustomer(fnameInput, lnameInput);
                        msg = "Customer processed...";
                        ProcessDisplay.showMessage(msg);
                        JOptionPane.showMessageDialog(null, msg);
                    }
                }
            }
        }
    }

    //Action Listener for Generate Log Button
    public class reportLogActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            staff.generateReport();
            JOptionPane.showMessageDialog(null, "Log has been written to file");
        }
    }
    //Action Listener for View Log Action Listener
    public class viewLogActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String msg = staff.viewLog();
            ProcessDisplay.showMessage(msg);
        }
    }

}
