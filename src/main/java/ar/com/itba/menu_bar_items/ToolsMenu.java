package ar.com.itba.menu_bar_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import ar.com.itba.action.CopyRightAction;
import ar.com.itba.action.EnchanceContrastAction;
import ar.com.itba.action.SaltAndPeperNoiseAction;
import ar.com.itba.frame.MainWindow;

/**
 * Created by Enzo on 25.08.15.
 */
@SuppressWarnings("serial")
public class ToolsMenu extends JMenu {

	// Histogram
	static public final String SHOW_HISTOGRAM = "Show";
	static public final String EQUALIZE = "Equalize";

	// Operations
	static public final String IMAGE_ADDITION = "Addition";
	static public final String IMAGE_SUBSTRACTION = "Substraction";
	static public final String IMAGE_MULTIPLICATION = "Multiplication";
	static public final String SCALAR_MULTIPLICATION = "Scalar multiply";

	// Transformations
	static public final String IMAGE_NEGATIVE = "Image negative";
	static public final String DYNAMIC_RANGE_COMPRESS = "Dynamic range compression";
	static public final String THRESHOLDING = "Thresholding";

	// Misc
	static public final String SHOW_IMAGE_OPTIONS = "Show image options";

	public static final String MEAN_MASK = "Mean Mask";
	public static final String MEDIAN_MASK = "Median Mask";
	public static final String GAUSSIAN_MASK = "Gaussian Mask";
	public static final String HIGH_PASS_MASK = "High Pass Mask";

	JFrame parent;

	public ToolsMenu(String name, JFrame parent) {
		super(name);
		this.parent = parent;
		MenuEventsListener listener = ((MainWindow) parent).fetchMenuEventsListener();
		populateMenu(listener);
	}

	private void populateMenu(MenuEventsListener listener) {
		createOperationsMenuSet(listener);
		createHistogramMenuSet(listener);
		createTransformationsMenuSet(listener);
		createMasksMenuSet(listener);
		addSeparator();
		createMiscMenuSet(listener);
	}

	private void createMasksMenuSet(MenuEventsListener listener) {
		JMenu masks = new JMenu("Masks");
		add(masks);

		JMenuItem meanMask = new JMenuItem(MEAN_MASK);
		meanMask.addActionListener(listener);
		masks.add(meanMask);

		JMenuItem medianMask = new JMenuItem(MEDIAN_MASK);
		medianMask.addActionListener(listener);
		masks.add(medianMask);

		JMenuItem gaussianMask = new JMenuItem(GAUSSIAN_MASK);
		gaussianMask.addActionListener(listener);
		masks.add(gaussianMask);

		JMenuItem highPassMask = new JMenuItem(HIGH_PASS_MASK);
		highPassMask.addActionListener(listener);
		masks.add(highPassMask);

	}

	private void createTransformationsMenuSet(MenuEventsListener listener) {
		JMenu transformations = new JMenu("Transformations");
		add(transformations);

		JMenuItem imageNegative = new JMenuItem(IMAGE_NEGATIVE);
		imageNegative.addActionListener(listener);
		transformations.add(imageNegative);

		JMenuItem dynamicRange = new JMenuItem(DYNAMIC_RANGE_COMPRESS);
		dynamicRange.addActionListener(listener);
		transformations.add(dynamicRange);

		JMenuItem threshold = new JMenuItem(THRESHOLDING);
		threshold.addActionListener(listener);
		transformations.add(threshold);
	}

	private void createMiscMenuSet(ActionListener listener) {
		JMenuItem copyRightMenuItem = new JMenuItem("Copy left to right frame");
		copyRightMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_KP_RIGHT, ActionEvent.CTRL_MASK));
		copyRightMenuItem.addActionListener(new CopyRightAction(parent));
		add(copyRightMenuItem);

		JMenuItem imageOptionsWindow = new JMenuItem(SHOW_IMAGE_OPTIONS);
		imageOptionsWindow.addActionListener(listener);
		add(imageOptionsWindow);

		JMenuItem enhanceContrastMenuItem = new JMenuItem("Enhance Contrast...");
		enhanceContrastMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, ActionEvent.CTRL_MASK));
		enhanceContrastMenuItem.addActionListener(new EnchanceContrastAction(parent));
		add(enhanceContrastMenuItem);

		JMenuItem saltAndPepperNoiseMenuItem = new JMenuItem("Generate Salt and Pepper Noise...");
		saltAndPepperNoiseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		saltAndPepperNoiseMenuItem.addActionListener(new SaltAndPeperNoiseAction(parent));
		add(saltAndPepperNoiseMenuItem);
	}

	private void createHistogramMenuSet(ActionListener listener) {
		JMenu histogram = new JMenu("Histogram");
		add(histogram);

		JMenuItem showHistogram = new JMenuItem(SHOW_HISTOGRAM);
		showHistogram.addActionListener(listener);
		histogram.add(showHistogram);

		JMenuItem equalize = new JMenuItem(EQUALIZE);
		equalize.addActionListener(listener);
		histogram.add(equalize);
	}

	private void createOperationsMenuSet(ActionListener listener) {
		JMenu operations = new JMenu("Operations");
		add(operations);

		JMenuItem addition = new JMenuItem(IMAGE_ADDITION);
		JMenuItem substraction = new JMenuItem(IMAGE_SUBSTRACTION);
		JMenuItem multiplication = new JMenuItem(IMAGE_MULTIPLICATION);
		JMenuItem scalarMultiplication = new JMenuItem(SCALAR_MULTIPLICATION);


		addition.addActionListener(listener);
		substraction.addActionListener(listener);
		multiplication.addActionListener(listener);
		scalarMultiplication.addActionListener(listener);

		operations.add(addition);
		operations.add(substraction);
		operations.add(multiplication);
		operations.add(scalarMultiplication);
	}
}
