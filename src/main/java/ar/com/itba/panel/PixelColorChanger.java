package ar.com.itba.panel;

import ar.com.itba.frame.ImageOptionsWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PixelColorChanger extends JPanel implements ChangeListener, ActionListener {

	private enum ColorMode {
		RGB, HSV
	};

	private QuickDrawPanel parent;

	final private int MIN_COLOR_VAL = 0;
	final private int MAX_COLOR_VAL = 255;
	final private int MAX_HUE_COLOR_VAL = 360;
	final private int MAX_SB_COLOR_VAL = 100;
	private final int SLIDER_COUNT = 3;

	// r, g, b
	private JSlider[] colors = new JSlider[3];
	private JLabel[] colorLabels = new JLabel[3];
	private JButton colorModeButton = new JButton("Cambiar a HSV");
	private JLabel pixelLabel = new JLabel("-");
	private Point pixel = null;
	private JPanel[] sliderPanels;
	private JPanel sliders;
	private String[] rgb = {"R", "G", "B"};
	private int[] lastValues = new int[3];

	private ColorMode colorMode = ColorMode.RGB;

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
		colorModeButton.setVisible(true);
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
			colors[i].setPaintTicks(false);
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
		colorModeButton.setActionCommand("SWITCH_COLOR_MODE");
		colorModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		colorModeButton.addActionListener(this);
		add(pixelLabel);
		add(sliders);
		add(colorModeButton);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider slider = (JSlider) e.getSource();
		int slider_index = getSliderIndex(slider);

		if (slider_index != -1 && pixel != null) {
			processSliderChange(slider_index);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (pixel != null && "SWITCH_COLOR_MODE".equals(e.getActionCommand())) {
			if (colorMode == ColorMode.RGB) {
				switchToHSVColorMode();
			} else {
				switchToRGBColorMode();
			}
		}

	}

	private void enableSliderEvents() {
		for (int slider = 0; slider < colors.length; slider++) {
			colors[slider].addChangeListener(this);
		}
	}

	private void disableSliderEvents() {
		for (int slider = 0; slider < colors.length; slider++) {
			colors[slider].removeChangeListener(this);
		}
	}

	private void switchToRGBColorMode() {
		colorMode = ColorMode.RGB;
		colorModeButton.setText("Cambiar a HSV");
		colors[0].setMaximum(MAX_COLOR_VAL);
		colors[1].setMaximum(MAX_COLOR_VAL);
		colors[2].setMaximum(MAX_COLOR_VAL);
		setPixelValues(pixel, lastValues[0], lastValues[1], lastValues[2]);
	}

	private void switchToHSVColorMode() {
		colorMode = ColorMode.HSV;
		colorModeButton.setText("Cambiar a RGB");
		colors[0].setMaximum(MAX_SB_COLOR_VAL);
		colors[1].setMaximum(MAX_SB_COLOR_VAL);
		colors[2].setMaximum(MAX_SB_COLOR_VAL);
		setPixelValues(pixel, lastValues[0], lastValues[1], lastValues[2]);
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
		if (colorMode == ColorMode.RGB) {
			updateRGBSlidersInfo(slider_index);
		} else {
			updateHSVSlidersInfo(slider_index);
		}
	}

	private void updateHSVSlidersInfo(int slider_index) {
		int[] rgb = new int[3];
		int h, s, v, r, g, b;
		JSlider slider = colors[slider_index];
		updateSliderLabel(slider_index, slider.getValue());
		h = colors[0].getValue();
		s = colors[1].getValue();
		v = colors[2].getValue();
		rgb = convertToRGB(h, s, v);
		r = rgb[0];
		g = rgb[1];
		b = rgb[2];
		updateSlidersLastValues(r, g, b);
		parent.changePixelColor(pixel, r, g, b);
	}

	private void updateRGBSlidersInfo(int slider_index) {
		JSlider slider = colors[slider_index];
		updateSliderLabel(slider_index, slider.getValue());

		int r = colors[0].getValue();
		int g = colors[1].getValue();
		int b = colors[2].getValue();
		updateSlidersLastValues(r, g, b);
		parent.changePixelColor(pixel, r, g, b);
	}

	private void updateSliderLabel(int slider_index, int value) {
		JLabel label = colorLabels[slider_index];
		String color = rgb[slider_index];
		if (colorMode == ColorMode.HSV) {
			switch (color) {
				case "R": color = "H"; break;
				case "G": color = "S"; break;
				case "B": color = "V"; break;
			}
		}
		label.setText(color + ": " + String.valueOf(value));
	}


	private int[] convertToRGB(int h, int s, int v) {
		float fh = (float) h / MAX_SB_COLOR_VAL;
		float fs = (float) s / MAX_SB_COLOR_VAL;
		float fv = (float) v / MAX_SB_COLOR_VAL;
		int value = Color.HSBtoRGB(fh, fs, fv);
		int[] rgb = new int[3];
		rgb[0] = ImageOptionsWindow.getRed(value);
		rgb[1] = ImageOptionsWindow.getGreen(value);
		rgb[2] = ImageOptionsWindow.getBlue(value);
		return rgb;
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
		if (colorMode == ColorMode.RGB) {
			setRGBPixelValues(r, g, b);
		} else {
			setHSVPixelValues(r, g, b);
		}
		pixelLabel.setText("Pixel: (" + (int) point.getX() + ", " + (int) point.getY() + ")");
	}

	private void setHSVPixelValues(int r, int g, int b) {
		int[] hsv = convertToHSV(r, g, b);

		colors[0].setValue(hsv[0]);
		colorLabels[0].setText("H:" + String.valueOf(hsv[0]));

		colors[1].setValue(hsv[1]);
		colorLabels[1].setText("S:" + String.valueOf(hsv[1]));

		colors[2].setValue(hsv[2]);
		colorLabels[2].setText("V:" + String.valueOf(hsv[2]));
	}

	private int[] convertToHSV(int r, int g, int b) {
		float[] hsv = Color.RGBtoHSB(r, g, b, null);
		hsv[0] *= MAX_SB_COLOR_VAL;
		hsv[1] *= MAX_SB_COLOR_VAL;
		hsv[2] *= MAX_SB_COLOR_VAL;
		int[] result = new int[3];
		for (int i = 0; i < 3; i++) {
			result[i] = (int) hsv[i];
		}
		return result;
	}

	private void setRGBPixelValues(int r, int g, int b) {
		colors[0].setValue(r);
		colorLabels[0].setText("R:" + String.valueOf(r));

		colors[1].setValue(g);
		colorLabels[1].setText("G:" + String.valueOf(g));

		colors[2].setValue(b);
		colorLabels[2].setText("B:" + String.valueOf(b));
	}

	public void changeImage(QuickDrawPanel quickDrawPanel) {
		this.parent = quickDrawPanel;
	}

}
