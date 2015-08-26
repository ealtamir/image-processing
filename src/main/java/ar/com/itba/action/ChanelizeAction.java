package ar.com.itba.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ChanelizeAction extends AbstractAction {

	private JFrame parent;

	public ChanelizeAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
