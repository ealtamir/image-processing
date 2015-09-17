package ar.com.itba.image_actions.Thresholding;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.QuickDrawPanel;

/**
 * Created by Enzo on 12.09.15.
 */
public class GlobalThresholdOperation extends ParameterizedImageAction{

    private final int THRESHOLD_MIN_VAL = 0;
    private final int THRESHOLD_MAX_VAL = 255;
    private final int THRESHOLD_DEFAULT_VAL = 100;

    private final int DELTA_MIN_VAL = 2;
    private final int DELTA__MAX_VAL = 150;
    private final int DELTA_DEFAULT_VAL = 50;

    private final int DIV = 1;

    private int threshold = THRESHOLD_DEFAULT_VAL;
    private int delta = DELTA_DEFAULT_VAL;

    public GlobalThresholdOperation(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(THRESHOLD_DEFAULT_VAL, THRESHOLD_MIN_VAL, THRESHOLD_MAX_VAL, DIV, true, this, new TextFormatter(DIV));
        addScalarSlider(DELTA_DEFAULT_VAL, DELTA_MIN_VAL, DELTA__MAX_VAL, DIV, true, this, new TextFormatter(DIV));
    }

    @Override
    protected void updateImage() {
        threshold = sliderPanels.get(0).getSlider().getValue();
        delta = sliderPanels.get(1).getSlider().getValue();
        BufferedImage resultImg = GlobalThreshold.applyGlobalThreshold(originalImage, threshold, delta);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }
}
