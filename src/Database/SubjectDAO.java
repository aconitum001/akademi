package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.SubjectModel;

import java.sql.ResultSet;

public class SubjectDAO {
    

    public boolean isFoundQuery(Connection connectDB, String label) {
        String query = "SELECT * FROM matiers WHERE labe = ?";

        try (PreparedStatement preparedStatement =  connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, label);
            ResultSet resultset = preparedStatement.executeQuery();
            return resultset.next();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addQuery(Connection connectDB, SubjectModel subjectModel) {
        String query = "INSERT INTO matiers (label, coeff, filierID) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement =  connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, subjectModel.getLabel());
            preparedStatement.setDouble(2, subjectModel.getCoefficient());
            preparedStatement.setInt(3, subjectModel.getFiliereId());
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuery(Connection connectDB, SubjectModel subjectModel) { 
        String query = "UPDATE matiers " +
                       "SET coeff = ?, " +
                       "filierID = ?, " +
                       "WHERE label = ?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setDouble(1, subjectModel.getCoefficient());
            preparedStatement.setInt(2, subjectModel.getFiliereId());
            preparedStatement.setString(3, subjectModel.getLabel());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteQuery(Connection connectDB, String label) {
        String query = "DELETE FROM matiers WHERE label = ? ";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, label);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
