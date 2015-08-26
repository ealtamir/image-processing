package ar.com.itba.menu_bar_items;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.image_actions.operations.ImageOperationManager;
import ar.com.itba.image_actions.transformations.ImageTransformationManager;
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
        if (belongsToImageOperations(e)) {
            return;
        } else if (belongsToImageHistogram(e)) {
            return;
        } else if (belongsToMiscOperations(e)) {
            return;
        } else if (belongsToImageTransformations(e)) {
            return;
        }
    }

    private boolean belongsToImageTransformations(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.IMAGE_NEGATIVE)) {
            ImageTransformationManager.showNegative(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.DYNAMIC_RANGE_COMPRESS)) {
            ImageTransformationManager.showDynamicCompression(quickDrawPanel, mainWindow);
        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToMiscOperations(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.SHOW_IMAGE_OPTIONS)) {
            quickDrawPanel.toggleImageTools();
        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToImageHistogram(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.SHOW_HISTOGRAM)) {
            quickDrawPanel.toggleImageHistogram();
        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToImageOperations(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.IMAGE_ADDITION)) {
            ImageOperationManager.performAddition(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.IMAGE_SUBSTRACTION)) {
            ImageOperationManager.performSubstraction(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.IMAGE_MULTIPLICATION)) {
            ImageOperationManager.performMultiplication(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.SCALAR_MULTIPLICATION)) {
            ImageOperationManager.imageScalarMult(quickDrawPanel, mainWindow);

        } else {
            belongs = false;
        }
        return belongs;
    }
}
