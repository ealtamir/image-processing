import javax.swing.SwingUtilities;

import ar.com.itba.frame.StartWindow;

public class main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new StartWindow();
			}
		});
	}
}
