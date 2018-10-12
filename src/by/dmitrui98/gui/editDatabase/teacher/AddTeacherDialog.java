package by.dmitrui98.gui.editDatabase.teacher;

import by.dmitrui98.dao.TeacherDao;
//import by.dmitrui98.data.Department;
//import by.dmitrui98.data.Group;
import by.dmitrui98.entity.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Администратор on 27.02.2018.
 */
public class AddTeacherDialog extends JDialog {
    private JTextField surnameTextField;
    private JTextField middleNameTextField;
    private JTextField lastNameTextField;
    private TeacherDao teacherDao;

    public AddTeacherDialog(JFrame parentFrame, String title, boolean isModal, TeacherDao teacherDao) throws HeadlessException {
        super(parentFrame, title, isModal);
        this.teacherDao = teacherDao;
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Фамилия:"));
        surnameTextField = new JTextField(20);
        inputPanel.add(surnameTextField);

        inputPanel.add(new JLabel("Имя:"));
        middleNameTextField = new JTextField(20);
        inputPanel.add(middleNameTextField);

        inputPanel.add(new JLabel("Отчество:"));
        lastNameTextField = new JTextField(20);
        inputPanel.add(lastNameTextField);

        JButton addButton = new JButton("Добавить");
        this.getRootPane().setDefaultButton(addButton);
        addButton.addActionListener(new AddTeacherActionListener());
        JButton cancelButton = new JButton("Закрыть");
        cancelButton.addActionListener(e -> this.setVisible(false));

        inputPanel.add(addButton);
        inputPanel.add(cancelButton);

        this.getContentPane().add(inputPanel, BorderLayout.CENTER);
    }
    private class AddTeacherActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JDialog dialog = (JDialog) SwingUtilities.getWindowAncestor((JButton)e.getSource());
            Teacher teacher = new Teacher();
            try {
                teacher.setSurname(surnameTextField.getText());
                teacher.setMiddlename(middleNameTextField.getText());
                teacher.setLastname(lastNameTextField.getText());

                if (teacher.getSurname().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "У преподавателя должна быть фамилия");
                    return;
                }

                teacherDao.insert(teacher);
                JOptionPane.showMessageDialog(dialog, "Преподаватель успешно добавлен");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Ошибка при добавлении преподавателя");
                ex.printStackTrace();
            }
        }
    }
}
