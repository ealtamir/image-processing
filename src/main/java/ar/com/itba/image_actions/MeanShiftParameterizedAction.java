package ar.com.itba.image_actions;

import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.utils.CustomBufferedImage;

import javax.swing.*;

/**
 * Created by Enzo on 09.02.16.
 */
public class MeanShiftParameterizedAction extends ParameterizedImageAction {

    public MeanShiftParameterizedAction(QuickDrawPanel quickDrawPanel) {
        super(quickDrawPanel);
        addScalarSlider(6, 1, 16, 1,
                true, this, new TextFormatter(1));
        addScalarSlider(12, 1, 16, 1,
                true, this, new TextFormatter(1));
    }

    @Override
    protected void updateImage() {
        int radius = sliderPanels.get(0).getSlider().getValue();
        int bandwidth = sliderPanels.get(1).getSlider().getValue();
        CustomBufferedImage newImg = Clustering.meanShiftClustering((CustomBufferedImage) originalImage, radius, bandwidth);
        quickDrawPanel.modifyCurrentImage(newImg);
    }
}
