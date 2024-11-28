package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import Database.DatabaseConnection;
import Database.StudentDAO;
import helper.CustomAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.FiliereModel;
import models.StudentModel;

public class StudentSceneController implements Initializable {

    @FXML
    private TextField addCINTextField;
    @FXML
    private TextField addFullNameTextField;
    @FXML
    private ChoiceBox<String> addClasseChoiceBox;
    @FXML
    private ChoiceBox<String> addFiliereChoiceBox;

    @FXML
    private TextField updateCINTextField;
    @FXML
    private TextField updateFullNameTextField;
    @FXML
    private ChoiceBox<String> updateClasseChoiceBox;
    @FXML
    private ChoiceBox<String> updateFiliereChoiceBox;

    @FXML
    private TextField deleteCINTextField;

    private ArrayList<String> classList = new ArrayList<>(Arrays.asList("1ère Licence", "2ème Licence", "3ème Licence"));
    private ArrayList<FiliereModel> filiereList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addClasseChoiceBox.getItems().addAll(classList);
        updateClasseChoiceBox.getItems().addAll(classList);

        updateClasseChoiceBox.setValue("Choisir une classe");
        addClasseChoiceBox.setValue("Choisir une classe");
        filiereList = getFiliereList();
        addFiliereChoiceBox.setValue("Choisir une filière");
        updateFiliereChoiceBox.setValue("Choisir une filière");
        filiereList.forEach(filiereModel -> addFiliereChoiceBox.getItems().add(filiereModel.getFiliereName()));
        filiereList.forEach(filiereModel -> updateFiliereChoiceBox.getItems().add(filiereModel.getFiliereName()));


    }

    public void addStudentMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        StudentModel studentModel = getAddStudentData();

        if (studentModel.isBlank()) {
            CustomAlerts.showInformationAlert("Champs manquants","Veuillez remplir tous les champs requis.");
            return;
        }

        if (!studentModel.isValidCIN()) {
            CustomAlerts.showErrorAlert("CIN invalide", "Le CIN doit comporter exactement 8 chiffres.");
            return;
        }

        StudentDAO studentDAO = new StudentDAO();

        if (studentDAO.isFoundQuery(connectDB, studentModel.getCIN())) {
            CustomAlerts.showErrorAlert("Etudiant déjà existant", "Cet étudiant est déjà présent dans la base de données.\n");
        } else {
            studentDAO.addQuery(connectDB, studentModel);
            CustomAlerts.showInformationAlert("Ajout réussi", "L'étudiant a été ajouté avec succès.");
            clearAllAddFields();
        }        
    }

    public void updateStudentMethode() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        StudentModel studentModel = getUpdateStudentData();

        if (studentModel.isBlank()) {
            CustomAlerts.showInformationAlert("Champs manquants","Veuillez remplir tous les champs requis.");
            return;
        }

        if (!studentModel.isValidCIN()) {
            CustomAlerts.showErrorAlert("CIN invalide", "Le CIN doit comporter exactement 8 chiffres.");
            return;
        }

        StudentDAO studentDAO = new StudentDAO();

        if (studentDAO.isFoundQuery(connectDB, studentModel.getCIN())) {
            studentDAO.updateQuery(connectDB, studentModel);
            CustomAlerts.showInformationAlert("Mise à jour réussie", "Les informations de l'étudiant ont été mises à jour avec succès.");
            clearAllUpdateFields();
        } else {
            CustomAlerts.showErrorAlert("CIN introuvable", "Aucun étudiant correspondant au CIN saisi n'a été trouvé. Veuillez vérifier et réessayer.");
        }

    }

    public void deleteStudentMethode() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String CIN = deleteCINTextField.getText();

        if (!(CIN.length() == 8 && CIN.matches("\\d{8}"))) {
            CustomAlerts.showErrorAlert("CIN invalide", "Le CIN doit comporter exactement 8 chiffres.");
            return;
        }

        StudentDAO studentDAO = new StudentDAO();

        if (studentDAO.isFoundQuery(connectDB, CIN)) {
            studentDAO.deleteQuery(connectDB, CIN);
            CustomAlerts.showInformationAlert("Suppression réussie" , "L'étudiant a été supprimé avec succès de la base de données.");
            deleteCINTextField.clear();
        } else {
            CustomAlerts.showErrorAlert("CIN introuvable", "Aucun étudiant correspondant au CIN saisi n'a été trouvé. Veuillez vérifier et réessayer.");
        }
    }

    public void clearAllUpdateFields() {
        updateCINTextField.clear();
        updateFullNameTextField.clear();
        updateClasseChoiceBox.setValue("Choisir une classe");
        updateFiliereChoiceBox.setValue("Choisir une filière");
    }

    public void clearAllAddFields() {
        addCINTextField.clear();
        addFullNameTextField.clear();
        addClasseChoiceBox.setValue("Choisir une classe");
        addFiliereChoiceBox.setValue("Choisir une filière");
    }

    public StudentModel getUpdateStudentData() {
        String CIN = updateCINTextField.getText();
        String fullName = updateFullNameTextField.getText();
        String classe = updateClasseChoiceBox.getValue();
        String filiere = updateFiliereChoiceBox.getValue();

        return new StudentModel(CIN, fullName, classe, filiere);
    }


    public StudentModel getAddStudentData() {
        String CIN = addCINTextField.getText();
        String fullName = addFullNameTextField.getText();
        String classe = addClasseChoiceBox.getValue();
        String filiere = addFiliereChoiceBox.getValue();

        return new StudentModel(CIN, fullName, classe, filiere);
    }

    public ArrayList<FiliereModel> getFiliereList() {
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
}
