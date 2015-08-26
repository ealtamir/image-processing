package ar.com.itba.menu_bar_items;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.panel.QuickDrawPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuEventsListener implements ActionListener {

    public QuickDrawPanel quickDrawPanel;
    public MainWindow mainWindow;

    public MenuEventsListener(QuickDrawPanel quickDrawPanel, MainWindow window) {
        this.quickDrawPanel = quickDrawPanel;
        this.mainWindow = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        if (belongsToImageOperations(e)) {
            return;
        } else if (belongsToImageHistogram(e)) {
            return;
        } else if (belongsToMiscOperations(e)) {
            return;
        }
    }

    private boolean belongsToMiscOperations(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand() == ToolsMenu.SHOW_IMAGE_OPTIONS) {
            quickDrawPanel.toggleImageTools();
        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToImageHistogram(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand() == ToolsMenu.SHOW_HISTOGRAM) {
            quickDrawPanel.toggleImageHistogram();
        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToImageOperations(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand() == ToolsMenu.HISTOGRAM_ADDITION) {

        } else if (e.getActionCommand() == ToolsMenu.HISTOGRAM_SUBSTRACTION) {

        } else if (e.getActionCommand() == ToolsMenu.HISTOGRAM_MULTIPLICATION) {

        } else {
            belongs = false;
        }
        return belongs;
    }
}
