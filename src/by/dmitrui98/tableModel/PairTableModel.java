package by.dmitrui98.tableModel;

import javax.swing.table.AbstractTableModel;

/**
 * Created by Администратор on 04.11.2016.
 */
public class PairTableModel extends AbstractTableModel {
    private Object[][] contents; //хранит данные
    private String[] columnNames = {"пары"};//хранит имена столбцов

    public PairTableModel(int row) {
        super();

        contents = new Object[row][];

        for (int i = 0,  y = 1; i < contents.length; i++, y++) {

            contents[i] = new Object[]{String.valueOf(y)};
            if (y==7) {
                y = 0;
            }
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
