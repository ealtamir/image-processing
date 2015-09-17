package ar.com.itba.image_actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ar.com.itba.panel.LabeledSliderPanel;
import ar.com.itba.panel.QuickDrawPanel;

abstract public class ParameterizedImageAction extends JFrame implements ActionListener, ChangeListener {

    protected boolean automaticChange = false;
    protected QuickDrawPanel quickDrawPanel;
    protected List<LabeledSliderPanel> sliderPanels;
    protected JPanel contents;

    private JCheckBox automaticSwitch;
    private JButton doChange;

    protected BufferedImage originalImage;

    protected final int MIN_VAL = 0;
    protected final int MAX_VAL = 255;
    protected final int DEFAULT_VAL = 100;
    protected final int DIV = 1;

    protected int sliderValue = DEFAULT_VAL;
    protected int changedSliderNum = -1;


    public ParameterizedImageAction(QuickDrawPanel quickDrawPanel) {
        this.quickDrawPanel = quickDrawPanel;
        this.originalImage = quickDrawPanel.image();
        sliderPanels = new ArrayList<LabeledSliderPanel>();
        contents = new JPanel();
        setContentPane(contents);
        contents.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
        contents.add(automaticSwitchOptions());
        contents.setVisible(true);
        setVisible(true);
        setResizable(false);
//        setSize(300, 100);
    }



    public JPanel automaticSwitchOptions() {
        JPanel automaticSwitchOptions = new JPanel();
        automaticSwitchOptions.setLayout(new BoxLayout(automaticSwitchOptions, BoxLayout.X_AXIS));

        automaticSwitch = new JCheckBox("Automático");
        automaticSwitch.setSelected(false);
        automaticSwitch.addActionListener(this);
        automaticSwitch.setVisible(true);
        automaticSwitchOptions.add(automaticSwitch);

        doChange = new JButton("Actualizar");
        doChange.addActionListener(this);
        doChange.setVisible(true);
        automaticSwitchOptions.add(doChange);

        return automaticSwitchOptions;
    }

    protected void processActionEvent(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(automaticSwitch)) {
            automaticChange = ((JCheckBox) source).isSelected();
        } else if (source.equals(doChange)) {
            updateImage();
        }
    }

    public void close() {
        setVisible(false);
        dispose();
    }

    public void changeTargetImage(QuickDrawPanel quickDrawPanel) {
        this.quickDrawPanel = quickDrawPanel;
        originalImage = quickDrawPanel.image();
    }


    static public class TextFormatter {
        private int div;

        public TextFormatter(int div) {
            this.div = div;
        }

        public String format(int num) {
            float val = (float) num / div;
            return String.format("%1.2f ", val);
        }

    }

    protected void identifyChangedSlider(ChangeEvent e) {
        JSlider changedSlider = (JSlider) e.getSource();
        JSlider slider;
        changedSliderNum = -1;

        for (LabeledSliderPanel panel : sliderPanels) {
            changedSliderNum += 1;
            slider = panel.getSlider();
            if (slider.equals(changedSlider)) {
                processSliderChange(changedSlider);
            }
        }
    }

    protected void addScalarSlider(int DEFAULT_VAL, int MIN_VAL, int MAX_VAL, int DIV, boolean b,
                                 ParameterizedImageAction scalarMultOperation, TextFormatter textFormatter) {
        LabeledSliderPanel scalarSlider = new LabeledSliderPanel(DEFAULT_VAL, MIN_VAL,
                MAX_VAL, true, this, new TextFormatter(DIV));
        contents.add(scalarSlider);
        scalarSlider.setVisible(true);
        sliderPanels.add(scalarSlider);
        sliderValue = DEFAULT_VAL;
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        processActionEvent(e);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        identifyChangedSlider(e);
    }


    protected void processSliderChange(JSlider changedSlider) {
        sliderValue = changedSlider.getValue();
        if (automaticChange) {
            updateImage();
        }
    }

    protected abstract void updateImage();

}
