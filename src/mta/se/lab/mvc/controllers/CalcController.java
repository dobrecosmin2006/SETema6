package mta.se.lab.mvc.controllers;

import mta.se.lab.mvc.exceptions.InputException;
import mta.se.lab.mvc.interfaces.IController;
import mta.se.lab.mvc.interfaces.IView;
import mta.se.lab.mvc.model.CalcModel;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    /*
    * This is my method for randomize integers
     */
    public static int getRandomNumberFrom(int min, int max) {

        Random foo = new Random();
        int randomNumber = foo.nextInt((max + 1) - min) + min;

        return randomNumber;

    }

    @Override
    public void actionPerformed(ActionEvent event) {

         if (event.getActionCommand().equals(ACTION_GENERATE)) {
            // Reset the model to its default state
            if (mModel != null) {
                try {
                    // set the initial values
                    mModel.setValue(CalcModel.INITIAL_VALUE,CalcModel.INITIAL_VALUE1);

                    // get random integers
                    int x=0;
                    int y=+299;
                    int rand = this.getRandomNumberFrom(x,y);

                    // get random integers
                    String string = "" + rand;
                    int x1=-40;
                    int y1=69;
                    int rand1 = this.getRandomNumberFrom(x1,y1);
                    String string1 = "" + rand1;
                    //set new randomize values by this method
                    mModel.setValue(string,string1);

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

}
