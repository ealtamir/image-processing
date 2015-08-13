package ar.com.itba.frame;

import ar.com.itba.panel.PixelColorChanger;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Enzo on 12.08.15.
 */
public class ImageOptionsWindow extends JFrame {

    private Component owner;
    private JPanel panel;
    private JLabel pointerLabel;
    private PixelColorChanger pixelColorChanger;

    public ImageOptionsWindow() {
        createWindowContents();
        setFrameConfiguration();
        setVisible(true);
        pixelColorChanger.setElementsVisibility(true);
    }

    private void createWindowContents() {
        panel = createWindowPanel();
        pointerLabel = createPointerPosLabel();
        pixelColorChanger = new PixelColorChanger(this);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
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
        setLocationRelativeTo(owner);
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
}
