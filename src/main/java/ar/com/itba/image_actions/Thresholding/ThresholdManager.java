package ar.com.itba.image_actions.Thresholding;

import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

/**
 * Created by Enzo on 12.09.15.
 */
public class ThresholdManager {

    public static void applyGlobalThreshold(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(mainWindow);
                quickDrawPanel.setParameterizedActionWindow(new GlobalThresholdOperation(quickDrawPanel));
            }
        });
    }

    public static void applyOtsuThreshold(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        CopyRightAction.peformCopyRightAction(mainWindow);
        BufferedImage newImg = OtsuThreshold.applyTransformation(quickDrawPanel.image());
        quickDrawPanel.modifyCurrentImage(newImg);
    }
}
