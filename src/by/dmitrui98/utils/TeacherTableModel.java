package by.dmitrui98.utils;

import by.dmitrui98.data.TeacherData;

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
    private static final int ROW = 20;

    public TeacherTableModel(ArrayList<TeacherData> list) {
        contents = new Object[list.size()*2][];
        for (int i = 0; i < contents.length; i++) {
            contents[i] = new Object[ROW];
            for (int j = 0; j < ROW; j++) {
                contents[i][j] = "";
            }
        }

        for (int i = 0, z = 0; i < contents.length; i += 2, z++) {
            ArrayList <String> teacherList = list.get(z).getTeacherList();
            ArrayList <Integer> loadList = list.get(z).getLoadList();

            //contents[i] = new Object[teacherList.size()];
            for (int j = 0; j < teacherList.size(); j++){
                contents[i][j] = teacherList.get(j);
                contents[i+1][j] = loadList.get(j);
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
        super.setValueAt(aValue, rowIndex, columnIndex);
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
