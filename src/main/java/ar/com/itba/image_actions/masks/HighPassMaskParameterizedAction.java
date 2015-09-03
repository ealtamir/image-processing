package ar.com.itba.image_actions.masks;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.QuickDrawPanel;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 03.09.15.
 */
public class HighPassMaskParameterizedAction extends ParameterizedImageAction {

    private final int MIN_VAL = 1;
    private final int MAX_VAL = 15;
    private final int DEFAULT_VAL = 1;

    public HighPassMaskParameterizedAction(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(DEFAULT_VAL, MIN_VAL, MAX_VAL, DIV,
                true, this, new TextFormatter(DIV));
    }

    @Override
    protected void updateImage() {
        if (sliderValue % 2 == 1) {
            BufferedImage newImg = Masks.applyHighPassMask(originalImage, sliderValue);
            quickDrawPanel.modifyCurrentImage(newImg);
        }

    }
}
