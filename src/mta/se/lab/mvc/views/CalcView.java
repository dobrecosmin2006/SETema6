package mta.se.lab.mvc.views;

import mta.se.lab.mvc.interfaces.IController;
import mta.se.lab.mvc.interfaces.IModelListener;
import mta.se.lab.mvc.interfaces.IView;
import mta.se.lab.mvc.model.CalcModel;
import mta.se.lab.mvc.utils.CalculateAction;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class CalcView extends JFrame implements IModelListener, IView {
    private static final long serialVersionUID = -5758555454500685115L;

    // View Components
    private JTextField mUserInputTf = new JTextField(6);
    private JTextField mTotalTf = new JTextField(20);
    private JButton mMultiplyBtn = new JButton("Multiply");
    private JButton mClearBtn = new JButton("Clear");

    private IController mCalcController;

    private CalcModel mModel;

    /**
     * Constructor
     */
    public CalcView() {
        // Initialize components
        mTotalTf.setEditable(false);
        mUserInputTf.getDocument().addDocumentListener(new DocumentListener() {
            // TODO - this is a hack, find a better solution to add data to an event
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            public void removeUpdate(DocumentEvent e) {
                warn();
            }

            public void insertUpdate(DocumentEvent e) {
                warn();
            }

            public void warn() {
                String newValue = mUserInputTf.getText();
                if (mMultiplyBtn.getAction() == null) {
                    mMultiplyBtn.setAction(new CalculateAction());
                }
                mMultiplyBtn.getAction().putValue(IController.ACTION_CALCULATE, newValue);
                mMultiplyBtn.setActionCommand(IController.ACTION_CALCULATE);
                mMultiplyBtn.setText("Multiply");
            }
        });

        // Layout the components.
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(new JLabel("Input"));
        content.add(mUserInputTf);
        content.add(mMultiplyBtn);
        content.add(new JLabel("Total"));
        content.add(mTotalTf);
        content.add(mClearBtn);

        // Finalize layout
        this.setContentPane(content);
        this.pack();

        this.setTitle("Calculator");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Sets the view's reference to the model - Only get operations allowed
     *
     * @param model The calc model
     */
    public void addModel(CalcModel model) {
        mModel = model;
        mTotalTf.setText(model.getValue());
    }

    /**
     * Sets the view's event listener - the controller - so that the changes made by the user in the view, can be reflected in the model
     *
     * @param controller The controller (event listener)
     */
    public void addController(IController controller) {
        mMultiplyBtn.setActionCommand(IController.ACTION_CALCULATE);
        mMultiplyBtn.addActionListener(controller);

        mClearBtn.setActionCommand(IController.ACTION_RESET);
        mClearBtn.addActionListener(controller);
    }

    @Override
    public void onMessage(boolean isError, String message) {
        if (isError) {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, message, "Calc MVC", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void onUpdate() {
        mTotalTf.setText(mModel.getValue());
    }
}