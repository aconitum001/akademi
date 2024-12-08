package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;



import models.SubjectModel;

import java.sql.ResultSet;

public class SubjectDAO {
    

    public boolean isFoundQuery(Connection connectDB, String label, int filiereID) {
        String query = "SELECT * FROM matiers WHERE LOWER(label) = ? AND filierID = ?";

        try (PreparedStatement preparedStatement =  connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, label.toLowerCase());
            preparedStatement.setInt(2, filiereID);
            ResultSet resultset = preparedStatement.executeQuery();
            return resultset.next();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<SubjectModel> getSubjectsQuery(Connection connectDB , String filiereName) {
        String query = "SELECT * " +
                        "FROM matiers m, filiers f " +
                        "WHERE m.filierID = f.filierID " +
                        "AND filierName = ?";
        ArrayList<SubjectModel> result = new ArrayList<>();
        try (PreparedStatement preparedStatement =  connectDB.prepareStatement(query)) {
            preparedStatement.setString(1, filiereName);
            ResultSet resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                int subjectID = resultset.getInt("matierID");
                String label = resultset.getString("label");
                double coeff = resultset.getDouble("coeff");
                int filierID = resultset.getInt("filierID");
                SubjectModel subjectModel = new SubjectModel(subjectID,label, coeff, filierID);
                result.add(subjectModel);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
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
                   "SET coeff = ? " +
                   "WHERE LOWER(label) = ? AND filierID = ?";
        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setDouble(1, subjectModel.getCoefficient());
            preparedStatement.setString(2, subjectModel.getLabel());
            preparedStatement.setInt(3, subjectModel.getFiliereId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteQuery(Connection connectDB, String label, int filierID) {
        String query = "DELETE FROM matiers WHERE LOWER(label) = ? AND filierID = ?";

        try {
            PreparedStatement preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1, label);
            preparedStatement.setInt(2, filierID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
