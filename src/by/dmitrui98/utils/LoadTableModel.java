package by.dmitrui98.utils;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Администратор on 30.10.2016.
 */
public class LoadTableModel extends AbstractTableModel {

    private Object[][] contents; //хранит данные
    private String[] columnNames;//хранит имена столбцов

    public LoadTableModel(int row, ArrayList<Integer> sg) {
        super();

        contents = new Object[row][];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = new Object[sg.size()];
            for (int j = 0; j < contents[i].length; j++)
                contents[i][j] = "";
        }

        columnNames = new String[sg.size()];
        for (int i = 0; i < sg.size(); i++) {
            columnNames[i] = String.valueOf(sg.get(i));
        }
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
}
