package ar.com.itba.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

public class ErrorTools {

	public static void showErrorMsg(String title, String msg, Component component) {
		JOptionPane.showMessageDialog(component, msg, title, JOptionPane.ERROR_MESSAGE);
	}
}
