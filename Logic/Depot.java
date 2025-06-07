/**
 * Provides attributes and methods that defines Depot or Manager class
 * @author U. UDOETTE
 * @version 12/24
 */

package Logic;
import java.io.*;
import java.util.HashMap;

public class Depot
{
    private ParcelMap parcelList; // list of waiting parcels.
    private CustomersQueue queue; //customer queue
    private Notifier activator = Notifier.getInstance(); // instantiate a notifier object
    private Log logger = Log.getInstance(); //
    private HashMap<String, Parcel>collectedList;

    public Depot(){
        parcelList = new ParcelMap();
        queue = new CustomersQueue();
        collectedList = new  HashMap<>();
//        readParcelData("Depot-System/src/Database/parcels.csv");
//        readCustomerData("Depot-System/src/Database/customers.csv");

    }
    /**
     * reads customer data  from file
     * calls processCustomerRow to process each line
     * @param filename- the file to read customer data from
     *returns the number of rows read or -1 if otherwise error occurs.
     */
    public int  readCustomerData(String filename){
        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int count = 0;
            while(br.ready()){
                line = br.readLine();
                int result = processCustomerRow(line);
                if (result == 0) count++;
            }
            br.close();
            activator.notifyObservers();
            return count;
        }catch(Exception xx){
            System.out.println(xx.getMessage());
            return -1;
        }
    }
    /**
     *process each row of customer data read from file
     * checks for valid name format
     * checks for valid parcel id format
     * checks for duplicated parcel id
     * creates a customer object
     * adds customer to queue
     * checks if there are multiple  entries of a customer but with different parcel ids
     * checks that parcel id of a customer is valid
     * adds parcel to the customer parcel list
     * @param row- the row of data to be processed
     * @return 1 if input name is not valid
     * @return 2 if parcel id format is invalid
     * @return 3 if  parcel with the customer parcel id does not exist
     * @return 4 if parcel id is already registered to another parcel
     * @return 0 if above conditions are false
     * @return -1 - if error occurs
     * @
     */
    public int processCustomerRow(String row){
        try{
            String parts[] = row.split(",");
            String name = parts[0].trim();
            String parcelId = parts[1].trim();
            String partsName[] = name.split(" ");
            String fname = partsName[0].trim();
            String lname = partsName[1].trim();

            if (checkNameFormat(lname) && checkNameFormat(fname)){
                if (checkParcelFormat(parcelId)){
                    if (parcelList.isInWaitingList(parcelId)){
                        if (!isDuplicateParcel(parcelId)){
                            if (!isDuplicateCustomer(fname, lname)){
                                Customer customer = new Customer(fname, lname);
                                customer.addParcelToList(parcelId);
                                queue.addNewCustomer(customer);
                            }else{
                                Customer customer = queue.getCustomer(fname, lname);
                                customer.addParcelToList(parcelId);
                                //return 0;
                            }
                        }else{return 4;}
                    }else{return 3;}
                }else{return 2;}
            }else{return 1;}
            return 0;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return -1;
        }
    }
    /**
     *
     * read parcel data from file
     * @param filename- file containing parcel data
     */
    public int readParcelData(String filename){
        try{
            FileReader fr = new FileReader(filename);
            BufferedReader br = new BufferedReader(fr);
            String line;
            int count=0;
            while(br.ready()){
                line = br.readLine();
                int result = processParcelRow(line);
                if (result == 0) count++;
            }
            br.close();
            activator.notifyObservers();
            return count;
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    /**
     * process each parcel row data from file
     * @param row-row of data being read
     * @return 1 if parcel id format is not valid
     * @return 0 if above condition is false
     * @return -1 if error occurs
     */
    public int  processParcelRow(String row){
        try{
            String parts[] = row.split(",");
            String parcelId = parts[0].trim();
            int dip = Integer.parseInt(parts[1].trim());
            double weight = Integer.parseInt(parts[2].trim());
            double length = Double.parseDouble(parts[3].trim());
            double width =  Double.parseDouble(parts[4].trim());
            double  height =  Double.parseDouble(parts[5].trim());

            if (checkParcelFormat(parcelId)){
                String dim = length + "x" + width + "x" + height;
                Parcel p1 = new Parcel(parcelId, dip, weight, dim);
                parcelList.addParcelToWaitingList(p1);
            }else{return 1;}
            return 0;
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    /**
     * checks if format of the parcel is valid. return true if so otherwise false
     * @param parcelId- the id of the parcel to check
     * @return - returns true if parcel id is valid, false otherwise
     */
    public boolean checkParcelFormat(String parcelId){
        try{
            if (parcelId.length() == 4) {
                char firstChar = parcelId.charAt(0);
                if (firstChar == 'C' || firstChar == 'X') {
                    int code = Integer.parseInt(parcelId.substring(2));
                    return true;
                } else{
                    System.out.println(parcelId + " invalid format, not added");
                    return false;
                }
            }else{
                System.out.println(parcelId + " invalid format -- too long");
                return false;
            }
        } catch (Exception xx){
            System.out.println(xx.getMessage());
            return false;
        }
    }

    /**
     * Checks if name format is valid
     * @param name- customer name to check
     * @return true if customer name is valid, false otherwise
     */
    public boolean checkNameFormat(String name){
       boolean bool = name.matches("[a-zA-Z]+");
       if (!bool){
           System.out.println(name +" invalid name format");
       }
       return bool;
    }

    /**
     * return True if parcel id is already registered to another parcel, False otherwise
     * @param parcelId- parcel id to check
     * @return True, False otherwise
     */
    private boolean isDuplicateParcel(String parcelId){
        if (queue.isEmpty()){
            return false;
        }
        Customer customer = queue.getCustomer(parcelId);
        return customer != null;
    }

    /**
     * return if true if customer have more than one parcel
     * @param fname-customer first name
     * @param lname-customer last name
     * @return True if a customer have more than one parcel
     */
    private boolean isDuplicateCustomer(String fname, String lname){
        if (queue.isEmpty()){
            return false;
        }
        Customer customer = queue.getCustomer(fname, lname);
        return customer != null;
    }

    /**
     * @return the queue object
     */
    public CustomersQueue getQueue(){
        return queue;
    }

    /**
     * @return parcel list object
     */
    public ParcelMap getParcelList(){
        return parcelList;
    }

    /**
     * adds parcel to the collected list
     * @param parcelId- parcel to add to collected list
     * remove parcel form waiting list
     * notify observers
     *log events with appropriate message
     */
    public void addParcelToCollectedList(String parcelId){
        Parcel  parcel = parcelList.getParcel(parcelId);
        collectedList.put(parcelId, parcel);
        parcelList.removeParcelFromWaitingList(parcelId);
        String msg = parcelId+" added to collected list";
        activator.notifyObservers();
        logger.writeEvent(msg);
    }

    /**
     * returns a list of all parcel in the collected list
     * @return String
     */
    public String getAllCollectedParcels(){
        String str = "********** COLLECTED PARCELS LIST *************\n";
        for (String pid: collectedList.keySet()){
            str += pid +'\n';
        }
        return str.toString();
    }

    /**
     * returns a  parcel object in the collected list if found, return null otherwise
     * @param pid- parcel id of the parcel to look for
     * @return parcel object if found in the collected list, return null otherwise
     */
    public Parcel findACollectedParcel(String pid){
        if (collectedList.containsKey(pid)){
            return collectedList.get(pid);
        }
        return null;
    }
}