package ar.com.itba.image_actions.masks;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.LabeledSliderPanel;
import ar.com.itba.panel.QuickDrawPanel;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 03.09.15.
 */
public class GaussianMaskParameterizedAction extends ParameterizedImageAction {

    private final int MIN_VAL = 1;
    private final int MAX_VAL = 5;
    private final int DEFAULT_VAL = 1;

    private LabeledSliderPanel slider;

    public GaussianMaskParameterizedAction(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(DEFAULT_VAL, MIN_VAL, MAX_VAL, DIV,
                true, this, new TextFormatter(DIV));
//        slider = sliderPanels.get(0);
//        slider.changeLabelWithoutFormat(0);
    }

    @Override
    protected void updateImage() {
//        double val = getRealValue(sliderValue);
//        slider.changeLabelWithoutFormat(val);
        BufferedImage newImg = Masks.applyGaussianMask(originalImage, sliderValue);
        quickDrawPanel.modifyCurrentImage(newImg);

    }

    private double getRealValue(int sliderValue) {
        switch (sliderValue) {
            case 1:
                return 0;
            case 2:
                return 0.1;
            case 3:
                return 0.01;
            case 4:
                return 0.001;
            case 5:
                return 0.0001;
            default:
                return 0;
        }
    }
}
