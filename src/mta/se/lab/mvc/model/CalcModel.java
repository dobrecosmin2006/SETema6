package mta.se.lab.mvc.model;

import mta.se.lab.mvc.exceptions.InputException;
import mta.se.lab.mvc.interfaces.IModelListener;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CalcModel {
    // Constants
    public static final String INITIAL_VALUE = "0";
    public static final String INITIAL_VALUE1="0";

    // Member variable defining state of calculator, the total current value
    private BigInteger VitezaVantului;
    private BigInteger Grade;

    private List<IModelListener> mListeners;

    /**
     * Constructor
     */
    public CalcModel()
    {
        VitezaVantului = new BigInteger(INITIAL_VALUE);
        Grade = new BigInteger(INITIAL_VALUE);
    }

    /**
     * Set the total value.
     *
     * @param value New value that should be used for the calculator total.
     */
    public void setValue(String value, String value2 // aici vreau sa mai pun un STRING
    ) throws InputException {
        try {
            VitezaVantului = new BigInteger(value);
            Grade          =  new BigInteger(value2);
            notifyListeners();

        } catch (NumberFormatException e) {

            throw new InputException(value, e.getMessage());
        }
    }

    /**
     * Return current calculator total.
     */
    public String getValueVant() {
        return VitezaVantului.toString();
    }
    public String getValueGrade() {
        return Grade.toString();
    }

    /**
     * Adds the view listener to the list
     *
     * @param listener The model event listener
     */
    public void addModelListener(IModelListener listener) {
        if (mListeners == null) {
            mListeners = new ArrayList<IModelListener>();
        }

        mListeners.add(listener);
    }

    /**
     * Notifies the views listeners of the changed state (value)
     */
    private void notifyListeners() {
        if (mListeners != null && !mListeners.isEmpty()) {
            for (IModelListener listener : mListeners)
                listener.onUpdate();
        }
    }
}
