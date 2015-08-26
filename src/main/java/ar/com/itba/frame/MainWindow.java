package ar.com.itba.frame;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;

import ar.com.itba.menu_bar_items.FileMenu;
import ar.com.itba.menu_bar_items.HelpMenu;
import ar.com.itba.menu_bar_items.MenuEventsListener;
import ar.com.itba.menu_bar_items.ToolsMenu;
import ar.com.itba.panel.QuickDrawPanel;
import javassist.tools.rmi.ObjectNotFoundException;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private QuickDrawPanel leftQuickDrawPanel;
	private QuickDrawPanel rightQuickDrawPanel;
	private JLabel mousePosLabel;
	private MenuEventsListener menuListener;

	public MainWindow() {
		initUI();
	}

	private void initUI() {
		createLeftQuickDrawPanel();
		createRightQuickDrawPanel();
		createWindowElements();
		setWindowsConfiguration();
	}

	private void setWindowsConfiguration() {
		setTitle("Image manipulator");
		setSize(600, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createWindowElements() {
		if (leftQuickDrawPanel != null) {
			createMenuBar();
		} else {
			throw new RuntimeException("Quick draw panel must be initialized.");
		}
	}

	private void createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		createFileMenu(menubar);
		createToolsMenu(menubar);
		createHelpMenu(menubar);
		setJMenuBar(menubar);
	}

	private void createToolsMenu(JMenuBar menubar) {
		menubar.add(new ToolsMenu("Tools", this));
	}

	private void createHelpMenu(JMenuBar menubar) {
		menubar.add(new HelpMenu("Help", this));
	}

	private void createFileMenu(JMenuBar menubar) {
		menubar.add(new FileMenu("File", this));
	}

	public void createLeftQuickDrawPanel() {
		leftQuickDrawPanel = new QuickDrawPanel();
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.add(new JScrollPane(leftQuickDrawPanel), "Center");
		contentPane.setVisible(true);
	}

	public void createRightQuickDrawPanel() {
		rightQuickDrawPanel = new QuickDrawPanel();
		Container contentPane = getContentPane();
		contentPane.add(new JScrollPane(rightQuickDrawPanel), "Center");
	}

	public void updateLeftQuickDrawPanel(BufferedImage image) {
		activateToolsMenuItem();
		leftQuickDrawPanel.image(image);
		this.pack();
	}

	public void updateRightQuickDrawPanel(BufferedImage image) {
		activateToolsMenuItem();
		rightQuickDrawPanel.image(image);
		this.pack();
	}

	private void activateToolsMenuItem() {
		// getMenuBar().getMenu(1).setEnabled(true);
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

	public QuickDrawPanel getLeftQuickDrawPanel() {
		return leftQuickDrawPanel;
	}

	public QuickDrawPanel getRightQuickDrawPanel() {
		return rightQuickDrawPanel;
	}

	public MenuEventsListener fetchMenuEventsListener() {
		if (menuListener == null) {
			menuListener = new MenuEventsListener(leftQuickDrawPanel, this);
		}
		return menuListener;
	}
}
