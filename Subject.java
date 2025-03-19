/**
 * Provides attributes and methods that defines Subject
 * @author U. UDOETTE
 * @version 12/24
 */

package Logic;
import Presentation.Observer;
public interface Subject {
    //register new observer
    public void registerObserver(Observer obs);

    //remove an Obsever from list of observers
    public void removeObserver(Observer obs);

    //notify all observers
    public void notifyObservers();
}
