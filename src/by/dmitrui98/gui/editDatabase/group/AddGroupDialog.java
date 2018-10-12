package by.dmitrui98.gui.editDatabase.group;

import by.dmitrui98.dao.GroupDao;
import by.dmitrui98.entity.Department;
import by.dmitrui98.entity.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Администратор on 27.02.2018.
 */
public class AddGroupDialog extends JDialog {
    private JTextField groupTextField;
    private JTextField courseTextField;
    private JComboBox<Department> departmentJComboBox;
    private GroupDao groupDao;

    public AddGroupDialog(JFrame parentFrame, String title, boolean isModal,
                          List<Department> departments, GroupDao groupDao) throws HeadlessException {
        super(parentFrame, title, isModal);
        this.groupDao = groupDao;
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Группа:"));
        groupTextField = new JTextField(20);
        inputPanel.add(groupTextField);

        inputPanel.add(new JLabel("Курс:"));
        courseTextField = new JTextField(20);
        inputPanel.add(courseTextField);

        inputPanel.add(new JLabel("Отделение:"));
        departmentJComboBox = new JComboBox<>();
        for (Department department : departments) {
            departmentJComboBox.addItem(department);
        }
        inputPanel.add(departmentJComboBox);

        JButton addButton = new JButton("Добавить");
        this.getRootPane().setDefaultButton(addButton);
        addButton.addActionListener(new AddGroupActionListener());
        JButton cancelButton = new JButton("Закрыть");
        cancelButton.addActionListener(e -> this.setVisible(false));

        inputPanel.add(addButton);
        inputPanel.add(cancelButton);

        this.getContentPane().add(inputPanel, BorderLayout.CENTER);
    }
    private class AddGroupActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor((JButton)e.getSource());
            Group group = new Group();
            try {
                group.setName(groupTextField.getText());
                group.setCourse(Integer.parseInt(courseTextField.getText()));
                Department dep = (Department) departmentJComboBox.getSelectedItem();
                group.setDepartment(dep);

                if (group.getName().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Название группы не должно быть пустым");
                    return;
                }

                List<Group> groups = groupDao.getAll();
                for (Group gr : groups) {
                    if (group.getName().equals(gr.getName())) {
                        JOptionPane.showMessageDialog(dialog, "Группа с таким названием уже существует");
                        return;
                    }
                }

                groupDao.insert(group);
                JOptionPane.showMessageDialog(dialog, "Группа успешно добавлена");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Ошибка при добавлении группы");
                ex.printStackTrace();
            }
        }
    }
}
