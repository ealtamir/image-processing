package ar.com.itba.menu_bar_items;

import ar.com.itba.action.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Enzo on 25.08.15.
 */
@SuppressWarnings("serial")
public class FileMenu extends JMenu {

	public FileMenu(String name, JFrame parent) {
		super(name);
		ImageIcon iconNew = new ImageIcon(new ImageIcon("menuImages/new.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));
		ImageIcon iconOpen = new ImageIcon(new ImageIcon("menuImages/open.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));
		ImageIcon iconSave = new ImageIcon(new ImageIcon("menuImages/save.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));
		ImageIcon iconExit = new ImageIcon(new ImageIcon("menuImages/exit.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));

		setMnemonic(KeyEvent.VK_F);

		JMenu newMenu = new JMenu(new MenuItemAction("New...", iconNew, KeyEvent.VK_N));

		JMenuItem blankMenuItem = new JMenuItem("Blank");
		blankMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		newMenu.add(blankMenuItem);

		JMenuItem circleMenuItem = new JMenuItem("Circle");
		circleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		circleMenuItem.addActionListener(new CreateCircleAction(parent));
		newMenu.add(circleMenuItem);

		JMenuItem squareMenuItem = new JMenuItem("Square");
		squareMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		squareMenuItem.addActionListener(new CreateSquareAction(parent));
		newMenu.add(squareMenuItem);

		JMenuItem grayscaleGradientMenuItem = new JMenuItem("Grayscale Gradient");
		grayscaleGradientMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, ActionEvent.CTRL_MASK));
		grayscaleGradientMenuItem.addActionListener(new CreateGradientAction(parent, Color.black, Color.white));
		newMenu.add(grayscaleGradientMenuItem);

		JMenuItem colorGradientMenuItem = new JMenuItem("Color Gradient");
		colorGradientMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, ActionEvent.CTRL_MASK));
		colorGradientMenuItem.addActionListener(new CreateGradientAction(parent, Color.red, Color.blue));
		newMenu.add(colorGradientMenuItem);

		JMenuItem openMenuItem = new JMenuItem(new MenuItemAction("Open", iconOpen, KeyEvent.VK_O));
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openMenuItem.addActionListener(new OpenFileAction(parent));

		JMenuItem saveMenuItem = new JMenuItem(new MenuItemAction("Save", iconSave, KeyEvent.VK_S));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.addActionListener(new SaveFileAction(parent));

		JMenuItem exitMenuItem = new JMenuItem("Exit", iconExit);
		exitMenuItem.setMnemonic(KeyEvent.VK_E);
		exitMenuItem.setToolTipText("Exit application");
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		add(newMenu);
		add(openMenuItem);
		add(saveMenuItem);
		addSeparator();
		add(exitMenuItem);
	}
}
