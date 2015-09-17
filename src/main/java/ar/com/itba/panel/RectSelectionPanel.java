package ar.com.itba.panel;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ar.com.itba.frame.ImageOptionsWindow;

public class RectSelectionPanel extends JPanel {

    private JLabel avgLabel;
    private JLabel areaLabel;
    private boolean elementsVisibility;

    public RectSelectionPanel(ImageOptionsWindow optionsPanel) {

        avgLabel = new JLabel("Avg: - ");
        areaLabel = new JLabel("P. Count: - ");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        avgLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        areaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(avgLabel);
        add(areaLabel);

    }

    public void setElementsVisibility(boolean elementsVisibility) {
        setVisible(elementsVisibility);
        avgLabel.setVisible(elementsVisibility);
        areaLabel.setVisible(elementsVisibility);

    }

    public void setAvgLabelValue(int r, int g, int b) {
        String txt = String.format("Avg: R(%d), G(%d), B(%d)", r, g, b);
        avgLabel.setText(txt);
    }

    public void setAreaLabelValue(int val) {
        areaLabel.setText("P. Count: " + String.valueOf(val));
    }
}
