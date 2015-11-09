package ar.com.itba.image_actions.figure_detection;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.utils.CustomBufferedImage;
import ar.com.itba.utils.ImageFileTools;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Enzo on 14.10.15.
 */
public class FigureDetectionManager {
    public static final String filePath = "/Users/Enzo/ITBA/ATI/TP_ATI/resources/Imagenes/img_video";

    public static void applyHoughCircleDetection(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage markedShapesImg = HoughFigureDetection.detectCircle(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(markedShapesImg);
    }

    public static void applyHoughLineDetection(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        BufferedImage markedShapesImg = HoughFigureDetection.detectLine(quickDrawPanel.image());
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(markedShapesImg);
    }

    public static void applyContourDetection(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        Rectangle selectedContour = quickDrawPanel.getDrawnRectangle();
        CustomBufferedImage customImg = (CustomBufferedImage) quickDrawPanel.image();

        if (selectedContour == null)
           return;
        System.out.println("Contour identified");
        int[][] phi = ContourDetection.getPhiFunction(customImg, selectedContour);
        BufferedImage img = ContourDetection.detectContour(quickDrawPanel.image(), phi);
        CopyRightAction.peformCopyRightAction(mainWindow);
        quickDrawPanel.modifyCurrentImage(img);
    }

    public static void startVideoContourDetection(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        File selectedFolder = selectFolder(mainWindow);
        if (selectedFolder == null) {
            return;
        }
        File[] files = selectedFolder.listFiles();
        if (files.length == 0) {
            System.out.println("No files in the chosen folder.");
            return;
        }
        try {
            BufferedImage img = ImageFileTools.loadImage(files[0], mainWindow);
            mainWindow.updateLeftQuickDrawPanel(img);
            quickDrawPanel.setContourDetectionFolder(selectedFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void executeVideoContourDetection(QuickDrawPanel quickDrawPanel, MainWindow mainWindow) {
        File selectedFolder = quickDrawPanel.getContourDetectionFolder();
        Rectangle initialContour = quickDrawPanel.getDrawnRectangle();
        if (selectedFolder == null || initialContour == null) {
            return;
        }
        ArrayList<CustomBufferedImage> video = new ArrayList<CustomBufferedImage>();
        for (File file : selectedFolder.listFiles()) {
            try {
                video.add((CustomBufferedImage) ImageFileTools.loadImage(file, mainWindow));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int[][] phi = ContourDetection.getPhiFunction(video.get(0), initialContour);
        System.out.println("Starting image creation");
        for (int i = 0; i < video.size(); i++) {
            CustomBufferedImage img = video.get(i);
            BufferedImage newImg;
            newImg = ContourDetection.detectContour(img, phi);
            try {
                ImageIO.write(newImg, "PNG", new File(filePath + "/" + String.valueOf(i)));
            } catch (IOException e) {
                System.out.println("Failed to save image");
                e.printStackTrace();
            }
        }
        System.out.println("Processing finished...");
    }

    private static File selectFolder(MainWindow window) {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File(".")); // start at application current directory
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showSaveDialog(window);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File yourFolder = fc.getSelectedFile();
            return yourFolder;
        }
        return null;
    }
}
