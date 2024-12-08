package controllers;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Database.DatabaseConnection;
import Database.NotesDAO;
import Database.StudentDAO;
import Database.SubjectDAO;
import helper.CustomAlerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.NoteModel;
import models.StudentModel;
import models.SubjectModel;

public class NotesSceneController implements Initializable{

    @FXML
    private TextField addCINTextField;

    @FXML
    private ChoiceBox<String> addMatiereChoiceBox;

    @FXML
    private TextField addNoteTextField;

    @FXML
    private Label classeLabel;

    @FXML
    private Label filiereLabel;

    @FXML
    private Label fullNameLabel;

    @FXML
    private Label nbNotesLabel;

    @FXML
    private ChoiceBox<String> updateMatiereChoiceBox;

    @FXML
    private ChoiceBox<String> deleteMatiereChoiceBox;

    @FXML
    private TextField updateNoteTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;
    


    private ArrayList<SubjectModel> subjectsList;
    private StudentModel student;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        updateButton.setDisable(true);
    }

    public void findStudentMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String CIN = addCINTextField.getText();

        if (CIN.isBlank()) {
            CustomAlerts.showInformationAlert("CIN Manquant", "Veuillez saisir le CIN de l'étudiant avant de continuer.");
            return;
        }

        if (!(CIN.length() == 8 && CIN.matches("\\d{8}"))) {
            CustomAlerts.showErrorAlert("CIN invalide", "Le CIN doit comporter exactement 8 chiffres.");
            return;
        }

        StudentDAO studentDAO = new StudentDAO();
        SubjectDAO subjectDAO = new SubjectDAO();

        if (studentDAO.isFoundQuery(connectDB, CIN)) {
            student  = studentDAO.getStudentQuery(connectDB, CIN);
            updateStudentLabels(student);
            updateNotesCountLabel(connectDB);
            subjectsList = subjectDAO.getSubjectsQuery(connectDB, student.getFiliere());
            addButton.setDisable(false);
            updateButton.setDisable(false);
            deleteButton.setDisable(false);
            fillChoiceBoxes(connectDB,CIN,student.getFiliere());
        } else {
            CustomAlerts.showErrorAlert("CIN Introuvable", "Aucun étudiant correspondant au CIN saisi n'a été trouvé. Veuillez vérifier et réessayer.");
        }
    }



    private void fillChoiceBoxes(Connection connectDB,String CIN,String filierName) {
        NotesDAO notesDAO = new NotesDAO();
        ArrayList<String> items = notesDAO.getRestStudentSubjectsName(connectDB, CIN, filierName);
        if (items.isEmpty()) {
            addMatiereChoiceBox.setValue("pas des matiers a ajouter");
            addButton.setDisable(true);
        } else {
            addMatiereChoiceBox.setValue("Choisir un matiére");
            addMatiereChoiceBox.getItems().setAll(items);
            addButton.setDisable(false);
        }
        
        ArrayList<String> studentSubjectsList = notesDAO.getStudentSubjectName(connectDB,CIN);

        if (studentSubjectsList.isEmpty()) {
            updateMatiereChoiceBox.setValue("Aucune matiére a modifier");
            deleteMatiereChoiceBox.setValue("Aucune matiére a supprimer");
            updateMatiereChoiceBox.getItems().setAll(studentSubjectsList);
            deleteMatiereChoiceBox.getItems().setAll(studentSubjectsList);
            deleteButton.setDisable(true);
            updateButton.setDisable(true);
        } else {
            deleteMatiereChoiceBox.setValue("Choisir un matiére");
            updateMatiereChoiceBox.setValue("Choisir un matiére");
            updateMatiereChoiceBox.getItems().setAll(studentSubjectsList);
            deleteMatiereChoiceBox.getItems().setAll(studentSubjectsList);
            deleteButton.setDisable(false);
            updateButton.setDisable(false);
        }
    
    }

    

    public void addStudentNoteMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        if (addNoteTextField.getText().isBlank() || addMatiereChoiceBox.getValue().equals("Choisir un matiére")) {
            CustomAlerts.showErrorAlert("Champs Manquants", "Veuillez entrer une note valide et sélectionner une matière.");
            return;
        }
        try {
            Double.parseDouble( addNoteTextField.getText());
        } catch(NumberFormatException e) {
            CustomAlerts.showErrorAlert("Entrée Invalide", "Veuillez saisir uniquement des chiffres pour ce champ.");
            return;
        }

        NoteModel noteModel =  getAddFieldsData();
        
        if (!noteModel.isValidNote()) {
            CustomAlerts.showErrorAlert("Note Invalide", "Veuillez saisir une note valide. La note doit être un nombre compris entre 0 et 20.");
            return;
        }

        NotesDAO notesDAO = new NotesDAO();

        if (notesDAO.isFoundQuery(connectDB, noteModel.getStudentCIN(),noteModel.getMatierID())) {
            CustomAlerts.showErrorAlert("Note Déjà Attribuée", "Cet étudiant a déjà une note enregistrée pour cette matière. Veuillez vérifier les informations saisies ou modifier la note existante.");
        } else {
            notesDAO.addQuery(connectDB, noteModel);
            CustomAlerts.showInformationAlert("Ajout Réussi", "La note a été ajoutée avec succès pour l'étudiant dans cette matière.");
            clearAddFields();
            updateNotesCountLabel(connectDB);
            fillChoiceBoxes(connectDB, student.getCIN(),student.getFiliere());
        }
    }

    public void updateStudentNotesMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
    

        if (updateNoteTextField.getText().isBlank() || updateMatiereChoiceBox.getValue().equals("Choisir un matiére")) {
            CustomAlerts.showErrorAlert("Champs Manquants", "Veuillez entrer une note valide et sélectionner une matière.");
            return;
        }

        try {
            Double.parseDouble(updateNoteTextField.getText());
        } catch(NumberFormatException e) {
            CustomAlerts.showErrorAlert("Entrée Invalide", "Veuillez saisir uniquement des chiffres pour ce champ.");
            return;
        }

        NoteModel noteModel = getUpdateFieldsData();

        if (!noteModel.isValidNote()) {
            CustomAlerts.showErrorAlert("Note Invalide", "Veuillez saisir une note valide. La note doit être un nombre compris entre 0 et 20.");
            return;
        }

        NotesDAO notesDAO = new NotesDAO();

        if (notesDAO.isFoundQuery(connectDB, noteModel.getStudentCIN(), noteModel.getMatierID())) {
            notesDAO.updateQuery(connectDB, noteModel);
            CustomAlerts.showInformationAlert("Mise à Jour Réussie", "La note de l'étudiant a été mise à jour avec succès.");
        }

    }

    public void deleteStudentSubjectMethode(ActionEvent event) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String deleteSubject = deleteMatiereChoiceBox.getValue();

        if (deleteSubject.equals("Choisir un matiére")) {
            CustomAlerts.showErrorAlert("Champs Manquants", "Veuillez sélectionner une matière.");
            return;
        }

        NotesDAO notesDAO = new NotesDAO();

        int subjectID = -1;
        for (SubjectModel subjectModel : subjectsList) {
            if (subjectModel.getLabel().equals(deleteSubject)) {
                subjectID = subjectModel.getID();
            }
        }

        notesDAO.deleteQuery(connectDB, student.getCIN(), subjectID);
        updateNotesCountLabel(connectDB);
        fillChoiceBoxes(connectDB, student.getCIN(), student.getFiliere());
    }

    private void updateStudentLabels(StudentModel student) {
        fullNameLabel.setText(student.getFullName());
        classeLabel.setText(student.getClasse());
        filiereLabel.setText(student.getFiliere());
    } 

    private void updateNotesCountLabel(Connection connectDB) {
        NotesDAO notesDAO = new NotesDAO();
        int notesCount = notesDAO.getNotesCount(connectDB, student.getCIN());
        nbNotesLabel.setText(Integer.toString(notesCount));
    }

    private void clearAddFields() {
        addNoteTextField.clear();
        addMatiereChoiceBox.setValue("Choisir un matiére");
    }

    private NoteModel getAddFieldsData() {
        double studentNote = Double.parseDouble(addNoteTextField.getText());
        String subjectName = addMatiereChoiceBox.getValue();
        int subjectID = -1;
        for (SubjectModel subjectModel : subjectsList) {
            if (subjectModel.getLabel().equals(subjectName)) {
                subjectID = subjectModel.getID();
            }
        }
        return new NoteModel(student.getCIN(), subjectID, studentNote);
    }

    private NoteModel getUpdateFieldsData() {
        double studentNote = Double.parseDouble(updateNoteTextField.getText());
        String subjectName = updateMatiereChoiceBox.getValue();
        int subjectID = -1;
        for (SubjectModel subjectModel : subjectsList) {
            if (subjectModel.getLabel().equals(subjectName)) {
                subjectID = subjectModel.getID();
            }
        }
        return new NoteModel(student.getCIN(), subjectID, studentNote);
    }



   

}
