package by.dmitrui98.tableRenderer;

import by.dmitrui98.data.Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Created by Администратор on 12.10.2018.
 */
public class TeacherColumnCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        String names = null;
        if (value instanceof Teacher) {
            Teacher t = (Teacher) value;
            names = t.getSurnames().toString();
        }

        JLabel l = (JLabel) c;
        l.setToolTipText(names);
        return c;
    }
}
