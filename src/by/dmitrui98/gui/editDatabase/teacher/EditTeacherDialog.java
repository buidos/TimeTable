package by.dmitrui98.gui.editDatabase.teacher;

import by.dmitrui98.dao.GroupDao;
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
public class EditTeacherDialog extends JDialog {
    private JTextField surnameTextField;
    private JTextField middleNameTextField;
    private JTextField lastNameTextField;
    private TeacherDao teacherDao;
    private Teacher teacher;

    public EditTeacherDialog(JFrame parentFrame, String title, boolean isModal,
                             TeacherDao teacherDao, Teacher teacher) throws HeadlessException {
        super(parentFrame, title, isModal);
        this.teacherDao = teacherDao;
        this.teacher = teacher;
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Фамилия:"));
        surnameTextField = new JTextField(20);
        surnameTextField.setText(teacher.getSurname());
        inputPanel.add(surnameTextField);

        inputPanel.add(new JLabel("Имя:"));
        middleNameTextField = new JTextField(20);
        middleNameTextField.setText(teacher.getMiddlename());
        inputPanel.add(middleNameTextField);

        inputPanel.add(new JLabel("Отчество:"));
        lastNameTextField = new JTextField(20);
        lastNameTextField.setText(teacher.getLastname());
        inputPanel.add(lastNameTextField);

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

                teacher.setSurname(surnameTextField.getText());
                teacher.setMiddlename(middleNameTextField.getText());
                teacher.setLastname(lastNameTextField.getText());

                if (teacher.getSurname().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "У преподавателя должна быть фамилия");
                    return;
                }

                teacherDao.update(teacher);
                JOptionPane.showMessageDialog(dialog, "Преподаватель успешно изменен");
                dialog.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Неверно введены данные");
            }

        }
    }
}
