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
    private JTextField mGrade = new JTextField(4);
    private JTextField mVitezaVantului = new JTextField(4);

    private JButton mMultiplyBtn = new JButton("Random Weather Forecast");

    private JButton mGenerateButon = new JButton("Generate ");

    private IController mCalcController;

    private CalcModel mModel;

    /**
     * Constructor
     */
    public CalcView() {
        // Initialize components

        mGrade.setEditable(false);
        mVitezaVantului.setEditable(false);

        mGrade.getDocument().addDocumentListener(new DocumentListener()
        {
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

            public void warn()
            {
                String newValue = mGrade.getText();
                if (mMultiplyBtn.getAction() == null)
                {
                    mMultiplyBtn.setAction(new CalculateAction());
                }



            }
        });

        // Layout the components.
        JPanel content = new JPanel();

        content.setLayout(new FlowLayout());
        content.add(new JLabel("Vremea locala: "));
        content.add(mGrade);
        content.add(new JLabel("Viteza vantului: "));
        content.add(mVitezaVantului);

        //** m clear buton -> generate
        content.add(mGenerateButon);

        // Finalize layout
        this.setContentPane(content);
        this.pack();

        this.setTitle("My Weather Forecast");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Sets the view's reference to the model - Only get operations allowed
     *
     * @param model The calc model
     */
    public void addModel(CalcModel model) {
        mModel = model;
        mVitezaVantului.setText(model.getValueVant());
        mGrade.setText(model.getValueGrade());
    }

    /**
     * Sets the view's event listener - the controller - so that the changes made by the user in the view, can be reflected in the model
     *
     * @param controller The controller (event listener)
     */
    public void addController(IController controller) {



        mGenerateButon.setActionCommand(IController.ACTION_GENERATE);
        mGenerateButon.addActionListener(controller);
    }

    @Override
    public void onMessage(boolean isError, String message) {
        if (isError) {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, message, "Weather Forecast MVC", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void onUpdate() {
        mVitezaVantului.setText(mModel.getValueVant());
        mGrade.setText(mModel.getValueGrade());
    }
}