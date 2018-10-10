package by.dmitrui98.gui.editDatabase;

import by.dmitrui98.dao.GroupDao;
import by.dmitrui98.dao.LoadDao;
import by.dmitrui98.dao.TeacherDao;
import by.dmitrui98.data.TeachersHour;
import by.dmitrui98.entity.Group;
import by.dmitrui98.entity.Teacher;
import by.dmitrui98.gui.HintTextField;
import by.dmitrui98.tableModel.editDatabase.BunchTableModel;
import by.dmitrui98.tableModel.editDatabase.LoadTableModel;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Администратор on 10.03.2018.
 */
public class LoadPanel extends JPanel {

    private List<Teacher> teachers;
    private LoadDao loadDao;

    private List<TeachersHour> teachersHourList = new ArrayList<>();
    private List<Teacher> bunchTeachers = new ArrayList<>();

    private JList<Teacher> teacherJList;
    private HintTextField teacherTextField;
    private JTable bunchTable;
    private JTable loadTable;
    private JComboBox<Group> groupJComboBox;
    JTextField hourTextField;

    public LoadPanel(TeacherDao teacherDao, GroupDao groupDao, LoadDao loadDao) {
        this.teachers = teacherDao.getAll();
        this.loadDao = loadDao;
        List<Group> groups = groupDao.getAll();

        JPanel teacherPanel = generateTeacherPanel();
        JPanel bunchPanel = generateBunchPanel();

        JPanel groupPanel = new JPanel();
        groupPanel.add(teacherPanel);
        groupPanel.add(bunchPanel);

        JPanel northPanel = generateNorthPanel(groups);
        JPanel eastPanel = generateEastPanel();

        this.setLayout(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);
        this.add(groupPanel, BorderLayout.WEST);
        this.add(eastPanel, BorderLayout.EAST);

    }

    private JPanel generateEastPanel() {
        JPanel panel = new JPanel();
        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(bl);

        loadTable = new JTable(new LoadTableModel(teachersHourList));
        loadTable.addMouseListener(new LoadMouseListener());
        JScrollPane loadScrollPane = new JScrollPane(loadTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panel.add(new JLabel("Нагрузка"));
        panel.add(loadScrollPane);
        return panel;
    }

    private JPanel generateNorthPanel(List<Group> groups) {
        JPanel panel = new JPanel();
        groupJComboBox = new JComboBox();
        for (Group group : groups) {
            groupJComboBox.addItem(group);
        }

        JButton addHourButton = new JButton("Добавить часы");
        addHourButton.addActionListener(e -> {
            int hour = 0;
            try {
                hour = Integer.parseInt(hourTextField.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getParent(), "Неверно введены часы в неделю");
                return;
            }
            if (bunchTeachers.isEmpty()) {
                JOptionPane.showMessageDialog(getParent(), "Нужно добавить в связку хотя бы одного учителя");
                return;
            }
            TeachersHour teachersHour = new TeachersHour(bunchTeachers, hour);
            teachersHourList.add(teachersHour);
            loadTable.setModel(new LoadTableModel(teachersHourList));
            bunchTeachers = new ArrayList<>();
            bunchTable.setModel(new BunchTableModel(bunchTeachers));
        });

        JButton addLoadButton = new JButton("Установить нагрузку выбранной группе");
        addLoadButton.addActionListener(new LoadActionListener());

        JLabel label = new JLabel("Часы в неделю:");
        hourTextField = new JTextField(10);
        panel.add(new JLabel("Группа:"));
        panel.add(groupJComboBox);
        panel.add(label);
        panel.add(hourTextField);
        panel.add(addHourButton);
        panel.add(addLoadButton);
        return panel;
    }

    private JPanel generateBunchPanel() {
        JPanel bunchPanel = new JPanel();
        BoxLayout bl = new BoxLayout(bunchPanel, BoxLayout.Y_AXIS);
        bunchPanel.setLayout(bl);
        JButton bunchButton = new JButton("Добавить в связку учителя");
        bunchButton.addActionListener(e -> {
            Teacher selectedTeacher = teacherJList.getSelectedValue();
            if (selectedTeacher != null) {
                bunchTeachers.add(selectedTeacher);
                bunchTable.setModel(new BunchTableModel(bunchTeachers));
            } else {
                JOptionPane.showMessageDialog(this.getParent(), "Выберите учителя");
            }
        });
        bunchTable = new JTable(new BunchTableModel(bunchTeachers));
        bunchTable.addMouseListener(new MyMouseListener());
        JScrollPane bunchTableScroller = new JScrollPane(bunchTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bunchPanel.add(bunchButton);
        bunchPanel.add(bunchTableScroller);
        return bunchPanel;
    }

    private JPanel generateTeacherPanel() {
        JPanel teacherPanel = new JPanel();
        BoxLayout bl = new BoxLayout(teacherPanel, BoxLayout.Y_AXIS);
        teacherPanel.setLayout(bl);
        teacherJList = new JList(teachers.toArray());
        int width = 150;
        int height = 400;
        teacherJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane teacherScroller = new JScrollPane(teacherJList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        teacherScroller.setPreferredSize(new Dimension(width, height));

        teacherTextField = new HintTextField("найти учителя");
        teacherTextField.addKeyListener(new MyKeyListener());
        teacherTextField.setMaximumSize(new Dimension(width, 30));

        teacherPanel.add(teacherTextField);
        teacherPanel.add(teacherScroller);
        return teacherPanel;
    }


    private class MyKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {
            String str = teacherTextField.getText().toLowerCase();

            ArrayList<Teacher> teachers1 = new ArrayList<>();
            for (Teacher t : teachers) {

                if (t.getSurname().toLowerCase().contains(str)) {
                    teachers1.add(t);
                }
            }

            teacherJList.setModel(new ListModel() {
                private Object[] contents = teachers1.toArray();

                @Override
                public int getSize() {
                    return contents.length;
                }

                @Override
                public Object getElementAt(int index) {
                    return contents[index];
                }

                @Override
                public void addListDataListener(ListDataListener l) {

                }

                @Override
                public void removeListDataListener(ListDataListener l) {

                }
            });
        }
    }

    private class MyMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem item = new JMenuItem("удалить");
                item.addActionListener(e1 -> {
                    int index = bunchTable.getSelectedRow();
                    if (index >= 0) {
                        bunchTeachers.remove(index);
                        bunchTable.setModel(new BunchTableModel(bunchTeachers));
                    } else
                        JOptionPane.showMessageDialog(getParent(), "Выберите учителя");
                });
                int selectedRow = bunchTable.getSelectedRow();
                if (selectedRow == -1)
                    item.setEnabled(false);
                popup.add(item);
                popup.show(bunchTable, e.getX(), e.getY());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class LoadMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                JPopupMenu popup = new JPopupMenu();
                JMenuItem item = new JMenuItem("удалить");
                item.addActionListener(e1 -> {
                    int index = loadTable.getSelectedRow();
                    if (index >= 0) {
                        teachersHourList.remove(index);
                        loadTable.setModel(new LoadTableModel(teachersHourList));
                    } else
                        JOptionPane.showMessageDialog(getParent(), "Выберите учителя");
                });
                int selectedRow = loadTable.getSelectedRow();
                if (selectedRow == -1)
                    item.setEnabled(false);
                popup.add(item);
                popup.show(loadTable, e.getX(), e.getY());
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class LoadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Group selectedGroup = (Group) groupJComboBox.getSelectedItem();
            Object[] options = { "Да", "Нет" };
            int n = JOptionPane.showOptionDialog(getParent(), "Установить нагрузку для группы "+selectedGroup.getName() + "?",
                    "Предыдущая нагрузка будет удалена", JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (n != 0)
                return;
            try {
                loadDao.insert(teachersHourList, selectedGroup);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(getParent(), "Ошибка при обновлении нагрузки");
                return;
            }
            JOptionPane.showMessageDialog(getParent(), "Нагрузка группы успешно обновлена");
            teachersHourList = new ArrayList<>();
            loadTable.setModel(new LoadTableModel(teachersHourList));
        }
    }
}
