package ar.com.itba.frame;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import ar.com.itba.action.MenuItemAction;
import ar.com.itba.action.OpenFileAction;
import ar.com.itba.action.SaveFileAction;
import ar.com.itba.panel.QuickDrawPanel;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private QuickDrawPanel quickDrawPanel;

	public MainWindow() {
		initUI();
	}

	private void initUI() {
		createMenuBar();
		createQuickDrawPanel();
		setTitle("Image manipulator");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		ImageIcon iconNew = new ImageIcon(new ImageIcon("menuImages/new.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));
		ImageIcon iconOpen = new ImageIcon(new ImageIcon("menuImages/open.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));
		ImageIcon iconSave = new ImageIcon(new ImageIcon("menuImages/save.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));
		ImageIcon iconExit = new ImageIcon(new ImageIcon("menuImages/exit.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));
		ImageIcon iconAbout = new ImageIcon(new ImageIcon("menuImages/about.png").getImage().getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING));
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenu newMenu = new JMenu(new MenuItemAction("New...", iconNew, KeyEvent.VK_N));
		JMenuItem blankMenuItem = new JMenuItem("Blank");
		blankMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		newMenu.add(blankMenuItem);
		JMenuItem circleMenuItem = new JMenuItem("Circle");
		circleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		newMenu.add(circleMenuItem);
		JMenuItem squareMenuItem = new JMenuItem("Square");
		squareMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		newMenu.add(squareMenuItem);
		JMenuItem openMenuItem = new JMenuItem(new MenuItemAction("Open", iconOpen, KeyEvent.VK_O));
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openMenuItem.addActionListener(new OpenFileAction(MainWindow.this));
		JMenuItem saveMenuItem = new JMenuItem(new MenuItemAction("Save", iconSave, KeyEvent.VK_S));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.addActionListener(new SaveFileAction(MainWindow.this));
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
		fileMenu.add(newMenu);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About", iconAbout);
		aboutMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(MainWindow.this, "Desarrollado por: \n\n Altamiranda, Enzo \n Elli, Federico", "About",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(aboutMenuItem);
		menubar.add(fileMenu);
		// menubar.add(Box.createHorizontalGlue());
		menubar.add(helpMenu);
		setJMenuBar(menubar);
	}

	private void createQuickDrawPanel() {
		quickDrawPanel = new QuickDrawPanel();
		Container contentPane = getContentPane();
		contentPane.add(new JScrollPane(quickDrawPanel), "Center");
	}

	public QuickDrawPanel quickDrawPanel() {
		return quickDrawPanel;
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

}