/**
 * Provides attributes and methods that defines the customer queue
 * creates and manages customer queue
 * @author UDOETTE
 * @version 12/24
 */

package Logic;
import java.util.*;
public class CustomersQueue {
    private TreeMap<String, Customer>customerList; //stores list of all customers in queue
    private Log logger = Log.getInstance();
    private Notifier activator = Notifier.getInstance();

    //Constructor
    public CustomersQueue(){
      customerList = new TreeMap<>();
    }
    /**
     *adds a new customer to queue
     * @param newCustomer- new customer object to be added to queue
     */
    public void addNewCustomer(Customer newCustomer){
        String name = newCustomer.getFullName();
        customerList.put(name, newCustomer);
        String msg = name+" added to queue";
        activator.notifyObservers();
        logger.writeEvent(msg);
    }

    /**
     * removes a customer from queue
     * @param fname- customer's first name
     * @param lname - customer's last name
     * @returns null if customer object is not in queue otherwise returns an appropriate message
     * notify observers
     * log events
     */
    public String  removeCustomer(String fname, String lname){
        // using treemap
        String name = lname+" "+fname;
        if (customerList.remove(name) == null){
            return null;
        }
        String msg = name+" removed from queue.";
        activator.notifyObservers();
        logger.writeEvent(msg);
        return msg;
    }

    /**
     * returns a list of all customers in queue as string
     * @return customers in queue
     */
    public String getAllCustomers(){
        //return ""+customersx;
        String str = "";
        for (String name: customerList.keySet()){
            str += name+'\n';
        }
        return str;
    }

    /**
     * returns all the details of the customer in the queue as string
     * @return details of all customer in the queue
     */
    public String getAllCustomersDetails(){
        String str = "*************** Details of Customers in Queue **************"+'\n';
        for (String name: customerList.keySet()){
            str += name+'\n';
             str += customerList.get(name).toString()+'\n';
             str += "*************************\n";
        }
        return str;
    }

    /**
     * returns the a customer object in the queue given customer's first and last names
     * @param fname - customer's first name
     * @param lname - customer's last name
     * @return customer object
     */
    public Customer getCustomer(String fname, String lname){
        String fullName = lname+" "+fname;
        return customerList.get(fullName);
    }
    /**
     *returns a customer object in the queue with a given parcel id
     * @param parcelId-parcel of a customer
     * @return customer who owns a parcel otherwise return null if customer not found in queue
     */
    public Customer getCustomer(String parcelId){
        for (Customer customer: customerList.values()){
            for (String pid: customer.getParcelList()){
                if (parcelId.equals(pid)){
                    return customer;
                }
            }
        }
        return null;
    }
    /**
     * returns True if the queue is empty, False otherwise
     * @return a boolean
     */
    public boolean isEmpty(){
        return customerList.isEmpty();
    }
    /**
     * returns the details of a customer in the queue as string given customer's first and last name
     * @param fname-customer's first name
     * @param lname-customer's last name
     * @return - details of a customer in the queue
     */
    public String getACustomerDetails(String fname, String lname){
        String name = lname+" "+fname;
        return  customerList.get(name).toString();
    }
    /**
     * returns the details of a customer in the queue as  a string  given a parcel id
     * @param parcelId- parcel id of a customer
     * @return details of a customer
     */
    public String getACustomerDetails(String parcelId){
        Customer customer = getCustomer(parcelId);
        return customer.toString();
    }
    /**
     * returns all the parcels Ids of the customers in the queue as string
     * @return all customer parcels in the queue
     */
    public String getAllWaitingParcels(){
        String str = "";
        for (Customer customer: customerList.values()){
            str += customer.getParcels();
        }
        return str;
    }
    /**
     * returns the sequence number of a customer in the queue as integer
     * @param fname-cusomter first name
     * @param lname - customer last name
     * @return the postion of a customer in the queue
     */
    public int getSequenceNumber(String fname, String lname){
       String name = lname+" "+fname;
        return customerList.headMap(name).size();
    }

    /**returns the a brief details of the details as a string
     * @return the details of  the queue
     */
    @Override
    public String toString() {
        return "CustomersQueue{" +
                ///"customers=" + customers +
                ", customersx=" + customerList +
                '}';
    }
}