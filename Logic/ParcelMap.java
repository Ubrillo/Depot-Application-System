/**
 * Provides attributes and methods that defines ParcelMap object
 * stores a list waiting parcels in the depot
 * @author U. UDOETTE
 * @version 12/24
 */

package Logic;
import java.util.*;
public class ParcelMap {
    private HashMap<String, Parcel> waitingList;
    private Log logger = Log.getInstance();
    private Notifier activator = Notifier.getInstance();
    public ParcelMap(){
        waitingList = new HashMap<>();
    }

    /**
     * adds a new parcel to the waiting list
     * @param newParcel - new parcel object to be added to waitinglist
     */
    public void addParcelToWaitingList(Parcel newParcel){
        String id = newParcel.getId();
        waitingList.put(id, newParcel);
        String msg =  id+" added to waiting list";
        activator.notifyObservers();
        logger.writeEvent(msg);
    }

    /**
     * removes a specified parcel object from waitingList
     * @param parcel - specified parcel to be removed
     */
    public void removeParcelFromWaitingList(Parcel parcel){
        String id = parcel.getId();
        waitingList.remove(id);
    }
    /**
     * remove a parcel for waiting parcel list given the id
     * @param parcelId
     * log events with appropriate message
     * notify observers
     */
    public void removeParcelFromWaitingList(String parcelId){
        waitingList.remove(parcelId);
        String msg = parcelId+" removed from waiting list";
        activator.notifyObservers();
        logger.writeEvent(msg);
    }

    /**
     *finds and return a specified parcel from waiting list
     * @param id - the id of the parcel being looked
     * @return a parcel object or null if not found
     */
    public Parcel getParcel(String id){
        if (isInWaitingList(id)){
            return waitingList.get(id);
        }
        return null;
    }

    /**
     * returns a list of all parcels in the waiting list as string
     * @return all  uncollected parcels
     */
    public String getAllWaitingParcels(){
        String str = "***************** All Waiting Parcels ************"+'\n';
        for (String pid: waitingList.keySet()){
            str += pid+'\n';
        }
        return str;
    }

    /**
     * @return details of all uncollected parcels as string
     */
    public String getAllWaitingParcelsDetails(){
        String str = "***************** All Waiting Parcels ************"+'\n';
        for (String pid: waitingList.keySet()){
            Parcel parcel = getParcel(pid);
            str += parcel.toString();
        }
        return str;
    }
    /**
     * @param parcelId -  parcel id of parcel
     * @return true if parcel is in  list, otherwise return false
     */
    public boolean isInWaitingList(String parcelId){
        return waitingList.containsKey(parcelId);
    }

    /**
     * @return all uncollected parcels
     */
    @Override
    public String toString() {
        return "ParcelMap{" +
                "waitingList=" + waitingList +
                '}';
    }
}