package ar.com.itba.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;

@SuppressWarnings("serial")
public class StartWindow extends JFrame {

	enum ButtonTypes {
		ImageLoader, SquareCreator, CircleCreator, GrayShades, ColorShades
	}

	public StartWindow() {
		setWindowConfiguration();
		createWindowContents();
		startWindow();
	}

	private void startWindow() {
		setVisible(true);
	}

	private void createWindowContents() {
		ArrayList<JButton> buttons = createWindowButtons();
		placeButtonsOnWindow(buttons);
	}

	private void placeButtonsOnWindow(ArrayList<JButton> buttons) {
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		initLayout(layout, buttons);
		add(panel);
	}

	private void initLayout(GroupLayout layout, ArrayList<JButton> buttons) {
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		ParallelGroup parallelGroup = layout.createParallelGroup(Alignment.CENTER);
		parallelGroup.addComponent(new JLabel("Choose an option to continue:"));
		for (JButton button : buttons) {
			parallelGroup.addComponent(button);
		}
		layout.setVerticalGroup(parallelGroup);
	}

	private ArrayList<JButton> createWindowButtons() {
		ArrayList<JButton> buttonList = new ArrayList<JButton>();

		JButton imageLoader = new JButton("Cargar Imagen");
		imageLoader.addActionListener(new DefaultActionListener(ButtonTypes.ImageLoader));
		buttonList.add(imageLoader);

		JButton createSquare = new JButton("Crear Cuadrado");
		imageLoader.addActionListener(new DefaultActionListener(ButtonTypes.SquareCreator));
		buttonList.add(createSquare);

		JButton createCircle = new JButton("Crear Circle");
		imageLoader.addActionListener(new DefaultActionListener(ButtonTypes.CircleCreator));
		buttonList.add(createCircle);

		JButton grayShades = new JButton("Degradé de Grises");
		imageLoader.addActionListener(new DefaultActionListener(ButtonTypes.GrayShades));
		buttonList.add(grayShades);

		JButton colorShades = new JButton("Degradé de Colores");
		imageLoader.addActionListener(new DefaultActionListener(ButtonTypes.ColorShades));
		buttonList.add(colorShades);

		return buttonList;
	}

	private void setWindowConfiguration() {
		setSize(1024, 768);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void executeAction(ButtonTypes type) {
		WindowContainedApp app;
		if (type == ButtonTypes.ImageLoader) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new StandardImageManipulator();
				}
			});
		} else if (type == ButtonTypes.SquareCreator) {

		} else if (type == ButtonTypes.CircleCreator) {

		} else if (type == ButtonTypes.ColorShades) {

		} else if (type == ButtonTypes.GrayShades) {

		}
	}

	private class DefaultActionListener implements ActionListener {

		private final ButtonTypes button;

		public DefaultActionListener(ButtonTypes button) {
			this.button = button;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			executeAction(button);
		}
	}

}
