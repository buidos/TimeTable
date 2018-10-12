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
public class EditGroupDialog extends JDialog {
    private JTextField groupTextField;
    private JTextField courseTextField;
    private JComboBox<Department> departmentJComboBox;
    private GroupDao groupDao;
    private Group group;

    public EditGroupDialog(JFrame parentFrame, String title, boolean isModal,
                           List<Department> departments, GroupDao groupDao, Group group) throws HeadlessException {
        super(parentFrame, title, isModal);
        this.groupDao = groupDao;
        this.group = group;
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Группа:"));
        groupTextField = new JTextField(20);
        groupTextField.setText(group.getName());
        inputPanel.add(groupTextField);

        inputPanel.add(new JLabel("Курс:"));
        courseTextField = new JTextField(20);
        courseTextField.setText(String.valueOf(group.getCourse()));
        inputPanel.add(courseTextField);

        inputPanel.add(new JLabel("Отделение:"));
        departmentJComboBox = new JComboBox<>();
        int i = 0;
        int selectedIndex = -1;
        for (Department department : departments) {
            departmentJComboBox.addItem(department);
            if (department.equals(group.getDepartment()))
                selectedIndex = i;
            i++;
        }
        departmentJComboBox.setSelectedIndex(selectedIndex);
        inputPanel.add(departmentJComboBox);

        JButton addButton = new JButton("Редактировать");
        this.getRootPane().setDefaultButton(addButton);
        addButton.addActionListener(new EditGroupActionListener());
        JButton cancelButton = new JButton("Закрыть");
        cancelButton.addActionListener(e -> this.setVisible(false));

        inputPanel.add(addButton);
        inputPanel.add(cancelButton);

        this.getContentPane().add(inputPanel, BorderLayout.CENTER);
    }
    private class EditGroupActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor((JButton)e.getSource());
            try {
                String oldName = group.getName();
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
                    if (group.getName().equals(gr.getName()) && !group.getName().equals(oldName)) {
                        JOptionPane.showMessageDialog(dialog, "Группа с таким названием уже существует");
                        return;
                    }
                }

                groupDao.update(group);
                JOptionPane.showMessageDialog(dialog, "Группа успешно изменена");
                dialog.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Ошибка при изменении группы");
                ex.printStackTrace();
            }

        }
    }
}
