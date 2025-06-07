/**
 * Provides attributes and methods that defines a Parcel object
 * @author U. UDOETTE
 * @version 12/24
 */

package Logic;
public class Parcel
{
    private String id;
    private int daysInDepot;
    private double weight;
    private String dimension;
    private ParcelState state;
    private Log logger = Log.getInstance();
    private Notifier activator = Notifier.getInstance();

    //Constructor
    public Parcel(String id, int dip, double wght, String dim){
        this.id = id;
        daysInDepot = dip;
        weight = wght;
        dimension = dim;
        state = ParcelState.WAITING;
    }

    /**
     * returns the ID of a parcel as string
     * @return id of a parcel
     */
    public String getId() {
        return id;
    }

    /**
     * returns the weight of a parcel as double
     * @return the weight of a parcel
     */
    public double getWeight(){
        return weight;
    }

    /**
     * returns the dimension of a parcel as string
     * @return the dimension of a parcel
     */
    public String getDimension(){
        return dimension;
    }

    /**
     * returns the states of a parcel
     * @return the state of a parcel
     */
    public ParcelState getState(){
        return state;
    }

    /**
     * updates the state of a parcel
     * @param newState- the newState to set the parcel to
     *log events with appropriate message
     */
    public void setState(ParcelState newState){
        state = newState;
        String msg = id+" state changed to "+state;
        activator.notifyObservers();
        logger.writeEvent(msg);
    }

    /**
     * update the numbers of days of a parcel in the depot
     * @param newDate-new day to set the parcel numbers of days to
     * notify obsevers
     * log events with appropriate message
     */
    public void setDaysInDepot(int newDate){
        daysInDepot = newDate;
        String msg = id+" days in depot changed to "+newDate;
        activator.notifyObservers();
        logger.writeEvent(msg);
    }

    /**
     * @return days a parcel has stayed in the depot as integer
     */
    public int getDaysInDepot(){
        return daysInDepot;
    }

    /**
     * @return  parcel details as string
     */
    @Override
    public String toString() {
        return "ID: "+ id+
                '\n'+"DaysInDepot: " + daysInDepot +
                '\n'+"Weight: "+ weight +"kg"+
                '\n'+"Dimension(cm3): " + dimension +
                '\n'+"State: "+state;
    }
}