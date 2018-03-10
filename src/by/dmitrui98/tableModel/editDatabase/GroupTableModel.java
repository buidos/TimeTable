package by.dmitrui98.tableModel.editDatabase;

import by.dmitrui98.dao.GroupDao;
import by.dmitrui98.entity.Group;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Created by Администратор on 30.10.2016.
 */
public class GroupTableModel extends AbstractTableModel {

    private Object[][] contents; //хранит данные
    private String[] columnNames;//хранит имена столбцов

    public GroupTableModel(GroupDao groupDao) {
        super();
        List<Group> groups = groupDao.getAll();
        int countColumns = 4;

        contents = new Object[groups.size()][countColumns];
        for (int i = 0; i < contents.length; i++) {
            contents[i][0] = groups.get(i).getId();
            contents[i][1] = groups.get(i).getName();
            contents[i][2] = groups.get(i).getCourse();
            contents[i][3] = groups.get(i).getDepartment();
        }

        columnNames = new String[countColumns];
        columnNames[0] = "id";
        columnNames[1] = "группа";
        columnNames[2] = "курс";
        columnNames[3] = "отделение";
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
