package ar.com.itba.frame;

import ar.com.itba.panel.PixelColorChanger;
import ar.com.itba.panel.QuickDrawPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


public class ImageOptionsWindow extends JFrame {

    private QuickDrawPanel owner;
    private JPanel panel;
    private JLabel pointerLabel;
    private PixelColorChanger pixelColorChanger;
    private BufferedImage image;

    public ImageOptionsWindow(QuickDrawPanel owner, BufferedImage image) {
        this.owner = owner;
        this.image = image;
        createWindowContents();
        setFrameConfiguration();
        setVisible(true);
        pixelColorChanger.setElementsVisibility(true);
    }

    private void createWindowContents() {
        panel = createWindowPanel();
        pointerLabel = createPointerPosLabel();
        pixelColorChanger = new PixelColorChanger(owner);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        pointerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(pointerLabel);
        panel.add(pixelColorChanger);
    }

    private JLabel createPointerPosLabel() {
        return new JLabel();
    }

    private JPanel createWindowPanel() {
        JPanel newPanel = new JPanel();
        add(newPanel);
        return newPanel;
    }

    private void setFrameConfiguration() {
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void setPointerLabelValues(Point point) {
        if (point != null) {
            String msg = "X: " + String.valueOf((int) point.getX());
            msg += ", Y: " + String.valueOf((int) point.getY());
            pointerLabel.setText(msg);
        } else {
            pointerLabel.setText("X: -, Y: -");
        }
    }

    public void updateRGBSliders(MouseEvent e) {
        int rgbPoint = image.getRGB(e.getX(), e.getY());
        int R = (rgbPoint & 0x00FF0000) >>> 16;
        int G = (rgbPoint & 0x0000FF00) >>> 8;
        int B = (rgbPoint & 0x000000FF);
        pixelColorChanger.setPixelValues(e.getPoint(), R, G, B);

    }
}
