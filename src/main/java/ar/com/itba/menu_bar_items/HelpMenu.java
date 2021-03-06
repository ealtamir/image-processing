package ar.com.itba.menu_bar_items;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * Created by Enzo on 25.08.15.
 */
@SuppressWarnings("serial")
public class HelpMenu extends JMenu {

	public HelpMenu(String name, Component parent) {
		super(name);
		ImageIcon iconAbout = new ImageIcon(new ImageIcon("menuImages/about.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));
		JMenuItem aboutMenuItem = new JMenuItem("About", iconAbout);
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(parent, "Desarrollado por: \n\n Altamiranda, Enzo \n Elli, Federico", "About", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		add(aboutMenuItem);
	}
}
