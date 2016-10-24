package by.dmitrui98.entity;

/**
 * Created by Администратор on 25.10.2016.
 */
public class Teacher {
    private String surname;
    private String name;
    private String patronymic;

    public Teacher(String surname, String name, String patronymic) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    public String getSurname() {

        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
