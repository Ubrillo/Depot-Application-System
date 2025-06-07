/**
 * Provides attributes and methods that defines a Parcel State
 * holds the state of a parcel
 * @author U. UDOETTE
 * @version 12/24
 */

package Logic;
public enum ParcelState {
    COLLECTED("Collected"), WAITING("Waiting");
    private String state;
    private ParcelState(String st){
        state = st;
    }
    public String toString(){
        return state;
    }
}
