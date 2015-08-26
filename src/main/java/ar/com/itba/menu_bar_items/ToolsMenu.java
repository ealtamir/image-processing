package ar.com.itba.menu_bar_items;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import ar.com.itba.action.CopyRightAction;

/**
 * Created by Enzo on 25.08.15.
 */
@SuppressWarnings("serial")
public class ToolsMenu extends JMenu {

	JFrame parent;

	public ToolsMenu(String name, JFrame parent) {
		super(name);
		this.parent = parent;
		createOperationsMenuSet();
		createHistogramMenuSet();
		JMenuItem copyRightMenu = new JMenuItem("Copy left to right frame");
		copyRightMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.KEY_LOCATION_RIGHT, ActionEvent.CTRL_MASK));
		copyRightMenu.addActionListener(new CopyRightAction(parent));
		add(copyRightMenu);
	}

	private void createHistogramMenuSet() {
		JMenu histogram = new JMenu("Histogram");
		add(histogram);
	}

	private void createOperationsMenuSet() {
		JMenu operations = new JMenu("Operations");
		add(operations);

		JMenuItem adddition = new JMenuItem("Addition");
		JMenuItem substraction = new JMenuItem("Substraction");
		JMenuItem multiplication = new JMenuItem("Multiplication");

		operations.add(adddition);
		operations.add(substraction);
		operations.add(multiplication);
	}
}
