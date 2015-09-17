package ar.com.itba.image_actions.masks;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.LabeledSliderPanel;
import ar.com.itba.panel.QuickDrawPanel;

/**
 * Created by Enzo on 03.09.15.
 */
public class GaussianMaskParameterizedAction extends ParameterizedImageAction {

    private final int MIN_VAL = 1;
    private final int MAX_VAL = 15;
    private final int DEFAULT_VAL = 1;

    private LabeledSliderPanel slider;

    public GaussianMaskParameterizedAction(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(DEFAULT_VAL, MIN_VAL, MAX_VAL, DIV,
                true, this, new TextFormatter(DIV));
    }

    @Override
    protected void updateImage() {
        BufferedImage newImg = Masks.applyGaussianMask(originalImage, sliderValue);
        quickDrawPanel.modifyCurrentImage(newImg);

    }

}
