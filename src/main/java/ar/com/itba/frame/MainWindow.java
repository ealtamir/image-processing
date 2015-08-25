package ar.com.itba.frame;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import ar.com.itba.menu_bar_items.FileMenu;
import ar.com.itba.menu_bar_items.HelpMenu;
import ar.com.itba.menu_bar_items.ToolsMenu;
import ar.com.itba.panel.QuickDrawPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private QuickDrawPanel quickDrawPanel;
	private JLabel mousePosLabel;

	public MainWindow() {
		initUI();
	}

	private void initUI() {
		createWindowElements();
		setWindowsConfiguration();
	}

	private void setWindowsConfiguration() {
		setTitle("Image manipulator");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createWindowElements() {
		createMenuBar();
		createQuickDrawPanel();
	}

	private void createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		createFileMenu(menubar);
		createToolsMenu(menubar);
		createHelpMenu(menubar);
		setJMenuBar(menubar);
	}

	private void createToolsMenu(JMenuBar menubar) {
		menubar.add(new ToolsMenu("Tools"));
	}

	private void createHelpMenu(JMenuBar menubar) {
		menubar.add(new HelpMenu("Help", this));
	}

	private void createFileMenu(JMenuBar menubar) {
		menubar.add(new FileMenu("File", this));
	}

	private void createQuickDrawPanel() {
		quickDrawPanel = new QuickDrawPanel();
		Container contentPane = getContentPane();
		contentPane.add(new JScrollPane(quickDrawPanel), "Center");
	}

	public void updateQuickDrawPanel(BufferedImage image) {
		activateToolsMenuItem();
		quickDrawPanel.image(image);
	}

	private void activateToolsMenuItem() {
//		getMenuBar().getMenu(1).setEnabled(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainWindow window = new MainWindow();
				window.setVisible(true);
			}
		});
	}

	public QuickDrawPanel getQuickDrawPanel() {
		return quickDrawPanel;
	}
}
