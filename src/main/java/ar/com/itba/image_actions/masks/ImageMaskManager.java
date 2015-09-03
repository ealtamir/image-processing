package ar.com.itba.image_actions.masks;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 03.09.15.
 */
public class ImageMaskManager {

    public static void applyMeanMask(QuickDrawPanel quickDrawPanel, MainWindow window) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(window);
                quickDrawPanel.setParameterizedActionWindow(new MeanMaskParameterizedAction(quickDrawPanel));
            }
        });
    }


    public static void applyMedianMask(QuickDrawPanel quickDrawPanel, MainWindow window) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(window);
                quickDrawPanel.setParameterizedActionWindow(new MedianMaskParameterizedAction(quickDrawPanel));
            }
        });
    }

    public static void applyGaussianMask(QuickDrawPanel quickDrawPanel, MainWindow window) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(window);
                quickDrawPanel.setParameterizedActionWindow(new GaussianMaskParameterizedAction(quickDrawPanel));
            }
        });
    }

    public static void applyHighPassMask(QuickDrawPanel quickDrawPanel, MainWindow window) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(window);
                quickDrawPanel.setParameterizedActionWindow(new HighPassMaskParameterizedAction(quickDrawPanel));
            }
        });

    }
}
