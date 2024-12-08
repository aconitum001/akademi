package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Database.DatabaseConnection;

public class FiliereModel {
    private int filiereId;
    private String filiereName;

    public FiliereModel(int filiereId, String filiereName) {
        this.filiereId = filiereId;
        this.filiereName = filiereName;
    }

    public static ArrayList<FiliereModel> getFiliereList() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String query = "SELECT * FROM filiers";
        ArrayList<FiliereModel> result = new ArrayList<>();

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int filierId = resultSet.getInt(1);
                String filierName = resultSet.getString(2);
                FiliereModel filiereModel = new FiliereModel(filierId, filierName);
                result.add(filiereModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int getFiliereId() {
        return this.filiereId;
    }

    public void setFiliereId(int filiereId) {
        this.filiereId = filiereId;
    }

    public String getFiliereName() {
        return this.filiereName;
    }

    public void setFiliereName(String filiereName) {
        this.filiereName = filiereName;
    }


    @Override
    public String toString() {
        return "{" +
            " filiereId='" + getFiliereId() + "'" +
            ", filiereName='" + getFiliereName() + "'" +
            "}";
    }
    
}
