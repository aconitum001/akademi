package controllers;

import java.io.IOException;
import java.sql.Connection;


import Database.DatabaseConnection;
import Database.TeacherDAO;
import helper.CustomAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import models.TeacherModel;

public class MainSceneController {
    

    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    @FXML
    private TextField CINTextField;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField tlphTextField;
    @FXML
    private TextField professionTextField;

    @FXML
    private TextField updateCINTextField;
    @FXML
    private TextField updateFullNameTextField;
    @FXML
    private TextField updateTlphTextField;
    @FXML
    private TextField updateProfessionTextField;

    @FXML
    private TextField deleteCINTextField;

    
    public void addTeacherMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        TeacherModel teacherModel = getAddTeacherData();
        

        if (teacherModel.checkBlank()) {
            CustomAlerts.showInformationAlert("Champs manquants","Veuillez remplir tous les champs requis.");
            return;
        }
        if (!teacherModel.isValidPhoneNumber()) {
            CustomAlerts.showErrorAlert("Numéro de téléphone", "Le numéro doit comporter 8 chiffres.");
            return;
        }
        if (!teacherModel.isValidCIN()) {
            CustomAlerts.showErrorAlert("CIN invalide", "Le CIN doit comporter exactement 8 chiffres.");
            return;
        }

        TeacherDAO teacherDAO = new TeacherDAO();

        if (teacherDAO.isFoundQuery(connectDB, teacherModel.getCIN())) {
            CustomAlerts.showErrorAlert("Enseignant déjà existant", "Cet enseignant est déjà présent dans la base de données.\n");
        } else {
            teacherDAO.addQuery(connectDB, teacherModel);
            CustomAlerts.showInformationAlert("Ajout réussi", "L'enseignant a été ajouté avec succès.");
            clearAllAddFields();
        }     
    }

    public void updateTeacherMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        TeacherModel teacherModel = getUpdateTeacherData();

        if (teacherModel.checkBlank()) {
            CustomAlerts.showInformationAlert("Champs manquants","Veuillez remplir tous les champs requis.");
            return;
        }
        if (!teacherModel.isValidPhoneNumber()) {
            CustomAlerts.showErrorAlert("Numéro de téléphone", "Le numéro doit comporter 8 chiffres.");
            return;
        }
        if (!teacherModel.isValidCIN()) {
            CustomAlerts.showErrorAlert("CIN invalide", "Le CIN doit comporter exactement 8 chiffres.");
            return;
        }

        TeacherDAO teacherDAO = new TeacherDAO();

        if (teacherDAO.isFoundQuery(connectDB, teacherModel.getCIN())) {
            teacherDAO.updateQuery(connectDB, teacherModel);
            CustomAlerts.showInformationAlert("Mise à jour réussie", "Les informations de l'enseignant ont été mises à jour avec succès.");
            clearAllUpdateFields();
        } else {
            CustomAlerts.showErrorAlert("CIN introuvable", "Aucun enseignant correspondant au CIN saisi n'a été trouvé. Veuillez vérifier et réessayer.");
        }
    }

    public void deleteTeacherMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String CIN = deleteCINTextField.getText();

        if (!(CIN.length() == 8 && CIN.matches("\\d{8}"))) {
            CustomAlerts.showErrorAlert("CIN invalide", "Le CIN doit comporter exactement 8 chiffres.");
            return;
        }

        TeacherDAO teacherDAO = new TeacherDAO();

        if (teacherDAO.isFoundQuery(connectDB, CIN)) {
            teacherDAO.deleteQuery(connectDB, CIN);
            CustomAlerts.showInformationAlert("Suppression réussie" , "L'enseignant a été supprimé avec succès de la base de données.");
            deleteCINTextField.clear();
        } else {
            CustomAlerts.showErrorAlert("CIN introuvable", "Aucun enseignant correspondant au CIN saisi n'a été trouvé. Veuillez vérifier et réessayer.");
        }

    }



    

    public void clearAllAddFields() {
        CINTextField.clear();
        fullNameTextField.clear();
        tlphTextField.clear();
        professionTextField.clear();
    }

    public void clearAllUpdateFields() {
        updateCINTextField.clear();
        updateFullNameTextField.clear();
        updateTlphTextField.clear();
        updateProfessionTextField.clear();
    }

    public TeacherModel getUpdateTeacherData() {
        String CIN = updateCINTextField.getText();
        String fullName = updateFullNameTextField.getText();
        String phoneNumber = updateTlphTextField.getText();
        String profession = updateProfessionTextField.getText();

        return new TeacherModel(CIN, fullName, phoneNumber, profession);
    }

    public TeacherModel getAddTeacherData() {
        String CIN = CINTextField.getText();
        String fullName = fullNameTextField.getText();
        String phoneNumber = tlphTextField.getText();
        String profession = professionTextField.getText();

        return new TeacherModel(CIN, fullName, phoneNumber, profession);
    }

    public void page1(ActionEvent event) throws IOException 
    {
        bp.setCenter(ap);
    }

    public void page2(ActionEvent event) throws IOException 
    {
        loadPage("StudentsScene");
    }

    public void page3(ActionEvent event) throws IOException 
    {
        loadPage("SubjectsScene");
    }

    public void page4(ActionEvent event) throws IOException 
    {
        loadPage("NotesScene");
    }

    public void page5(ActionEvent event) throws IOException 
    {
        loadPage("TeacherScene");
    }

    private void loadPage(String page) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../views/" + page + ".fxml"));
    
        // Ensure dynamic resizing
        bp.setCenter(root);
    
    }
}
