package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;

import models.StudentModel;

public class StudentDAO {
    

    public void addQuery(Connection connectDB, StudentModel studentModel) {
        String query = "INSERT INTO students (CIN, fullName, classe, filiere) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, studentModel.getCIN());
            preparedStatement.setString(2, studentModel.getFullName());
            preparedStatement.setString(3, studentModel.getClasse());
            preparedStatement.setString(4, studentModel.getFiliere());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isFoundQuery(Connection connectDB, String CIN) {
        String query = "SELECT * FROM students WHERE CIN = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, CIN);
            ResultSet resultset = preparedStatement.executeQuery();

            return resultset.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateQuery(Connection connectDB, StudentModel studentModel) {
        String query = "UPDATE students " +
                       "SET fullName = ?, " +
                       "classe = ?, " +
                       "filiere = ? " +
                       "WHERE CIN = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, studentModel.getFullName());
            preparedStatement.setString(2, studentModel.getClasse());
            preparedStatement.setString(3, studentModel.getFiliere());
            preparedStatement.setString(4, studentModel.getCIN());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuery(Connection connectDB, String CIN) {
        String query = "DELETE FROM students WHERE CIN = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, CIN);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
