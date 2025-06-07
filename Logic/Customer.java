/**
 * Provides attributes and methods that defines the customer object
 * @author UDOETTE
 * @version 12/24
 */

package Logic;
import java.util.ArrayList;
import java.util.Iterator;
public class Customer
{
    private String fname, lname;
    private ArrayList<String>parcelList; // a collections that stores customer parcel IDs.
    private Log logger = Log.getInstance();
    private Notifier activator = Notifier.getInstance(); //

    //Constructor
    public Customer(String fname, String lname){
        this.fname = fname;
        this.lname = lname;
        parcelList = new ArrayList<>();
    }

    /**
     * returns full name of a customer as string
     * @return full name of a customer
     */
    public String getFullName(){
        return lname+" "+fname;
    }
    /**
     * returns the list of parcels a customer owns
     * @return list of parcel owned by a customer
     */
    public ArrayList<String> getParcelList(){
        return parcelList;
    }
    /**
     * returns the first of a customer as string
     * @return the first name of a customer
     */
    public String getFname() {
        return fname;
    }

    /**
     *returns customer's lastname as string
     * @return customer's lastname
     */
    public String getLname() {
        return lname;
    }

    /**
     * adds new parcel to customer parcel list
     * notify observers
     * record event with appropriate message
     * @param parcelId- new parcel to be added to customer list
     */
    public void addParcelToList(String parcelId){
        parcelList.add(parcelId);
        activator.notifyObservers();
        String msg = parcelId+ " added to "+getFullName()+" collections";
        logger.writeEvent(msg);
    }

    /**
     * returns all customer's parcels as a string
     * @return all parcels owned by a customer
     */
    public String getParcels(){
        String str = "";
        for(String pid: parcelList){
            str += pid+'\n';
        }
        return str;
    }

    /**
     * removes a specified parcel from the customer parcel collection.
     * notify observers
     * record event with appropriate message
     * @param parcelId
     */
    public void removeParcel(String parcelId){
        parcelList.remove(parcelId);
        activator.notifyObservers();
        String msg = parcelId+" removed from "+getFullName()+" collections";
        logger.writeEvent(msg);
    }

    /**
     * returns a string representation of a customer details
     * @return the details of a customer
     */
    @Override
    public String toString() {
        return "First Name: "+fname+
                "\nLast Name: "+lname+
                "\nParcel(s): "+parcelList;
    }
}
