package ar.com.itba.image_actions.transformations;

import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

public class ImageTransformationManager {

    static public void showNegative(QuickDrawPanel quickDrawPanel, MainWindow window) {
        BufferedImage negative = Transformations.getNegative(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(window);
        quickDrawPanel.modifyCurrentImage(negative);
    }

    public static void performEqualization(QuickDrawPanel quickDrawPanel, MainWindow window) {
        BufferedImage equalized = Transformations.getEqualized(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(window);
        quickDrawPanel.modifyCurrentImage(equalized);
    }

    public static void showDynamicCompression(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage newImg = Transformations.applyDynamicCompression(quickDrawPanel.image());
        quickDrawPanel.modifyCurrentImage(newImg);
    }
    public static void showThresholdTransform(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(mainWindow);
                quickDrawPanel.setParameterizedActionWindow(new ThresholdTransform(quickDrawPanel));
            }
        });

    }

}
