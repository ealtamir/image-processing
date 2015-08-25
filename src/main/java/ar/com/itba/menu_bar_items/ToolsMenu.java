package ar.com.itba.menu_bar_items;

import javax.swing.*;

/**
 * Created by Enzo on 25.08.15.
 */
public class ToolsMenu extends JMenu {

    public ToolsMenu(String name) {
        super(name);
        createOperationsMenuSet();
        createHistogramMenuSet();
        setEnabled(false);
    }

    private void createHistogramMenuSet() {
        JMenu histogram = new JMenu("Histogram");
        add(histogram);
    }

    private void createOperationsMenuSet() {
        JMenu operations = new JMenu("Operations");
        add(operations);

        JMenuItem adddition = new JMenuItem("Addition");
        JMenuItem substraction = new JMenuItem("Substraction");
        JMenuItem multiplication = new JMenuItem("Multiplication");

        operations.add(adddition);
        operations.add(substraction);
        operations.add(multiplication);
    }
}
