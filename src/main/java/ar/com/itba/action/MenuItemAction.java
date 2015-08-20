package ar.com.itba.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class MenuItemAction extends AbstractAction {

	public MenuItemAction(String text, ImageIcon icon, Integer mnemonic) {
		super(text);
		putValue(SMALL_ICON, icon);
		putValue(MNEMONIC_KEY, mnemonic);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
	}
}
