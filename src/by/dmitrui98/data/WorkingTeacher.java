package by.dmitrui98.data;


import by.dmitrui98.enums.TypeHour;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Администратор on 18.11.2016.
 */
public class WorkingTeacher implements Serializable {
    private ArrayList<String> names = new ArrayList<String> ();
    private ArrayList<Pair> pairs = new ArrayList<Pair>();

    public WorkingTeacher(ArrayList<String> names, Pair pair) {
        for (String name : names) {
            this.names.add(new String(name));
        }

        pairs.add(pair);
    }

    public void addPair(Pair pair) {
        for (int i = 0; i < pairs.size(); i++) {
            Pair p = pairs.get(i);
            if ((p.getRow() == pair.getRow()) && (p.getGroup() == pair.getGroup()) && (p.getTypeHour() == pair.getTypeHour())) {

                System.out.println("Ошибка добавления пары");
                return;
            }
        }
        pairs.add(pair);
    }

    public void removePair(Pair pair) {
        for (int i = 0; i < pairs.size(); i++) {
            Pair p = pairs.get(i);
            if ((p.getRow() == pair.getRow()) && (p.getGroup() == pair.getGroup()) && (p.getTypeHour() == pair.getTypeHour())) {
                pairs.remove(i);
                break;
            }
        }
    }

    public ArrayList<Pair> getPairs() {
        return pairs;
    }

    public Pair getPair(int row, int col, TypeHour th) {
        for (Pair pair : pairs) {
            if (pair.getRow() == row && pair.getGroup() == col && pair.getTypeHour() == th)
                return pair;
        }
        return null;
    }

    public Pair getPair(int row, int col) {
        for (Pair pair : pairs) {
            if (pair.getRow() == row && pair.getGroup() == col)
                return pair;
        }
        return null;
    }

    public ArrayList<String> getNames() {
        return names;
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
