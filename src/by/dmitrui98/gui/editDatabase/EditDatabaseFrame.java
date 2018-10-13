package by.dmitrui98.gui.editDatabase;

import by.dmitrui98.Main;
import by.dmitrui98.dao.DepartmentDao;
import by.dmitrui98.dao.GroupDao;
import by.dmitrui98.dao.LoadDao;
import by.dmitrui98.dao.TeacherDao;
import by.dmitrui98.entity.Group;
import by.dmitrui98.entity.Department;
import by.dmitrui98.entity.Teacher;
import by.dmitrui98.gui.editDatabase.group.AddGroupDialog;
import by.dmitrui98.gui.editDatabase.group.EditGroupDialog;
import by.dmitrui98.gui.editDatabase.teacher.AddTeacherDialog;
import by.dmitrui98.gui.editDatabase.teacher.EditTeacherDialog;
import by.dmitrui98.tableModel.editDatabase.GroupTableModel;
import by.dmitrui98.tableModel.editDatabase.TeacherTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Администратор on 24.02.2018.
 */
public class EditDatabaseFrame extends JFrame {

    private GroupDao groupDao;
    private TeacherDao teacherDao;
    private DepartmentDao departmentDao;
    private LoadDao loadDao;

    public EditDatabaseFrame(String name, Main main) {
        super(name);
        groupDao = new GroupDao(main.getCon());
        departmentDao = new DepartmentDao(main.getCon());
        teacherDao = new TeacherDao(main.getCon());
        loadDao = new LoadDao(main.getCon());
        generateGui();
    }

    LoadPanel loadPanel;
    private void generateGui() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(new TabbedChangeListener());

        // отделения
//        JPanel depPanel = new JPanel();
//        depPanel.setLayout(new BorderLayout());

        // группы
        JPanel groupPanel = new JPanel();
        groupPanel.setLayout(new BorderLayout());
        JTable groupTable = new JTable(new GroupTableModel(groupDao));
        JScrollPane groupScroller = new JScrollPane(groupTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        groupScroller.getVerticalScrollBar().setUnitIncrement(16);
        JPanel groupButtonPanel = new JPanel();
        JButton addGroup = new JButton("Добавить");
        addGroup.addActionListener(new AddGroupActionListener(groupTable));
        JButton editGroup = new JButton("Редактировать");
        editGroup.addActionListener(new EditGroupActionListener(groupTable));
        JButton deleteGroup = new JButton("Удалить");
        deleteGroup.addActionListener(new DeleteGroupActionListener(groupTable));
        groupButtonPanel.add(addGroup);
        groupButtonPanel.add(editGroup);
        groupButtonPanel.add(deleteGroup);
        groupPanel.add(groupButtonPanel, BorderLayout.NORTH);
        groupPanel.add(groupScroller, BorderLayout.CENTER);
        tabbedPane.addTab("Группы", groupPanel);

        // учителя
        JPanel teacherPanel = new JPanel();
        teacherPanel.setLayout(new BorderLayout());
        JTable teacherTable = new JTable(new TeacherTableModel(teacherDao));
        JScrollPane teacherScroller = new JScrollPane(teacherTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        teacherScroller.getVerticalScrollBar().setUnitIncrement(16);
        JPanel teacherButtonPanel = new JPanel();
        JButton addTeacher = new JButton("Добавить");
        addTeacher.addActionListener(new AddTeacherActionListener(teacherTable));
        JButton editTeacher = new JButton("Редактировать");
        editTeacher.addActionListener(new EditTeacherActionListener(teacherTable));
        JButton deleteTeacher = new JButton("Удалить");
        deleteTeacher.addActionListener(new DeleteTeacherActionListener(teacherTable));
        teacherButtonPanel.add(addTeacher);
        teacherButtonPanel.add(editTeacher);
        teacherButtonPanel.add(deleteTeacher);
        teacherPanel.add(teacherButtonPanel, BorderLayout.NORTH);
        teacherPanel.add(teacherScroller, BorderLayout.CENTER);
        tabbedPane.addTab("Учителя", teacherPanel);

        // нагрузка
        loadPanel = new LoadPanel(teacherDao, groupDao, loadDao);
        tabbedPane.addTab("Нагрузка", loadPanel);

        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
//        this.getContentPane().add(groupPanel, BorderLayout.CENTER);
    }

    private class AddGroupActionListener implements ActionListener {

        private JTable groupTable;

        public AddGroupActionListener(JTable groupTable) {
            this.groupTable = groupTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton)e.getSource());
            List<Department> departments = new ArrayList<>();
            try {
                departments = departmentDao.getAll();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(getParent(), "Ошибка при чтении отделений");
            }
            AddGroupDialog addGroupDialog = new AddGroupDialog(topFrame, "Добавить группу", true, departments,
                    groupDao);
            addGroupDialog.pack();
            addGroupDialog.setVisible(true);
            groupTable.setModel(new GroupTableModel(groupDao));
        }
    }

    private class EditGroupActionListener implements ActionListener {

        private JTable groupTable;

        public EditGroupActionListener(JTable groupTable) {
            this.groupTable = groupTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton)e.getSource());
            int row = groupTable.getSelectedRow();
            if (row != -1) {
                Integer id = (Integer) groupTable.getValueAt(row, 0);
                String name = (String) groupTable.getValueAt(row, 1);
                Integer course = (Integer) groupTable.getValueAt(row, 2);
                Department department = (Department) groupTable.getValueAt(row, 3);
                Group group = new Group(id, name, course, department);
                List<Department> departments = null;
                try {
                    departments = departmentDao.getAll();
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(getParent(), "Ошибка при чтении отделений");
                    e1.printStackTrace();
                }
                EditGroupDialog editGroupDialog = new EditGroupDialog(topFrame, "Редактирование группы", true, departments,
                        groupDao, group);
                editGroupDialog.pack();
                editGroupDialog.setVisible(true);
                groupTable.setModel(new GroupTableModel(groupDao));
            } else {
                JOptionPane.showMessageDialog(topFrame, "Выберите запись");
            }


        }
    }

    private class DeleteGroupActionListener implements ActionListener {
        private JTable groupTable;
        public DeleteGroupActionListener(JTable groupTable) {
            this.groupTable = groupTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((JButton)e.getSource());
            Object[] options = { "Да", "Нет" };
            int n = JOptionPane.showOptionDialog(frame, "Удалить выбранные группы?",
                    "Выбранные группы будут удалены", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (n != 0)
                return;

            int r = groupTable.getSelectedRow();
            if (r != -1) {
                int min = groupTable.getSelectionModel().getMinSelectionIndex();
                int max = groupTable.getSelectionModel().getMaxSelectionIndex();

                try {
                    for (int row = min; row <= max; row++) {
                        Integer id = (Integer) groupTable.getValueAt(row, 0);
                        Group group = new Group(id);
                        groupDao.delete(group);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Ошибка при удалении");
                    ex.printStackTrace();
                    return;
                } finally {
                    groupTable.setModel(new GroupTableModel(groupDao));
                }
                JOptionPane.showMessageDialog(frame, "Выбранные группы успешно удалены");
            } else {
                JOptionPane.showMessageDialog(frame, "Выберите записи");
            }
        }
    }

    private class AddTeacherActionListener implements ActionListener {

        private JTable teacherTable;

        public AddTeacherActionListener(JTable teacherTable) {
            this.teacherTable = teacherTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton)e.getSource());
            AddTeacherDialog addTeacherDialog = new AddTeacherDialog(topFrame, "Добавление учителей", true,
                    teacherDao);
            addTeacherDialog.pack();
            addTeacherDialog.setVisible(true);
            teacherTable.setModel(new TeacherTableModel(teacherDao));
        }
    }

    private class EditTeacherActionListener implements ActionListener {

        private JTable teacherTable;

        public EditTeacherActionListener(JTable teacherTable) {
            this.teacherTable = teacherTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton)e.getSource());
            int row = teacherTable.getSelectedRow();
            if (row != -1) {
                Integer id = (Integer) teacherTable.getValueAt(row, 0);
                String surname = (String) teacherTable.getValueAt(row, 1);
                String middleName = (String) teacherTable.getValueAt(row, 2);
                String lastName = (String) teacherTable.getValueAt(row, 3);

                Teacher teacher = new Teacher(id, surname, middleName, lastName);

                EditTeacherDialog editTeacherDialog = new EditTeacherDialog(topFrame, "Редактирование группы", true,
                        teacherDao, teacher);
                editTeacherDialog.pack();
                editTeacherDialog.setVisible(true);
                teacherTable.setModel(new TeacherTableModel(teacherDao));
            } else {
                JOptionPane.showMessageDialog(topFrame, "Выберите запись");
            }


        }
    }

    private class DeleteTeacherActionListener implements ActionListener {
        private JTable teacherTable;
        public DeleteTeacherActionListener(JTable teacherTable) {
            this.teacherTable = teacherTable;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((JButton)e.getSource());
            Object[] options = { "Да", "Нет" };
            int n = JOptionPane.showOptionDialog(frame, "Удалить выбранных преподавателей?",
                    "Выбранные преподаватели будут удалены", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (n != 0)
                return;

            int r = teacherTable.getSelectedRow();
            if (r != -1) {
                int min = teacherTable.getSelectionModel().getMinSelectionIndex();
                int max = teacherTable.getSelectionModel().getMaxSelectionIndex();

                try {
                    for (int row = min; row <= max; row++) {
                        Integer id = (Integer) teacherTable.getValueAt(row, 0);
                        Teacher teacher = new Teacher(id);
                        teacherDao.delete(teacher);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Ошибка при удалении");
                    ex.printStackTrace();
                    return;
                } finally {
                    teacherTable.setModel(new TeacherTableModel(teacherDao));
                }
                JOptionPane.showMessageDialog(frame, "Выбранные преподаватели удалены");
            } else {
                JOptionPane.showMessageDialog(frame, "Выберите записи");
            }
        }
    }

    private class TabbedChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
            int index = tabbedPane.getSelectedIndex();
            String tabName = tabbedPane.getTitleAt(index);
            if (tabName.equals("Нагрузка")) {
                loadPanel.setTeachers(teacherDao.getAll());
                loadPanel.setGroups(groupDao.getAll());
            }
        }
    }
}
