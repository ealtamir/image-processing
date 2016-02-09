package ar.com.itba.image_actions;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;

/**
 * Created by Enzo on 09.02.16.
 */
public class ClusteringManager {

    public static void applyMeanShift(QuickDrawPanel quickDrawPanel, MainWindow window) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(window);
                quickDrawPanel.setParameterizedActionWindow(new MeanShiftParameterizedAction(quickDrawPanel));
            }
        });
    }
}
