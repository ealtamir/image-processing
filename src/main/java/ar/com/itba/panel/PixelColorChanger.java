package ar.com.itba.panel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by Enzo on 12.08.15.
 */
public class PixelColorChanger extends JPanel implements ChangeListener {

	private QuickDrawPanel parent;

	final private int MIN_COLOR_VAL = 0;
	final private int MAX_COLOR_VAL = 255;
	private final int SLIDER_COUNT = 3;

	// r, g, b
	private JSlider[] colors = new JSlider[3];
	private JLabel[] colorLabels = new JLabel[3];

	private JLabel pixelLabel = new JLabel("-");
	private Point pixel = null;
	private JPanel[] sliderPanels;
	private JPanel sliders;
	private String[] rgb = {"R", "G", "B"};
	private int[] lastValues = new int[3];

	public PixelColorChanger(QuickDrawPanel parent) {
		this.parent = parent;
		createSliders();
		setElementsLayout();
	}

	public void setElementsVisibility(boolean visibility) {
		this.setVisible(visibility);
		for (int i = 0; i < SLIDER_COUNT; i++) {
			sliderPanels[i].setVisible(visibility);
			colors[i].setVisible(visibility);
			colorLabels[i].setVisible(visibility);
		}
		pixelLabel.setVisible(visibility);
	}

	private void setElementsLayout() {
		for (int i = 0; i < 3; i++) {
			sliderPanels[i].setLayout(new BoxLayout(sliderPanels[i], BoxLayout.Y_AXIS));
		}
		sliders.setLayout(new BoxLayout(sliders, BoxLayout.X_AXIS));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}

	private void createSliders() {
		sliderPanels = new JPanel[3];
		sliders = new JPanel();

		for (int i = 0; i < 3; i++) {
			sliderPanels[i] = new JPanel();
			colors[i] = new JSlider(JSlider.VERTICAL, MIN_COLOR_VAL, MAX_COLOR_VAL, 0);
			colors[i].setPaintTicks(true);
			colors[i].setAlignmentX(Component.CENTER_ALIGNMENT);

			// Registration of listener
			colors[i].addChangeListener(this);

			colorLabels[i] = new JLabel("-");
			colorLabels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
			sliderPanels[i].add(colors[i]);
			sliderPanels[i].add(colorLabels[i]);
			sliders.add(sliderPanels[i]);
		}
		pixelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(pixelLabel);
		add(sliders);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		int slider_index = getSliderIndex(slider);

		if (slider_index != -1 && pixel != null) {
			processSliderChange(slider_index);
		}
	}

	private void processSliderChange(int slider_index) {
		if (sliderChangedFromUserInteraction(slider_index)) {
			updateImageInfo(slider_index);
		}
	}

	private boolean sliderChangedFromUserInteraction(int slider_index) {
		return lastValues[slider_index] != colors[slider_index].getValue();
	}

	private void updateImageInfo(int slider_index) {
		JSlider slider = colors[slider_index];
		JLabel label = colorLabels[slider_index];
		String newText = rgb[slider_index] + " : " + String.valueOf(slider.getValue());
		label.setText(newText);
		int r = colors[0].getValue();
		int g = colors[1].getValue();
		int b = colors[2].getValue();
		updateSlidersLastValues(r, g, b);
		parent.changePixelColor(pixel, r, g, b);
	}

	private void updateSlidersLastValues(int r, int g, int b) {
		lastValues[0] = r;
		lastValues[1] = g;
		lastValues[2] = b;
	}

	private int getSliderIndex(JSlider slider) {
		for (int i = 0; i < colors.length; i++) {
			if (slider.equals(colors[i]))
				return i;
		}
		return -1;
	}

	public void setPixelValues(Point point, int r, int g, int b) {
		pixel = point;

		updateSlidersLastValues(r, g, b);

		colors[0].setValue(r);
		colorLabels[0].setText("R:" + String.valueOf(r));

		colors[1].setValue(g);
		colorLabels[1].setText("G:" + String.valueOf(g));

		colors[2].setValue(b);
		colorLabels[2].setText("B:" + String.valueOf(b));

		pixelLabel.setText("Pixel: (" + (int) point.getX() + ", " + (int) point.getY() + ")");
	}
}
