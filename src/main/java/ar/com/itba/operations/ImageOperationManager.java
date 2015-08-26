package ar.com.itba.operations;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.action.OpenFileAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.utils.ImageFileTools;

import java.awt.image.BufferedImage;

public class ImageOperationManager {

    public static void performAddition(QuickDrawPanel quickDrawPanel, MainWindow window) {
        BufferedImage first = quickDrawPanel.image();
        BufferedImage second = OpenFileAction.getBufferedImageFromFile(window);
        if (second == null) {
            return;
        }
        BufferedImage result = Operators.imageAddition(first, second);
        CopyRightAction.peformCopyRightAction(window);
    }

    public static void performSubstraction(QuickDrawPanel quickDrawPanel) {

    }

    public static void performMultiplication(QuickDrawPanel quickDrawPanel) {

    }
}
