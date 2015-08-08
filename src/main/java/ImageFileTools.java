import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Enzo on 08.08.15.
 */
public class ImageFileTools {

    public static BufferedImage loadImage(Component component) throws IOException {
        File file = selectFile(component);
        if (file != null) {
            return createBufferedImageFromFile(file);
        }
        return null;
    }

    private static BufferedImage createBufferedImageFromFile(File file) throws IOException {
        return ImageIO.read(file);
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        return fileChooser;
    }
}
