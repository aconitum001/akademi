package controllers;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Database.DatabaseConnection;
import Database.SubjectDAO;
import helper.CustomAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import models.FiliereModel;
import models.SubjectModel;

public class SubjectSceneController implements Initializable{
    @FXML
    private TextField addSubjectLabelTextField;
    @FXML
    private TextField addSubjectCoeffTextField;
    @FXML
    private ChoiceBox<String> addFiliereChoiceBox;
    private ArrayList<FiliereModel> filiereList;

    @FXML
    private TextField updateSubjectLabelTextField;
    @FXML
    private TextField updateSubjectCoeffTextField;
    @FXML
    private ChoiceBox<String> updateFiliereChoiceBox;

    @FXML
    private TextField deleteSubjectLabelTextField;
    @FXML
    private ChoiceBox<String> deleteFiliereChoiceBox;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addFiliereChoiceBox.setValue("Choisir une filière");
        updateFiliereChoiceBox.setValue("Choisir une filière");
        deleteFiliereChoiceBox.setValue("Choisir une filière");

        filiereList = FiliereModel.getFiliereList();
        ArrayList<String> items = new ArrayList<>();
        filiereList.forEach(filiereModel -> items.add(filiereModel.getFiliereName()));
        addFiliereChoiceBox.getItems().addAll(items);
        updateFiliereChoiceBox.getItems().addAll(items);
        deleteFiliereChoiceBox.getItems().addAll(items);
    }

    public void addSubjectMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        SubjectModel subjectModel = getAddSubjectData();

        if (subjectModel.isBlank()) {
            CustomAlerts.showInformationAlert("Champs manquants","Veuillez remplir tous les champs requis.");
            return;
        }

        if (subjectModel.getCoefficient() <= 0) {
            CustomAlerts.showErrorAlert(
                "Coefficient invalide",
                "Le coefficient doit être un nombre positif.\nVeuillez saisir une valeur correcte."
            );
            return;
        }

        SubjectDAO subjectDAO = new SubjectDAO();

        if (subjectDAO.isFoundQuery(connectDB, subjectModel.getLabel(),subjectModel.getFiliereId())) {
            CustomAlerts.showErrorAlert(
    "Matière déjà existante", 
    "Cette matière est déjà présente dans la base de données.\nVeuillez vérifier votre saisie.");
        } else {
            subjectDAO.addQuery(connectDB, subjectModel);
            CustomAlerts.showInformationAlert("Matière ajoutée", "La matière a été ajoutée avec succès à la base de données.");
            clearAddSubjectFields();
        }
    }

    public void updateSubjectMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
    
        SubjectModel subjectModel = getUpdateSubjectData();
    
        if (subjectModel.isBlank()) {
            CustomAlerts.showInformationAlert("Champs manquants", "Veuillez remplir tous les champs requis.");
            return;
        }

        if (subjectModel.getCoefficient() <= 0) {
            CustomAlerts.showErrorAlert(
                "Coefficient invalide",
                "Le coefficient doit être un nombre positif.\nVeuillez saisir une valeur correcte."
            );
            return;
        }
    
        SubjectDAO subjectDAO = new SubjectDAO();
    
        if (!subjectDAO.isFoundQuery(connectDB, subjectModel.getLabel(), subjectModel.getFiliereId())) {
            CustomAlerts.showErrorAlert(
                "Matière introuvable",
                "La matière spécifiée n'existe pas dans la base de données.\nVeuillez vérifier votre saisie."
            );
        } else {
            subjectDAO.updateQuery(connectDB, subjectModel);
            CustomAlerts.showInformationAlert("Matière mise à jour", "La matière a été mise à jour avec succès.");
            clearAddSubjectFields();
        }
    }
    
    public void deleteSubjectMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
    
        String label = deleteSubjectLabelTextField.getText();
        String filiere = deleteFiliereChoiceBox.getValue();

        if (label.isBlank() || filiere.equals("Choisir une filière")) {
            CustomAlerts.showInformationAlert("Champs manquants", "Veuillez remplir tous les champs requis.");
            return;
        }

        int filiereId = -1;
        for (FiliereModel filiereModel : filiereList) {
            if (filiereModel.getFiliereName().equals(filiere)) {
                filiereId = filiereModel.getFiliereId();
                break;
            }
        }

        SubjectDAO subjectDAO = new SubjectDAO();
    
        if (!subjectDAO.isFoundQuery(connectDB, label, filiereId)) {
            CustomAlerts.showErrorAlert(
                "Matière introuvable",
                "La matière spécifiée n'existe pas dans la base de données.\nVeuillez vérifier votre saisie."
            );
        } else {
            subjectDAO.deleteQuery(connectDB, label, filiereId);
            CustomAlerts.showInformationAlert("Matière supprimée", "La matière a été supprimée avec succès.");
            
        }
    }

    public void clearAddSubjectFields() {
        addSubjectCoeffTextField.clear();
        addSubjectLabelTextField.clear();
        addFiliereChoiceBox.setValue("Choisir une filière");
    }

    public void clearUpdateSubjectFields() {
        updateSubjectCoeffTextField.clear();
        updateSubjectLabelTextField.clear();
        updateFiliereChoiceBox.setValue("Choisir une filière");
    }

    public void clearDeleteSubjectFields() {
        deleteFiliereChoiceBox.setValue("Choisir une filière");
        deleteSubjectLabelTextField.clear();
    }

    public SubjectModel getUpdateSubjectData() {
        String label = updateSubjectLabelTextField.getText();
        double coeff;
        try {
            coeff = Double.parseDouble(updateSubjectCoeffTextField.getText());
        } catch (NumberFormatException e) {
            coeff = -43;
        }
        String filiereName = updateFiliereChoiceBox.getValue();
        int filiereId = -1;
        for (FiliereModel filiereModel : filiereList) {
            if (filiereModel.getFiliereName().equals(filiereName)) {
                filiereId = filiereModel.getFiliereId();
                break;
            }
        }

        return new SubjectModel(-1,label, coeff, filiereId);
    }
    public SubjectModel getAddSubjectData() {
        String label = addSubjectLabelTextField.getText();
        double coeff;
        try {
            coeff = Double.parseDouble(addSubjectCoeffTextField.getText());
        } catch (NumberFormatException e) {
            coeff = -43;
        }
        String filiereName = addFiliereChoiceBox.getValue();
        int filiereId = -1;
        for (FiliereModel filiereModel : filiereList) {
            if (filiereModel.getFiliereName().equals(filiereName)) {
                filiereId = filiereModel.getFiliereId();
                break;
            }
        }

        return new SubjectModel(-1,label, coeff, filiereId);
        
    }


}
