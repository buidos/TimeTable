package by.dmitrui98.dao;


import by.dmitrui98.data.TeachersHour;
import by.dmitrui98.entity.Group;
import by.dmitrui98.entity.Teacher;

import java.sql.*;
import java.util.List;

/**
 * Created by Администратор on 25.02.2018.
 */
public class LoadDao {

    private Connection con;


    public LoadDao(Connection con) {
        this.con = con;
    }

    /**
     * Вставляет нагрузку группы в базу данных
     * @param teachersHourList объектов TeachersHour
     * @param group - группа для которой вставляется нагрузка
     */
    public void insert(List<TeachersHour> teachersHourList, Group group) {

        // удаляем предыдущую нагрузку
        String sql = "DELETE FROM load WHERE group_id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, group.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            for (Teacher teacher : th.getTeachers()) {
                try (PreparedStatement workingTeacherPs = con.prepareStatement(workingTeacherSql)) {
                    workingTeacherPs.setInt(1, loadId);
                    workingTeacherPs.setInt(2, teacher.getId());
                    workingTeacherPs.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
