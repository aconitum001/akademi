package models;


public class StudentModel {
    private String CIN;
    private String fullName;
    private String classe;
    private String filiere;

    public StudentModel(String CIN, String fullName, String classe, String filiere) {
        this.CIN = CIN;
        this.fullName = fullName;
        this.classe = classe;
        this.filiere = filiere;
    }

    public boolean isBlank() {
        return CIN.isBlank() || fullName.isBlank() || classe.equals("Choisir une classe") || filiere.equals("Choisir une filière");
    }

    public boolean isValidCIN() {
        return CIN.length() == 8 && CIN.matches("\\d{8}");
    }

    public String getCIN() {
        return this.CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getClasse() {
        return this.classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getFiliere() {
        return this.filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    @Override
    public String toString() {
        return "{" +
            " CIN='" + getCIN() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", classe='" + getClasse() + "'" +
            ", filiere='" + getFiliere() + "'" +
            "}";
    }


}
