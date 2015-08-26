package ar.com.itba.image_actions.operations;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.LabeledSliderPanel;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 26.08.15.
 */
public class ScalarMultOperation extends ParameterizedImageAction {

    private final int MIN_VAL = 0;
    private final int MAX_VAL = 300;
    private final int DEFAULT_VAL = 100;
    private final int DIV = 100;
    private float scalar;

    public ScalarMultOperation(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        scalar = DEFAULT_VAL / DIV;
        addScalarSlider();
        contents.setVisible(true);
    }

    private void addScalarSlider() {
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

    @Override
    protected void processSliderChange(JSlider changedSlider) {
        int val = changedSlider.getValue();
        scalar = (float) val / (float) DIV;
        if (automaticChange) {
            updateImage();
        }
    }

    @Override
    protected void updateImage() {
        BufferedImage newImg = Operators.imageScalarMult(scalar, originalImage);
        quickDrawPanel.image(newImg);
    }
}
