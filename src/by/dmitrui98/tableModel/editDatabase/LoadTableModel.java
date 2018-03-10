package by.dmitrui98.tableModel.editDatabase;

import by.dmitrui98.data.TeachersHour;
import by.dmitrui98.entity.Teacher;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by Администратор on 30.10.2016.
 */
public class LoadTableModel extends AbstractTableModel {

    private Object[][] contents; //хранит данные
    private String[] columnNames;//хранит имена столбцов

    public LoadTableModel(List<TeachersHour> teachersHourList) {
        super();
        int countColumns = 2;

        if (teachersHourList.isEmpty())
            contents = new Object[0][countColumns];
        else {
            contents = new Object[teachersHourList.size()][countColumns];
            for (int i = 0; i < contents.length; i++) {
                contents[i][0] = teachersHourList.get(i).getTeachers();
                contents[i][1] = teachersHourList.get(i).getHour();
            }
        }

        columnNames = new String[countColumns];
        columnNames[0] = "учитель";
        columnNames[1] = "час";
    }

    public int getRowCount() {
        return contents.length;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int row, int column) {
        return contents[row][column];
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        contents[rowIndex][columnIndex] = aValue;
    }
}
