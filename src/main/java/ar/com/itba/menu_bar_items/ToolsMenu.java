package ar.com.itba.menu_bar_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import ar.com.itba.action.AnisotropicDifussionAction;
import ar.com.itba.action.CopyRightAction;
import ar.com.itba.action.EnchanceContrastAction;
import ar.com.itba.action.ExponentialNoiseAction;
import ar.com.itba.action.GaussianNoiseAction;
import ar.com.itba.action.ModifyGammaAction;
import ar.com.itba.action.RayleighNoiseAction;
import ar.com.itba.action.SaltAndPeperNoiseAction;
import ar.com.itba.frame.MainWindow;

/**
 * Created by Enzo on 25.08.15.
 */
@SuppressWarnings("serial")
public class ToolsMenu extends JMenu {

	// Edge Detection
	static public final String PREWITT_HORIZONTAL = "Horizontal";
	static public final String PREWITT_VERTICAL = "Vertical";
	public static final String SOBEL_HORIZONTAL = "Horizontal";
	public static final String SOBEL_VERTICAL = "Vertical";

	// Thresholding
	public static final String OTSU_THRESHOLD = "Otsu";
	public static final String GLOBAL_THRESHOLD = "Global";

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
	public static final String LAPLACIAN = "Laplacian";
	public static final String LAPLACIAN_SLOPE = "Laplacian Slope";
	public static final String LAPLACIAN_GAUSSIAN = "Laplacian Gaussian";

	JFrame parent;

	public ToolsMenu(String name, JFrame parent) {
		super(name);
		this.parent = parent;
		MenuEventsListener listener = ((MainWindow) parent).fetchMenuEventsListener();
		populateMenu(listener);
	}

	private void populateMenu(MenuEventsListener listener) {
		createOperationsMenuSet(listener);
		createNoiseMenuSet(listener);
		createHistogramMenuSet(listener);
		createTransformationsMenuSet(listener);
		createMasksMenuSet(listener);
		createEdgeDetectionSet(listener);
		createThresholdSet(listener);
		addSeparator();
		createMiscMenuSet(listener);
	}

	private void createThresholdSet(MenuEventsListener listener) {
		JMenu threshold = new JMenu("Umbralización");
		add(threshold);

		JMenuItem global = new JMenuItem(GLOBAL_THRESHOLD);
		threshold.add(global);
		global.addActionListener(listener);

		JMenuItem otsu = new JMenuItem(OTSU_THRESHOLD);
		threshold.add(otsu);
		otsu.addActionListener(listener);

	}

	private void createEdgeDetectionSet(MenuEventsListener listener) {
		JMenu edge = new JMenu("Edge Detection");
		add(edge);

		JMenu prewitt = new JMenu("Prewitt");
		edge.add(prewitt);
		JMenuItem horizontal = new JMenuItem(PREWITT_HORIZONTAL);
		prewitt.add(horizontal);
		horizontal.addActionListener(listener);
		JMenuItem vertical = new JMenuItem(PREWITT_VERTICAL);
		prewitt.add(vertical);
		vertical.addActionListener(listener);

		JMenu sobel = new JMenu("Sobel");
		edge.add(sobel);
		JMenuItem sobel_horizontal = new JMenuItem(SOBEL_HORIZONTAL);
		sobel.add(sobel_horizontal);
		sobel_horizontal.addActionListener(listener);
		JMenuItem sobel_vertical = new JMenuItem(SOBEL_VERTICAL);
		sobel.add(sobel_vertical);
		sobel_vertical.addActionListener(listener);

		JMenu second_deriv = new JMenu("Segunda Derivada");
		edge.add(second_deriv);
		JMenuItem laplacian = new JMenuItem(LAPLACIAN);
		second_deriv.add(laplacian);
		laplacian.addActionListener(listener);
		JMenuItem laplacian_slope = new JMenuItem(LAPLACIAN_SLOPE);
		second_deriv.add(laplacian_slope);
		laplacian_slope.addActionListener(listener);
		JMenuItem laplacian_gaussian = new JMenuItem(LAPLACIAN_GAUSSIAN);
		second_deriv.add(laplacian_gaussian);
		laplacian_gaussian.addActionListener(listener);
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

	private void createNoiseMenuSet(ActionListener listener) {
		JMenu noise = new JMenu("Noise");
		add(noise);

		JMenu additiveNoise = new JMenu("Additive Noise");
		noise.add(additiveNoise);

		JMenu multiplicativeNoise = new JMenu("Multiplicative Noise");
		noise.add(multiplicativeNoise);

		JMenuItem saltAndPepperNoiseMenuItem = new JMenuItem("Salt and Pepper...");
		saltAndPepperNoiseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		saltAndPepperNoiseMenuItem.addActionListener(new SaltAndPeperNoiseAction(parent));
		noise.add(saltAndPepperNoiseMenuItem);

		JMenuItem gaussianNoiseMenuItem = new JMenuItem("Gaussian...");
		gaussianNoiseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		gaussianNoiseMenuItem.addActionListener(new GaussianNoiseAction(parent));
		additiveNoise.add(gaussianNoiseMenuItem);

		JMenuItem exponentialNoiseMenuItem = new JMenuItem("Exponential...");
		exponentialNoiseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		exponentialNoiseMenuItem.addActionListener(new ExponentialNoiseAction(parent));
		multiplicativeNoise.add(exponentialNoiseMenuItem);

		JMenuItem rayleighNoiseMenuItem = new JMenuItem("Rayleigh...");
		rayleighNoiseMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		rayleighNoiseMenuItem.addActionListener(new RayleighNoiseAction(parent));
		multiplicativeNoise.add(rayleighNoiseMenuItem);
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

		JMenuItem modifyGammaMenuItem = new JMenuItem("Modify Gamma...");
		modifyGammaMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		modifyGammaMenuItem.addActionListener(new ModifyGammaAction(parent));
		add(modifyGammaMenuItem);

		JMenuItem anisotropicMenuItem = new JMenuItem("Anisotropic Difussion...");
		anisotropicMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		anisotropicMenuItem.addActionListener(new AnisotropicDifussionAction(parent));
		add(anisotropicMenuItem);

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
