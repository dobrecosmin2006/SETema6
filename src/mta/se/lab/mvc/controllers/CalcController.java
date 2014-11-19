package mta.se.lab.mvc.controllers;

import mta.se.lab.mvc.exceptions.InputException;
import mta.se.lab.mvc.interfaces.IController;
import mta.se.lab.mvc.interfaces.IView;
import mta.se.lab.mvc.model.CalcModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CalcController implements IController {
    // The Controller needs to interact with both the Model and View.
    private CalcModel mModel;

    // The list of views that listen for updates
    private List<IView> mViews;

    /**
     * Constructor
     */
    public CalcController() {
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals(ACTION_CALCULATE)) {
            // Make the operation
            try {
                JButton source = (JButton) event.getSource();
                if (source != null && source.getAction() != null && source.getAction().getValue(ACTION_CALCULATE) != null) {
                    String userInput = source.getAction().getValue(ACTION_CALCULATE).toString();
                    makeOperation(userInput);
                } else {
                    notifyViews(true, "Invalid operation data");
                }
            } catch (InputException e) {
                notifyViews(true, e.getMessage());
            } catch (ClassCastException ec) {
                notifyViews(true, ec.getMessage());
            }
        } else if (event.getActionCommand().equals(ACTION_RESET)) {
            // Reset the model to its default state
            if (mModel != null) {
                try {
                    mModel.setValue(CalcModel.INITIAL_VALUE);
                } catch (InputException e) {
                    notifyViews(true, e.getMessage());
                }
            }
        }
    }

    /**
     * Adds a view reference in order to interact with it
     *
     * @param view The view from the controller will receive events and send messages
     */
    public void addView(IView view) {
        if (mViews == null) {
            mViews = new ArrayList<IView>();
        }

        mViews.add(view);
    }

    /**
     * Adds a reference to the model, so it can update it
     *
     * @param model The data model reference
     */
    public void addModel(CalcModel model) {
        mModel = model;
    }

    /**
     * Notifies the views when an message must be displayed
     *
     * @param isError {@code true} if the message is an error, {@code false} otherwise
     * @param message The string to be displayed
     */
    private void notifyViews(boolean isError, String message) {
        if (mViews != null && !mViews.isEmpty()) {
            for (IView view : mViews) {
                view.onMessage(isError, message);
            }
        }
    }

    /**
     * Multiply current total by a number. The operation can be generalized
     *
     * @param operand Number (as string) to multiply total by
     */
    private void makeOperation(String operand) throws InputException {
        if (mModel != null) {
            BigInteger currentValue = new BigInteger(mModel.getValue());

            try {
                // Update the model
                mModel.setValue(currentValue.multiply(new BigInteger(operand)).toString());
            } catch (NumberFormatException e) {
                throw new InputException(operand, e.getMessage());
            }
        }
    }
}
