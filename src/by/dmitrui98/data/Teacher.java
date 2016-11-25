package by.dmitrui98.data;

import by.dmitrui98.enums.TypeHour;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Администратор on 12.11.2016.
 */
public class Teacher implements Serializable {
    private ArrayList<String> names = new ArrayList<String>();
    private int load;

    public Teacher() {
    }

    public Teacher(String name, int load) {
        this.load = load;
        names.add(name);
    }

    public int getLoad() {
        return load;
    }

    public void add(String name) {
        names.add(name);
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String teacherName : names) {
            s.append(teacherName+"| ");
        }
        s.delete(s.length()-2, s.length()-1);

        return s.toString();
    }

}
