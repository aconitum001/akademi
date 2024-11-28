package controllers;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import Database.DatabaseConnection;
import helper.CustomAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class LoginController {
    

    @FXML 
    private TextField emailTextField;
    @FXML
    private TextField passwordTextField;
    

    private Parent root;
    private Stage stage;
    private Scene scene;

    public void loginButton(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        if (email.isBlank() || password.isBlank()) 
        {
            CustomAlerts.showInformationAlert("Champs manquants","Veuillez remplir tous les champs requis.");
            return;
        }
        
        String connectQuery = "SELECT * FROM akademi.users WHERE email = '"+ email +"' AND password = '"+ password +"'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            
            if (queryOutput.next()) {
                switchToMainScene(event, queryOutput);

                emailTextField.clear();
                passwordTextField.clear();
            } else {
                CustomAlerts.showErrorAlert("Erreur de connexion", "Utilisateur non trouvé. Veuillez vérifier vos informations.");
            }
    
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    
    public void switchToMainScene(ActionEvent event,ResultSet queryOutput) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/MainScene.fxml"));
        root = loader.load();
        
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Set the stage size to match the screen dimensions
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
