package ar.com.itba.image_actions.transformations;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.QuickDrawPanel;

/**
 * Created by Enzo on 26.08.15.
 */
public class ThresholdTransform extends ParameterizedImageAction{

    public ThresholdTransform(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(DEFAULT_VAL, MIN_VAL, MAX_VAL, DIV,
                true, this, new TextFormatter(DIV));
    }

    @Override
    protected void updateImage() {
        BufferedImage newImg = Transformations.applyThreshold(originalImage, sliderValue);
        quickDrawPanel.modifyCurrentImage(newImg);
    }
}
