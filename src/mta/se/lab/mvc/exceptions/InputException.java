package mta.se.lab.mvc.exceptions;

/**
 * Created by John on 18/11/2014.
 *
 * This class implements the exception thrown when the format of the input is inappropriate
 */
public class InputException extends Exception {

    /**
     * The constructor for the input exception
     * @param input The input on witch the exception was obtained
     * @param errorMessage  The default exception message
     */
    public InputException(String input, String errorMessage) {
        super("Input Format exception on \"" + input + "\" : " + errorMessage);
    }
}
