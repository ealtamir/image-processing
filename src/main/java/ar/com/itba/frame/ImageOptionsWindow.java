package ar.com.itba.frame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Enzo on 12.08.15.
 */
public class ImageOptionsWindow extends JFrame {

    private Component owner;
    private JPanel panel;
    private JLabel pointerLabel;

    public ImageOptionsWindow() {
        createWindowContents();
        setFrameConfiguration();
    }

    private void createWindowContents() {
        panel = new JPanel();
        pointerLabel = new JLabel();
        add(panel);
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        GroupLayout.ParallelGroup parallelGroup = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
        parallelGroup.addComponent(pointerLabel);
    }

    private void setFrameConfiguration() {
        setSize(150, 60);
        setResizable(false);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setVisible(true);
    }

    public void setPointerLabelValues(Point point) {
        if (point != null) {
            String msg = "X: " + String.valueOf(point.getX());
            msg += ", Y: " + String.valueOf(point.getY());
            pointerLabel.setText(msg);
        } else {
            pointerLabel.setText("X: -, Y: -");
        }
    }
}
