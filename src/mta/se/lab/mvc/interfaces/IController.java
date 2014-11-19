package mta.se.lab.mvc.interfaces;

import java.awt.event.ActionListener;

/**
 * Created by John on 18/11/2014.
 *
 * The interface implemented by the controller and made public so that all views can use it
 */
public interface IController extends ActionListener
{
    
    public static final String ACTION_GENERATE = "GENERATE";


}
