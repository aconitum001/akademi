package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.TeacherModel;

public class TeacherDAO {

    public boolean isFoundQuery(Connection connectDB, String CIN) {
        String query = "SELECT CIN FROM teacher WHERE CIN = ?";
        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, CIN);
            ResultSet resultset = preparedStatement.executeQuery();

            return resultset.next(); 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }

    public void addQuery(Connection connectDB, TeacherModel teacherModel) {
        String query = "INSERT INTO teacher (CIN, fullName, phoneNumber, profession) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, teacherModel.getCIN());
            preparedStatement.setString(2, teacherModel.getFullName());
            preparedStatement.setString(3, teacherModel.getPhoneNumber());
            preparedStatement.setString(4, teacherModel.getProfession());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void updateQuery(Connection connectDB, TeacherModel teacherModel) { 
        String query = "UPDATE teacher " +
                       "SET fullName = ?, " +
                       "phoneNumber = ?, " +
                       "profession = ? " +
                       "WHERE CIN = ?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, teacherModel.getFullName());
            preparedStatement.setString(2, teacherModel.getPhoneNumber());
            preparedStatement.setString(3, teacherModel.getProfession());
            preparedStatement.setString(4, teacherModel.getCIN());
    
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteQuery(Connection connectDB, String CIN) {
        String query = "DELETE FROM teacher WHERE CIN = ? ";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, CIN);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}