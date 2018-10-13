package by.dmitrui98.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Администратор on 12.11.2016.
 */
public class Teacher implements Serializable {
    private ArrayList<String> surnames = new ArrayList();
    private ArrayList<String> names = new ArrayList();
    private ArrayList<String> patronymics = new ArrayList();
    private int load;

    public Teacher() {
    }

    public Teacher(String name, int load) {
        this.load = load;
        surnames.add(name);
    }

    public int getLoad() {
        return load;
    }

    public void add(String name) {
        surnames.add(name);
    }

    public ArrayList<String> getSurnames() {
        return surnames;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String teacherName : surnames) {
            s.append(teacherName+"| ");
        }
        s.delete(s.length()-2, s.length()-1);

        return s.toString();
    }

    public void setSurnames(ArrayList<String> surnames) {
        this.surnames = surnames;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public ArrayList<String> getPatronymics() {
        return patronymics;
    }

    public void setPatronymics(ArrayList<String> patronymics) {
        this.patronymics = patronymics;
    }
}
