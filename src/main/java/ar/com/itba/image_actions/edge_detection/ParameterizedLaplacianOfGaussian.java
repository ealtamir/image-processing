package ar.com.itba.image_actions.edge_detection;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.utils.CustomBufferedImage;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 17.09.15.
 */
public class ParameterizedLaplacianOfGaussian extends ParameterizedImageAction {

    private final int MIN_VAL = 1;
    private final int MAX_VAL = 10;
    private final int DEFAULT_VAL = 3;

    public ParameterizedLaplacianOfGaussian(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(DEFAULT_VAL, MIN_VAL, MAX_VAL, DIV,
                true, this, new TextFormatter(DIV));
    }

    @Override
    protected void updateImage() {
        BufferedImage newImg = LaplacianMethods.laplacianOfGaussian(originalImage, sliderValue);
        quickDrawPanel.modifyCurrentImage(newImg);
    }
}
