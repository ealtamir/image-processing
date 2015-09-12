package ar.com.itba.image_actions.Thresholding;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;
import java.awt.image.BufferedImage;

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

    }
}
