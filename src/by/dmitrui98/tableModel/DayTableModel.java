package by.dmitrui98.tableModel;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

/**
 * Created by Администратор on 15.11.2016.
 */
public class DayTableModel extends DefaultTableModel {

    public DayTableModel() {
    }

    public DayTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }

    public DayTableModel(Vector columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public DayTableModel(Object[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public DayTableModel(Vector data, Vector columnNames) {
        super(data, columnNames);
    }

    public DayTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
