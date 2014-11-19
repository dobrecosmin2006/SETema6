package mta.se.lab.mvc.interfaces;

/**
 * Created by John on 18/11/2014.
 *
 * The interface made public to the controller (observer pattern) - goes both ways
 */
public interface IModelListener {

    /**
     * Notifies the listeners that an update occurred in the model
     */
    public void onUpdate();
}
