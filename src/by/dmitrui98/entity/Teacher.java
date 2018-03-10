package by.dmitrui98.entity;

import java.util.Objects;

/**
 * Created by Администратор on 27.01.2018.
 */
public class Teacher {
    private int id;
    private String surname;
    private String middlename;
    private String lastname;

    public Teacher() {
    }

    public Teacher(int id) {
        this.id = id;
    }

    public Teacher(int id, String surname, String middlename, String lastname) {
        this.id = id;
        this.surname = surname;
        this.middlename = middlename;
        this.lastname = lastname;
    }

    public Teacher(String surname, String middlename, String lastname) {
        this.surname = surname;
        this.middlename = middlename;
        this.lastname = lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(surname);
        if (middlename != null)
            sb.append(" ").append(middlename);
        if (lastname != null)
            sb.append(" ").append(lastname);
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, middlename, lastname, id);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Teacher))
            return false;
        Teacher t = (Teacher) o;
        return t.getSurname().equals(getSurname()) &&
                t.getMiddlename().equals(getMiddlename()) &&
                t.getLastname().equals(getLastname()) &&
                t.getId() == getId();
    }
}
