package ar.com.itba.image_actions.corner_detection;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.utils.CustomBufferedImage;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 09.11.15.
 */
public class CornerDetectionManager {

    public static void detectCornersWithHarris(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        CustomBufferedImage newImg = new CustomBufferedImage(quickDrawPanel.image());
//        BufferedImage imgWithCorners = HarrisCornerDetection.harrisCornerDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
//        quickDrawPanel.modifyCurrentImage(imgWithCorners);

        HarrisFast obj = new HarrisFast(newImg);
        int[][] result = obj.filter(2, 0.04, 3);

        quickDrawPanel.modifyCurrentImage(newImg);

    }
}
