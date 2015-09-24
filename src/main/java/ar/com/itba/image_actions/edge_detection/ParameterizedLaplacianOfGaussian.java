package ar.com.itba.image_actions.edge_detection;

import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.utils.CustomBufferedImage;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 17.09.15.
 */
public class ParameterizedLaplacianOfGaussian extends ParameterizedImageAction {


    public ParameterizedLaplacianOfGaussian(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        MIN_VAL = 1;
        MAX_VAL = 500;
        DEFAULT_VAL = 100;
        DIV = 100;
        addScalarSlider(DEFAULT_VAL, MIN_VAL, MAX_VAL, DIV,
                true, this, new TextFormatter(DIV));
    }

    @Override
    protected void updateImage() {
        float val = (float) sliderValue / DIV;
        System.out.println(val);
        BufferedImage newImg = LaplacianMethods.laplacianOfGaussian(originalImage, val);
        quickDrawPanel.modifyCurrentImage(newImg);
    }
}
