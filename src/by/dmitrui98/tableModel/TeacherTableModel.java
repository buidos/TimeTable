package by.dmitrui98.tableModel;

import by.dmitrui98.data.Teacher;
import by.dmitrui98.data.TeacherColumn;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.EventListener;

/**
 * Created by Администратор on 11.11.2016.
 */
public class TeacherTableModel extends AbstractTableModel {
    private Object[][] contents; //хранит данные
    public static final int ROW = 20;

    public TeacherTableModel(ArrayList<TeacherColumn> list) {

        contents = new Object[list.size()*2][];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = new Object[ROW];
            for (int j = 0; j < contents[i].length; j++) {
                contents[i][j] = "";
            }
        }

        for (int j = 0, z = 0; z < list.size(); j += 2, z++) {
            ArrayList<Teacher> teacherRow = list.get(z).getTeacherList();

            for (int i = 0; i < teacherRow.size(); i++){
                Teacher t = teacherRow.get(i);

                contents[j][i] = t;
                contents[j+1][i] = t.getLoad();
            }
        }
    }

    @Override
    public int getRowCount() {
        return ROW;
    }

    @Override
    public int getColumnCount() {
        return contents.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return contents[columnIndex][rowIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        contents[columnIndex][rowIndex] = aValue;

        fireTableCellUpdated(columnIndex, rowIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return super.isCellEditable(rowIndex, columnIndex);
    }



    @Override
    public void addTableModelListener(TableModelListener l) {
        super.addTableModelListener(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        super.removeTableModelListener(l);
    }

    @Override
    public TableModelListener[] getTableModelListeners() {
        return super.getTableModelListeners();
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }

    @Override
    public void fireTableStructureChanged() {
        super.fireTableStructureChanged();
    }

    @Override
    public void fireTableRowsInserted(int firstRow, int lastRow) {
        super.fireTableRowsInserted(firstRow, lastRow);
    }

    @Override
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        super.fireTableRowsUpdated(firstRow, lastRow);
    }

    @Override
    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        super.fireTableRowsDeleted(firstRow, lastRow);
    }

    @Override
    public void fireTableCellUpdated(int row, int column) {
        super.fireTableCellUpdated(row, column);
    }

    @Override
    public void fireTableChanged(TableModelEvent e) {
        super.fireTableChanged(e);
    }

    @Override
    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return super.getListeners(listenerType);
    }
}
