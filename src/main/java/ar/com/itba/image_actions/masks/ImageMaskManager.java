package ar.com.itba.image_actions.masks;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;
import java.awt.image.BufferedImage;

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
//
//        JPanel optionsPanel = new JPanel();
//        JTextField sigma = new JTextField(3);
//        optionsPanel.add(new JLabel("Sigma: "));
//        optionsPanel.add(sigma);
//
//        String msg = "Elige el valor de sigma:";
//        int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
//        if (result == JOptionPane.OK_OPTION) {
//            double sigmaVal = Double.valueOf(sigma.getText());
//            CopyRightAction.peformCopyRightAction(window);
//            BufferedImage newImg = Masks.applyGaussianMask(quickDrawPanel.image(), sigmaVal);
//            quickDrawPanel.modifyCurrentImage(newImg);
//        }

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
