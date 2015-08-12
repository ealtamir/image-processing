package ar.com.itba.frame;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.utils.ImageFileTools;

public class StandardImageManipulator extends JFrame {

    private final BufferedImage originalImage;

    public StandardImageManipulator() {
        originalImage = loadImage();
        add(new QuickDrawPanel(originalImage));

    }

    private BufferedImage loadImage() {
        try {
            return ImageFileTools.loadImage(this);
        } catch (IOException e) {
            tellUserImgLoadFailed(e);
        }
        return null;
    }

    private void closeFrame() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void tellUserImgLoadFailed(IOException e) {
        String msg = "Falló la carga de la imagen por la siguiente razón: \n";
        msg += e.getLocalizedMessage();
        JOptionPane.showMessageDialog(this, msg,
                "Error al cargar la imagen", JOptionPane.ERROR_MESSAGE);
    }
}
