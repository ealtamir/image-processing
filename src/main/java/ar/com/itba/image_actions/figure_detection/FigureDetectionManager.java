package ar.com.itba.image_actions.figure_detection;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.utils.CustomBufferedImage;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 14.10.15.
 */
public class FigureDetectionManager {

    public static void applyHoughCircleDetection(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage markedShapesImg = HoughFigureDetection.detectCircle(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(markedShapesImg);
    }

    public static void applyHoughLineDetection(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage markedShapesImg = HoughFigureDetection.detectLine(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(markedShapesImg);
    }
}
