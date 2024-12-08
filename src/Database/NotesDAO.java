package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.NoteModel;

public class NotesDAO {
    

    public int getNotesCount(Connection connectDB, String CIN) {
        String query = "SELECT count(*) FROM notes WHERE studentCIN = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, CIN);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<String> getStudentSubjectName(Connection connectDB, String CIN) {
        String query = "SELECT label FROM notes n, matiers m WHERE m.matierID = n.matierID AND studentCIN = ?";
        ArrayList<String> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, CIN);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getRestStudentSubjectsName(Connection connectDB, String CIN, String filierName) {
        String query = "SELECT label\n" + //
                        "FROM matiers m, filiers f\n" + //
                        "WHERE m.filierID = f.filierID\n" + //
                        "AND filierName = ?\n" + //
                        "AND matierID NOT IN (SELECT matierID FROM notes WHERE studentCIN = ?)";
        ArrayList<String> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, filierName);
            preparedStatement.setString(2, CIN);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addQuery(Connection connectDB, NoteModel noteModel) {
        String query = "INSERT INTO notes (studentCIN, matierID, note) VALUES (?,?,?)";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, noteModel.getStudentCIN());
            preparedStatement.setInt(2, noteModel.getMatierID());
            preparedStatement.setDouble(3, noteModel.getNote());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isFoundQuery(Connection connectDB, String studentCIN, int matierID) {
        String query = "SELECT * FROM notes WHERE studentCIN = ? AND matierID = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, studentCIN);
            preparedStatement.setInt(2, matierID);
            ResultSet resultSet =  preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateQuery(Connection connectDB, NoteModel noteModel) {
        String query = "UPDATE notes " +
                        "SET note = ? " +
                        "WHERE studentCIN = ? " +
                        "AND matierID = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setDouble(1,noteModel.getNote());
            preparedStatement.setString(2, noteModel.getStudentCIN());
            preparedStatement.setInt(3, noteModel.getMatierID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void deleteQuery(Connection connectDB,String studentCIN, int matierID) {
        String query = "DELETE FROM notes WHERE studentCIN = ? AND matierID = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, studentCIN);
            preparedStatement.setInt(2, matierID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
