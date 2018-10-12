package by.dmitrui98.dao;


import by.dmitrui98.data.TeachersHour;
import by.dmitrui98.entity.Group;
import by.dmitrui98.entity.Load;
import by.dmitrui98.entity.Teacher;

import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Created by Администратор on 25.02.2018.
 */
public class LoadDao extends AbstractDao<Load> {

    private Connection con;


    public LoadDao(Connection con) {
        this.con = con;
    }

    /**
     * Вставляет нагрузку группы в базу данных
     */
    @Override
    public void insert(Load load) throws SQLException {
        List<TeachersHour> teachersHourList = load.getTeachersHourList();
        Group group = load.getGroup();
        // удаляем предыдущую нагрузку
        String sql = "DELETE FROM load WHERE group_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, group.getId());
            ps.executeUpdate();
        }

        String loadSql = "INSERT INTO load(group_id, hour) VALUES(?,?)";
        String workingTeacherSql = "INSERT INTO working_teacher(id, teacher_id) VALUES(?,?)";

        for (TeachersHour th : teachersHourList) {

            // устанавливаем часы группе
            int loadId = -1;
            try (PreparedStatement loadPs = con.prepareStatement(loadSql);
                 Statement st = con.createStatement()){
                loadPs.setInt(1, group.getId());
                loadPs.setInt(2, th.getHour());
                loadPs.executeUpdate();
                ResultSet rs = st.executeQuery("SELECT last_insert_rowid() as id");
                loadId = rs.getInt("id");
            }
            for (Teacher teacher : th.getTeachers()) {
                try (PreparedStatement workingTeacherPs = con.prepareStatement(workingTeacherSql)) {
                    workingTeacherPs.setInt(1, loadId);
                    workingTeacherPs.setInt(2, teacher.getId());
                    workingTeacherPs.executeUpdate();
                }
            }
        }
    }
}
