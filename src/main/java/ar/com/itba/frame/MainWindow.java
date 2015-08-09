package ar.com.itba.frame;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JPanel panel;

	public MainWindow() {
		initUI();
	}

	private void initUI() {
		createMenuBar();
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
		// JMenu impMenu = new JMenu("Import");
		//
		// JMenuItem newsfMi = new JMenuItem("Import newsfeed list...");
		// JMenuItem bookmMi = new JMenuItem("Import bookmarks...");
		// JMenuItem mailMi = new JMenuItem("Import mail...");

		// impMenu.add(newsfMi);
		// impMenu.add(bookmMi);
		// impMenu.add(mailMi);

		JMenuItem newMenuItem = new JMenuItem(new MenuItemAction("New", iconNew, KeyEvent.VK_N));
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		JMenuItem openMenuItem = new JMenuItem(new MenuItemAction("Open", iconOpen, KeyEvent.VK_O));
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openMenuItem.addActionListener(new OpenFileAction());
		JMenuItem saveMenuItem = new JMenuItem(new MenuItemAction("Save", iconSave, KeyEvent.VK_S));
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
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

		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		// fileMenu.add(impMenu);
		// fileMenu.addSeparator();
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

	private class MenuItemAction extends AbstractAction {

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

	private class OpenFileAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {

			JFileChooser fileChooser = new JFileChooser();
			FileFilter filter = new FileNameExtensionFilter("Image files", "bmp", "pgm", "ppm", "png", "jpeg", "jpg", "gif", "tiff");
			fileChooser.addChoosableFileFilter(filter);
			int ret = fileChooser.showDialog(panel, "Open file");
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					BufferedImage image = ImageIO.read(file);
				} catch (IOException ex) {
					// handle exception...
				}
			}
		}
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