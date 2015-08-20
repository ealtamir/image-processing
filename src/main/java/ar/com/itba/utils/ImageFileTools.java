package ar.com.itba.utils;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.*;
import java.security.InvalidParameterException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.io.FilenameUtils;


public class ImageFileTools {

    static private final int WIDTH = 0;
    static private final int HEIGHT = 1;

    public static BufferedImage loadImage(Component component) throws IOException {
        File file = selectFile(component);
        if (file == null) {
            return null;
        }
        return createBufferedImageFromFile(file, component);
    }

    public static BufferedImage loadImage(File file, Component parentComponent) throws IOException {
        if (file == null) {
            throw new InvalidParameterException("file object is invalid.");
        }
        return createBufferedImageFromFile(file, parentComponent);
    }

    private static BufferedImage createBufferedImageFromFile(File file, Component component) throws IOException {
        String format = FilenameUtils.getExtension(file.getName()).toLowerCase();
        if (format.equals("raw")) {
            return loadImageFromRawFile(file, component);
        } else {
            return loadImageFromStandardFormatFile(file, component);
        }
    }

    private static BufferedImage loadImageFromStandardFormatFile(File file, Component component) throws IOException {
        BufferedImage img = ImageIO.read(file);
        if (img == null) {
            tellUserImgFormatInvalid(component);
        }
        return img;
    }

    private static BufferedImage loadImageFromRawFile(File file, Component component) {
        int[] imgDimensions = askUserImgDimensions(component);
        if (imgDimensions != null) {
            byte[][] rawData = createRawDataMatrix(file, imgDimensions, component);
            return generateBufferedImageFromRawData(rawData, component);
        }
        return null;
    }

    private static BufferedImage generateBufferedImageFromRawData(byte[][] rawData, Component component) {
        BufferedImage image = new BufferedImage(rawData[0].length, rawData.length,
                BufferedImage.TYPE_INT_RGB);
        for (int h = 0; h < rawData.length; h++) {
            for (int w = 0; w < rawData[0].length; w++) {
                image.setRGB(w, h, byteToARGB(rawData[h][w]));
            }
        }
        return image;
    }

    private static int byteToARGB(byte color) {
        int r = (0x000000FF & color) << 16;
        int g = (0x000000FF & color) << 8;
        int b = (0x000000FF & color);
        return r | g | b;
    }

    private static byte[][] createRawDataMatrix(File file, int[] imgDimensions, Component component) {
        byte[][] dataMatrix = null;
        try {
            FileInputStream rawImageStream = new FileInputStream(file);
            dataMatrix = loadMatrixFromFile(rawImageStream, imgDimensions);
            rawImageStream.close();
        } catch (FileNotFoundException e) {
            tellUserFileNotFound(component);
        } catch (IOException e) {
            tellUserIOError(component);
        }
        return dataMatrix;
    }

    private static byte[][] loadMatrixFromFile(FileInputStream rawImageStream, int[] imgDimensions) throws IOException {
        byte[][] dataMatrix = new byte[imgDimensions[HEIGHT]][imgDimensions[WIDTH]];
        for (int h = 0; h < imgDimensions[HEIGHT]; h++) {
            for (int w = 0; w < imgDimensions[WIDTH]; w++) {
                dataMatrix[h][w] = (byte) rawImageStream.read();
            }
        }
        return dataMatrix;
    }

    private static void tellUserIOError(Component component) {
        String msg = "Falló la carga de la imagen por la siguiente razón: \n";
        msg += "Hubo un error de IO. Por favor, vuelve a intentarlo.";
        String title =  "Error al cargar la imagen";
        ErrorTools.showErrorMsg(title, msg, component);
    }


    private static void tellUserFileNotFound(Component component) {
        String msg = "Falló la carga de la imagen por la siguiente razón: \n";
        msg += "El archivo seleccionado no existe.";
        String title =  "Error al cargar la imagen";
        ErrorTools.showErrorMsg(title, msg, component);
    }

    private static int[] askUserImgDimensions(Component component) {
        int[] dimensions = new int[2];
        JTextField width = new JTextField(3);
        JTextField height = new JTextField(3);

        JPanel optionsPanel = new JPanel();
        optionsPanel.add(new JLabel("width: "));
        optionsPanel.add(width);
        optionsPanel.add(Box.createHorizontalStrut(15));
        optionsPanel.add(new JLabel("height: "));
        optionsPanel.add(height);

        String msg = "Indica cuáles son las dimensiones de la imagen RAW:";
        int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                dimensions[WIDTH] = Integer.valueOf(width.getText());
                dimensions[HEIGHT] = Integer.valueOf(height.getText());
                return dimensions;
            } catch (NumberFormatException e) {
                tellUserInvalidInput(component);
            }
        }
        return null;
    }

    private static void tellUserInvalidInput(Component component) {
        String msg = "Los datos ingresados son inválidos.";
        String title = "Error de entrada de datos";
        ErrorTools.showErrorMsg(title, msg, component);
    }

    private static void tellUserImgFormatInvalid(Component component) {
        String msg = "Falló la carga de la imagen por la siguiente razón: \n";
        msg += "El archivo seleccionado es inválido.";
        String title = "Error al cargar la imagen";
        ErrorTools.showErrorMsg(title, msg, component);
    }

    private static File selectFile(Component parentComponent) {
        JFileChooser fileChooser = createFileChooser();
        return makeFileSelection(parentComponent, fileChooser);
    }

    private static File makeFileSelection(Component parentComponent, JFileChooser fileChooser) {
        int choosenVal = fileChooser.showOpenDialog(parentComponent);
        if (choosenVal == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    private static JFileChooser createFileChooser() {
        JFileChooser fileChooser = new JFileChooser("/Users/Enzo/Dropbox/ITBA/ATI/Imagenes");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        return fileChooser;
    }
}
