package ar.com.itba.image_actions.edge_detection;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 12.09.15.
 */
public class EdgeDetectionManager {

    public static void applyPrewittHorizontal(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = PrewittEdgeDetection.horizontalEdgeDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }

    public static void applyPrewittVertical(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = PrewittEdgeDetection.verticalEdgeDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }

    public static void applySobelHorizontal(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = SobelEdgeDetection.horizontalEdgeDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }

    public static void applySobelVertical(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = SobelEdgeDetection.verticalEdgeDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }

    public static void applyLaplacian(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = LaplacianMethods.standardLaplacian(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);

    }

    public static void applyLaplacianWithSlope(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = LaplacianMethods.laplacianWithSlope(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }

    public static void applyLaplacianGaussian(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
    }
}
