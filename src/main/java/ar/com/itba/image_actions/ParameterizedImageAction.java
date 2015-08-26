package ar.com.itba.image_actions;

import ar.com.itba.image_actions.operations.ScalarMultOperation;
import ar.com.itba.panel.LabeledSliderPanel;
import ar.com.itba.panel.QuickDrawPanel;
import com.sun.xml.internal.bind.v2.model.annotation.Quick;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

abstract public class ParameterizedImageAction extends JFrame implements ActionListener, ChangeListener {

    protected boolean automaticChange = false;
    protected QuickDrawPanel quickDrawPanel;
    protected List<LabeledSliderPanel> sliderPanels;
    protected JPanel contents;

    private JCheckBox automaticSwitch;
    private JButton doChange;
    private final int DIV = 1;

    protected BufferedImage originalImage;

    public ParameterizedImageAction(QuickDrawPanel quickDrawPanel) {
        this.quickDrawPanel = quickDrawPanel;
        this.originalImage = quickDrawPanel.image();
        sliderPanels = new ArrayList<LabeledSliderPanel>();
        contents = new JPanel();
        setContentPane(contents);
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
        contents.add(automaticSwitchOptions());
        contents.setVisible(true);
        setVisible(true);
        setResizable(false);
        setSize(300, 100);
    }

    public JPanel automaticSwitchOptions() {
        JPanel automaticSwitchOptions = new JPanel();
        automaticSwitchOptions.setLayout(new BoxLayout(automaticSwitchOptions, BoxLayout.X_AXIS));

        automaticSwitch = new JCheckBox("Automático");
        automaticSwitch.setSelected(false);
        automaticSwitch.addActionListener(this);
        automaticSwitch.setVisible(true);
        automaticSwitchOptions.add(automaticSwitch);

        doChange = new JButton("Actualizar");
        doChange.addActionListener(this);
        doChange.setVisible(true);
        automaticSwitchOptions.add(doChange);

        return automaticSwitchOptions;
    }

    protected void processActionEvent(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(automaticSwitch)) {
            automaticChange = ((JCheckBox) source).isSelected();
        } else if (source.equals(doChange)) {
            updateImage();
        }
    }

    public void close() {
        setVisible(false);
        dispose();
    }

    public void changeTargetImage(QuickDrawPanel quickDrawPanel) {
        this.quickDrawPanel = quickDrawPanel;
        originalImage = quickDrawPanel.image();
    }


    static public class TextFormatter {
        private int div;

        public TextFormatter(int div) {
            this.div = div;
        }

        public String format(int num) {
            float val = (float) num / div;
            return String.format("%1.2f ", val);
        }

    }

    protected void identifyChangedSlider(ChangeEvent e) {
        JSlider changedSlider = (JSlider) e.getSource();
        JSlider slider;

        for (LabeledSliderPanel panel : sliderPanels) {
            slider = panel.getSlider();
            if (slider.equals(changedSlider)) {
                processSliderChange(changedSlider);
            }
        }
    }

    protected void addScalarSlider(int DEFAULT_VAL, int MIN_VAL, int MAX_VAL, int DIV, boolean b,
                                 ParameterizedImageAction scalarMultOperation, TextFormatter textFormatter) {
        LabeledSliderPanel scalarSlider = new LabeledSliderPanel(DEFAULT_VAL, MIN_VAL,
                MAX_VAL, true, this, new TextFormatter(DIV));
        contents.add(scalarSlider);
        scalarSlider.setVisible(true);
        sliderPanels.add(scalarSlider);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        processActionEvent(e);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        identifyChangedSlider(e);
    }


    abstract protected void processSliderChange(JSlider changedSlider);

    protected abstract void updateImage();

}
