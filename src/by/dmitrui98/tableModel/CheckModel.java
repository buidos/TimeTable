package by.dmitrui98.tableModel;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CheckModel extends AbstractTableModel {

    private int rows;
    private List<Boolean> rowList = new ArrayList<>();
    private Set<Integer> selectedGroup = new TreeSet<>();

    public CheckModel() {

    }

    public CheckModel(int rows) {
        this.rows = rows;
        rowList = new ArrayList<Boolean>(rows);
        for (int i = 0; i < rows; i++) {
            rowList.add(Boolean.TRUE);
            selectedGroup.add(i);
        }
    }

    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int col) {
        return "Отобразить группы:";
    }

    @Override
    public Object getValueAt(int row, int col) {
        return rowList.get(row);
    }

    @Override
    public void setValueAt(Object aValue, int row, int col) {
        boolean b = (Boolean) aValue;
        rowList.set(row, b);
        if (b) {
            selectedGroup.add(row);
        } else {
            selectedGroup.remove(row);
        }
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return getValueAt(0, col).getClass();
    } // задаем, что ячейка будет представлена в виде JCheckBox

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    public TreeSet<Integer> getSelectedGroup() {
        return (TreeSet <Integer>) selectedGroup;
    }
}