package ar.com.itba.panel;

import ar.com.itba.image_actions.ParameterizedImageAction;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by Enzo on 26.08.15.
 */
public class LabeledSliderPanel extends JPanel implements ChangeListener {

    private final ParameterizedImageAction.TextFormatter txtFmt;
    private Component parent;
    private JSlider slider;
    private JLabel label;

    public LabeledSliderPanel(int defaultVal, int min, int max, boolean horizontal,
                              Component parent, ParameterizedImageAction.TextFormatter txtFmt) {
        this.parent = parent;
        this.txtFmt = txtFmt;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setLabel(defaultVal, txtFmt);
        setSlider(defaultVal, min, max, horizontal);
    }

    private void setLabel(int defaultVal, ParameterizedImageAction.TextFormatter txtFmt) {
        label = new JLabel();
        label.setText(txtFmt.format(defaultVal));
        label.setVisible(true);
        add(label);
    }

    private void setSlider(int defaultVal, int min, int max, boolean horizontal) {
        slider = new JSlider();
        slider.setMinimum(min);
        slider.setMaximum(max);
        slider.setValue(defaultVal);
        slider.setEnabled(true);
        slider.setVisible(true);
        if (horizontal) {
            slider.setOrientation(JSlider.HORIZONTAL);
        } else {
            slider.setOrientation(JSlider.VERTICAL);
        }
        add(slider);
        slider.addChangeListener(this);
    }

    public JSlider getSlider() {
        return slider;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider slider = (JSlider) e.getSource();
        changeLabel(slider.getValue());
        ((ParameterizedImageAction) parent).stateChanged(e);
    }

    private void changeLabel(int value) {
        label.setText(txtFmt.format(value));
    }

    public void changeLabelWithoutFormat(double val) {
        label.setText(String.valueOf(val));
    }
}
