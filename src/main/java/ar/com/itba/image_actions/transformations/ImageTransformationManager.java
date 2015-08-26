package ar.com.itba.image_actions.transformations;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageTransformationManager {

    static public void showNegative(QuickDrawPanel quickDrawPanel, MainWindow window) {
        BufferedImage negative = Transformations.getNegative(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(window);
        quickDrawPanel.modifyCurrentImage(negative);
    }

    public static void showDynamicCompression(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(mainWindow);
                quickDrawPanel.setParameterizedActionWindow(new DynamicRangeTransform(quickDrawPanel));
            }
        });

    }
}
