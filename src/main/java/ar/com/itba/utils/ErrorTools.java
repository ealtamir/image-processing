package ar.com.itba.utils;

import javax.swing.*;
import java.awt.*;

public class ErrorTools {

	public static void showErrorMsg(String title, String msg, Component component) {
		JOptionPane.showMessageDialog(component, msg, title, JOptionPane.ERROR_MESSAGE);
	}
}
