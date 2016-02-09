package ar.com.itba.image_actions.corner_detection;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.utils.CustomBufferedImage;

/**
 * Created by Enzo on 09.11.15.
 */
public class CornerDetectionManager {

    public static void detectCornersWithHarris(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        CustomBufferedImage newImg = new CustomBufferedImage(quickDrawPanel.image());
//        BufferedImage imgWithCorners = HarrisCornerDetection.harrisCornerDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
//        quickDrawPanel.modifyCurrentImage(imgWithCorners);

        HarrisDetection obj = new HarrisDetection(newImg);
        int[][] result = obj.filter(2, 0.06, 3);

        quickDrawPanel.modifyCurrentImage(newImg);

    }
}
