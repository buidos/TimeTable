package by.dmitrui98.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Администратор on 24.10.2016.
 */
public class TimetableMainPanel extends JPanel {
    public TimetableMainPanel() {
        GridLayout grid = new GridLayout(2, 3, 1, 2);
        JPanel panel = new JPanel(grid);

        panel.add(new JCheckBox("1 курс"));
        panel.add(new JCheckBox("3 курс"));
        panel.add(new JCheckBox("радио"));


        panel.add(new JCheckBox("2 курс"));
        panel.add(new JCheckBox("4 курс"));
        panel.add(new JCheckBox("комп"));

        this.add(panel);

        grid = new GridLayout(2, 3, 1, 2);
        JPanel panelDay = new JPanel(grid);
        panelDay.add(new JCheckBox("пн"));
        panelDay.add(new JCheckBox("вт"));
        panelDay.add(new JCheckBox("ср"));
        panelDay.add(new JCheckBox("чт"));
        panelDay.add(new JCheckBox("пт"));
        panelDay.add(new JCheckBox("сб"));

        this.add(panelDay);

        JButton btnDisplayGroup = new JButton("Отображаемые группы");
        btnDisplayGroup.addActionListener(new ButtonDisplayGroupListener());
        this.add(btnDisplayGroup);

        JButton btnShow = new JButton("Показать");
        btnShow.addActionListener(new ButtonShowListener());
        this.add(btnShow);

        /*Box courseBox1_2 = new Box(BoxLayout.Y_AXIS);
        Box courseBox3_4 = new Box(BoxLayout.Y_AXIS);

        courseBox1_2.add(new JCheckBox("1 курс"));
        courseBox1_2.add(new JCheckBox("2 курс"));
        courseBox3_4.add(new JCheckBox("3 курс"));
        courseBox3_4.add(new JCheckBox("4 курс"));

        this.add(courseBox1_2);
        this.add(courseBox3_4);

        Box departmentBox = new Box(BoxLayout.Y_AXIS);
        departmentBox.add(new JCheckBox("радиотехническое"));
        departmentBox.add(new JCheckBox("компьютерное"));

        this.add(departmentBox);*/



        /*ObservableList<String> strings = FXCollections.observableArrayList();
        for (int i = 0; i <= 100; i++) {
            strings.add("Item " + i);
        }

        // Create the CheckComboBox with the data
        CheckComboBox<String> checkComboBox = new CheckComboBox<String>(strings);

        this.add(checkComboBox);
*/
    }

    class ButtonDisplayGroupListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }

    class ButtonShowListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }
}
