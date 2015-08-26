package ar.com.itba.image_actions.transformations;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class DynamicRangeTransform extends ParameterizedImageAction {

    private final int MIN_VAL = 0;
    private final int MAX_VAL = 255;
    private final int DEFAULT_VAL = 255;
    private final int DIV = 1;
    private int range;

    public DynamicRangeTransform(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(DEFAULT_VAL, MIN_VAL, MAX_VAL, DIV,
                true, this, new TextFormatter(DIV));
        contents.setVisible(true);
    }

    @Override
    protected void processSliderChange(JSlider changedSlider) {
        range = changedSlider.getValue();
        if (automaticChange) {
            updateImage();
        }
    }

    @Override
    protected void updateImage() {
        BufferedImage newImg = Transformations.applyDynamicCompression(originalImage, range);
        quickDrawPanel.modifyCurrentImage(newImg);
    }
}
