package ar.com.itba.image_actions.operations;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.action.OpenFileAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ImageOperationManager {

    public static void performAddition(QuickDrawPanel quickDrawPanel, MainWindow window) {
        BufferedImage first = quickDrawPanel.image();
        BufferedImage second = OpenFileAction.getBufferedImageFromFile(window);
        if (second == null) {
            return;
        }
        CopyRightAction.peformCopyRightAction(window);
        BufferedImage result = Operators.imageAddition(first, second);
        window.getLeftQuickDrawPanel().image(result);
    }

    public static void performSubstraction(QuickDrawPanel quickDrawPanel, MainWindow window) {
        BufferedImage first = quickDrawPanel.image();
        BufferedImage second = OpenFileAction.getBufferedImageFromFile(window);
        if (second == null) {
            return;
        }
        CopyRightAction.peformCopyRightAction(window);
        BufferedImage result = Operators.imageSubstraction(first, second);
        window.getLeftQuickDrawPanel().image(result);
    }

    public static void performMultiplication(QuickDrawPanel quickDrawPanel, MainWindow window) {
        BufferedImage first = quickDrawPanel.image();
        BufferedImage second = OpenFileAction.getBufferedImageFromFile(window);
        if (second == null) {
            return;
        }
        CopyRightAction.peformCopyRightAction(window);
        BufferedImage result = Operators.imageMultiplication(first, second);
        window.getLeftQuickDrawPanel().image(result);
    }

    public static void imageScalarMult(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CopyRightAction.peformCopyRightAction(mainWindow);
                quickDrawPanel.setParameterizedActionWindow(new ScalarMultOperation(quickDrawPanel));
            }
        });

    }
}
