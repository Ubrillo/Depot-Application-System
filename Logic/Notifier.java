/**
 * Provides attributes and methods that defines a Notifier object
 * Manages the Observer and Subject objects
 * Notify Observers
 * @author U. UDOETTE
 * @version 12/24
 */

package Logic;
import Presentation.Observer;
import java.util.*;

public class Notifier implements Subject {
    private List<Observer> registeredObservers = new LinkedList<>();
    public static Notifier notifier;
    private Notifier(){}

    /**
     * @return create or return Notifier instance
     */
    public static Notifier getInstance(){
        if (notifier == null){
            notifier = new Notifier();
        }
        return notifier;
    }

    /**
     * adds new observer to list of observer
     * @param obs - new observer to be added
     */
    public void registerObserver(Observer obs){
        registeredObservers.add(obs);
    }

    /**
     * remove an observer from list of observers
     * @param obs - observer to be removed
     */
    public void removeObserver(Observer obs){
        registeredObservers.remove(obs);
    }

    /**
     * notify obeservers.
     */
    public void notifyObservers(){
        for (Observer obs: registeredObservers)
            obs.update();
    }
}
