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

	static public final String SHOW_HISTOGRAM = "Show";
	static public final String IMAGE_ADDITION = "Addition";
	static public final String IMAGE_SUBSTRACTION = "Substraction";
	static public final String IMAGE_MULTIPLICATION = "Multiplication";
	static public final String SHOW_IMAGE_OPTIONS = "Show Image Options";
	static public final String IMAGE_NEGATIVE = "Image Negative";
	static public final String SCALAR_MULTIPLICATION = "Scalar Multiply";

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
		addSeparator();
		createMiscMenuSet(listener);
	}

	private void createTransformationsMenuSet(MenuEventsListener listener) {
		JMenu transformations = new JMenu("Transformations");
		add(transformations);

		JMenuItem imageNegative = new JMenuItem(IMAGE_NEGATIVE);
		imageNegative.addActionListener(listener);
		transformations.add(imageNegative);

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
