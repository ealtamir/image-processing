package ar.com.itba.utils;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


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

    private static BufferedImage createBufferedImageFromFile(File file, Component component) throws IOException {
        String format = FilenameUtils.getExtension(file.getName()).toLowerCase();
        if (format.equals("raw")) {
            return loadImageFromRawFile(file, component);
        } else {
            return loadImageFromStandardFormatFile(file, component);
        }
    }

    private static BufferedImage loadImageFromStandardFormatFile(File file, Component component) throws IOException {
        BufferedImage img =  ImageIO.read(file);
        if (img == null) {
            tellUserImgFormatInvalid(component);
        }
        return img;
    }

    private static BufferedImage loadImageFromRawFile(File file, Component component) {
        int[] imgDimensions = askUserImgDimensions(component);
        return null;
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
        JOptionPane.showMessageDialog(component, msg,
                "Error de entrada de datos", JOptionPane.ERROR_MESSAGE);
    }

    private static void tellUserImgFormatInvalid(Component component) {
        String msg = "Falló la carga de la imagen por la siguiente razón: \n";
        msg += "El archivo seleccionado es inválido.";
        JOptionPane.showMessageDialog(component, msg,
                "Error al cargar la imagen", JOptionPane.ERROR_MESSAGE);
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
