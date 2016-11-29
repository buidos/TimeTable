package by.dmitrui98.tableModel;

import by.dmitrui98.Main;
import by.dmitrui98.data.WorkingTeacher;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Администратор on 26.11.2016.
 */
public class TeacherRaspModel extends AbstractTableModel {
    Object[][] contents = new Object[Main.COUNT_PAIR][];
    String[] names = {"пара", "понедельник", "вторник", "среда", "четверг", "пятница", "суббота"};

    public TeacherRaspModel() {
        for (int i = 0; i < contents.length; i++) {
            contents[i] = new Object[names.length];
            contents[i][0] = Integer.toString(i+1);
            for (int j = 1; j < names.length; j++)
                contents[i][j] = "";
        }

    }

    @Override
    public int getRowCount() {
        return contents.length;
    }

    @Override
    public int getColumnCount() {
        return names.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return contents[rowIndex][columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return names[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        contents[rowIndex][columnIndex] = aValue;
    }

    public void clear() {
        for (int i = 0; i < contents.length; i++) {
            for (int j = 1; j < contents[i].length; j++) {
                contents[i][j] = "";
            }
        }
    }
}
