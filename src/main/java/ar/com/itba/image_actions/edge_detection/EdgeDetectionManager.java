package ar.com.itba.image_actions.edge_detection;

import java.awt.image.BufferedImage;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;

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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(mainWindow);
                quickDrawPanel.setParameterizedActionWindow(new ParameterizedLaplacianWithSlope(quickDrawPanel));
            }
        });
    }

    public static void applyLaplacianGaussian(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(mainWindow);
                quickDrawPanel.setParameterizedActionWindow(new ParameterizedLaplacianOfGaussian(quickDrawPanel));
            }
        });
    }

    public static void applySobelModule(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = SobelEdgeDetection.applyModule(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);

    }

    public static void applyPrewittModule(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = PrewittEdgeDetection.applyModule(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }

    public static void applyPrewittDirectional(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = Prewitt2EdgeDetection.applyEdgeDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }

    public static void applyKirshDirectional(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = KirshEdgeDetection.applyEdgeDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }

    public static void applySobelDirectional(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = Sobel2EdgeDetection.applyEdgeDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }

    public static void applyDefaultDirectional(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage resultImg = DefaultEdgeDetection.applyEdgeDetection(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(resultImg);
    }
}
