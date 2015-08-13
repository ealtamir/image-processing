package ar.com.itba.panel;

import sun.jvm.hotspot.utilities.Hashtable;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.Dictionary;
import java.util.HashMap;

/**
 * Created by Enzo on 12.08.15.
 */
public class PixelColorChanger extends JPanel implements ChangeListener {

    private Component parent;

    final private int MIN_COLOR_VAL = 0;
    final private int MAX_COLOR_VAL = 255;
    private final int SLIDER_COUNT = 3;

    private JSlider[] colors = new JSlider[3];
    private JLabel[] colorLabels = new JLabel[3];
    private JLabel pixelLabel = new JLabel("Test");
    private Point pixel;
    private JPanel[] slidersPanel;

    public PixelColorChanger(Component parent) {
        this.parent = parent;
        createSliders();
        setElementsLayout();
    }

    public void setElementsVisibility(boolean visibility) {
        this.setVisible(visibility);
        for (int i = 0; i < SLIDER_COUNT; i++) {
            slidersPanel[i].setVisible(visibility);
            colors[i].setVisible(visibility);
            colorLabels[i].setVisible(visibility);
        }
        pixelLabel.setVisible(visibility);
    }

    private void setElementsLayout() {
        for (int i = 0; i < 3; i++) {
            slidersPanel[i].setLayout(new BoxLayout(slidersPanel[i], BoxLayout.Y_AXIS));
        }
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    }

    private void createSliders() {
        slidersPanel = new JPanel[3];

        for (int i = 0; i < 3; i++) {
            slidersPanel[i] = new JPanel();
            colors[i] = new JSlider(JSlider.VERTICAL, MIN_COLOR_VAL, MAX_COLOR_VAL, 0);
            colors[i].setPaintTicks(true);
            colorLabels[i] = new JLabel("Test");
            slidersPanel[i].add(colors[i]);
            slidersPanel[i].add(colorLabels[i]);
            add(slidersPanel[i]);
        }

        add(pixelLabel);
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
