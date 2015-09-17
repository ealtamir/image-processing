package ar.com.itba.image_actions.masks;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.QuickDrawPanel;

/**
 * Created by Enzo on 03.09.15.
 */
public class MeanMaskParameterizedAction extends ParameterizedImageAction{

    private final int MIN_VAL = 1;
    private final int MAX_VAL = 15;
    private final int DEFAULT_VAL = 1;

    public MeanMaskParameterizedAction(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(DEFAULT_VAL, MIN_VAL, MAX_VAL, DIV,
                true, this, new TextFormatter(DIV));
    }

    @Override
    protected void updateImage() {
        if (sliderValue % 2 == 1) {
            BufferedImage newImg = Masks.applyMeanMask(originalImage, sliderValue);
            quickDrawPanel.modifyCurrentImage(newImg);
        }

    }
}
