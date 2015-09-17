package ar.com.itba.image_actions.edge_detection;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.QuickDrawPanel;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 17.09.15.
 */
public class ParameterizedLaplacianWithSlope extends ParameterizedImageAction{

    private final int MIN_VAL = 2;
    private final int MAX_VAL = 255;
    private final int DEFAULT_VAL = 1;

    public ParameterizedLaplacianWithSlope(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(DEFAULT_VAL, MIN_VAL, MAX_VAL, DIV,
                true, this, new TextFormatter(DIV));
    }

    @Override
    protected void updateImage() {
        BufferedImage newImg = LaplacianMethods.laplacianWithSlope(originalImage, sliderValue);
        quickDrawPanel.modifyCurrentImage(newImg);
    }
}
