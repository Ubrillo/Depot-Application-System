/**
 * Provides attributes and methods that defines Staff operations
 * provides all staff operational actions on the system
 * @author U. UDOETTE
 * @version 12/24
 */

package Logic;
import java.util.Iterator;

public class Staff{
    private Log logger = Log.getInstance();
    String fname, id;
    Depot depot;
    public Staff(String name, String id, Depot depot){
        fname = name;
        this.id = id;
        this.depot = depot;
    }

    /**
     * loads parcel data from file into depot
     * @return
     */
    public int loadParcelData(){
        return depot.readParcelData("Depot-System/src/Database/parcels.csv");
    }

    /**
     * load customer data from file into depot
     * @return
     */
    public int loadCustomerData(){
        return depot.readCustomerData("Depot-System/src/Database/customers.csv");
    }

    /**
     * checks out a customer in the queue given a specified first and last name
     * validates the input name of the customer
     * removes a cutomer from queue
     * @param fname- customer first name
     * @param lname -customer last name
     * @return the status of this method with appropriate signal as integer
     */
    public String processACustomer(String fname, String lname){
        CustomersQueue queue = depot.getQueue();
        if (depot.checkNameFormat(fname) && depot.checkNameFormat(lname)){
            Customer customer = queue.getCustomer(fname, lname);
            Iterator<String> iterator = customer.getParcelList().iterator();
            while (iterator.hasNext()) {
                String item = iterator.next();
                releaseParcel(item);
                iterator.remove();
            }
            if (customer.getParcelList().isEmpty()) {
                return queue.removeCustomer(fname, lname);
            }
        }
        return null;
    }

    /**
     * check out a parcel in the queue
     * removes a parcel owner from the queue
     * @param parcelId -
     * @return the status of this method with appropriate signal as integer
     */
    public int processAParcel(String parcelId){
        CustomersQueue queue = depot.getQueue();
        if (depot.checkParcelFormat(parcelId)){
            Customer customer = queue.getCustomer(parcelId);
            customer.removeParcel(parcelId);
            if (customer.getParcelList().isEmpty()){
                queue.removeCustomer(customer.getFname(), customer.getLname());
            }
            releaseParcel(parcelId);
        }
        return 0;
    }

    /**
     * update the status of a parcel
     * add parcels to collected list
     * removes parcels from waiting list
     * @param parcelId
     * @return the status of this method with appropriate signal as integer
     */
    public int releaseParcel(String parcelId){
        ParcelMap parcelList = depot.getParcelList();
        Parcel parcel = parcelList.getParcel(parcelId);
        parcel.setState(ParcelState.COLLECTED);
        depot.addParcelToCollectedList(parcelId);
        parcelList.removeParcelFromWaitingList(parcelId);
        return 0;
    }

    /**
     * adds a new customer to queue
     * @param fname - customer first name
     * @param lname - customer last name
     * @param parcelId -parcel id of the customer
     * @return the status of this method with appropriate signal as integer
     */
    public int addCustomerToQueue(String fname, String lname, String parcelId){
        String row = fname+" "+lname+","+parcelId;
        return depot.processCustomerRow(row);
    }

    /**
     * adds parcel to waiting list
     * @param parcelId - parcel id
     * @param dip - days in depot of the parcel
     * @param wght - weight of the parcel
     * @param length - length of the parcel
     * @param width - width of the parcel
     * @param height - height of the parcel
     * @return the status of this method with appropriate signal as integer
     */
    public int addParcelToWaitingList(String parcelId, String dip, String wght, String length, String width, String height){
        String row = parcelId+","+dip+","+wght+","+length+","+width+","+height;
        return depot.processParcelRow(row);
    }

    /**
     * @param parcelId - parcel id
     * @return details of a speficied parcel
     */
    public String viewParcel(String parcelId){
        Parcel parcel = depot.getParcelList().getParcel(parcelId);
        if (parcel == null){
            parcel = depot.findACollectedParcel(parcelId);
        }
        if (parcel != null){return parcel.toString();}
        return null;
    }

    /**
     * @return all parcels of customers in queue as string
     */
    public String viewAllQueueParcels(){
        return depot.getQueue().getAllWaitingParcels();
    }

    /**
     * @return all waiting parcels as string
     */
    public String viewAllWaitingParcels(){return depot.getParcelList().getAllWaitingParcels();}

    /**
     * @return all collected parcels as string
     */
    public String viewAllCollectedParcels(){
        return depot.getAllCollectedParcels();
    }

    /**
     *
     * @param fname
     * @param lname
     * @return details of a customer as string given first and last name of a customer
     */
    public String viewACustomer(String fname, String lname){
       String details = depot.getQueue().getACustomerDetails(fname, lname);
       return details;
    }

    /**
     * @param parcelId
     * @return details of a customer given a parcel id
     */
    public String viewACustomer(String parcelId){
        return depot.getQueue().getACustomerDetails(parcelId);
    }

    /**
     * @return all cutomers in queue as string
     */
    public String viewAllCustomersInQueue(){
        return depot.getQueue().getAllCustomers();
    }

    /**
     * removes a customer from queue
     * @param fname- first name of customer
     * @param lname - last name of customer
     * @return a signal to signify the status of this method
     */
    public String removeACustomerFromQueue(String fname, String lname){
        return depot.getQueue().removeCustomer(fname, lname);
        //String msg = fname+" "+ lname+" removed from queue"+'\n';
        //return msg;
    }

    /**
     * returns the sequence number of a customer in  the queue given the names of the customer
     * @param fname
     * @param lname
     * @return the sequence number of a customer in  the queue
     */
    public int getSequenceNumber(String fname, String lname){
        return depot.getQueue().getSequenceNumber(fname, lname)+1;
    }

    /**
     * @param parcelId
     * @returns sequence of number of customer in the queue given the the parcel id
     */
    public int getSequenceNumber(String parcelId){
        Customer customer = depot.getQueue().getCustomer(parcelId);
        if (customer == null){
            return -1;
        }
        return getSequenceNumber(customer.getFname(), customer.getLname());
    }

    /**
     * @return temporary logged events
     */
    public String viewLog(){
        return logger.getLog();
    }

    /**
     * write log events  to a file
     */
    public void generateReport(){
        logger.writeLogToFile();
    }

    /**
     * @param parcelId
     * @return the calcuated fee of a parcel as double
     */
    public double calculateFee(String parcelId){
        double weightCharge;
        Parcel parcel = depot.getParcelList().getParcel(parcelId);
        if (parcel == null){return -1;}
        if (parcelId.charAt(0) == 'C') {
            weightCharge = parcel.getWeight()*0.25;
        }
        else{
            weightCharge = parcel.getWeight()*0.4;
        }
        int dipCharge  = 2 * parcel.getDaysInDepot();
        return dipCharge + weightCharge;
    }

    /**
     * @param fname -customer first name
     * @param lname - customer last name
     * @return the total fees of if a customer owns one or more than one parcel
     */
    public double calculateAllFees(String fname, String lname){
        Customer customer = depot.getQueue().getCustomer(fname, lname);
        if (customer == null){return -1;}
        double total = 0;
        for (String pid: customer.getParcelList()){
            total += calculateFee(pid);
        }
        return total;
    }
}