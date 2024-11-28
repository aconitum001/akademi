package models;

public class SubjectModel {
    private int ID;
    private String label;
    private double coefficient;
    private int filiereId;


    public SubjectModel(int ID, String label, double coefficient, int filiereId) {
        this.ID = ID;
        this.label = label;
        this.coefficient = coefficient;
        this.filiereId = filiereId;
    }


    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getFiliereId() {
        return this.filiereId;
    }

    public void setFiliereId(int filiereId) {
        this.filiereId = filiereId;
    }

    @Override
    public String toString() {
        return "{" +
            " ID='" + getID() + "'" +
            ", label='" + getLabel() + "'" +
            ", coefficient='" + getCoefficient() + "'" +
            ", filiereId='" + getFiliereId() + "'" +
            "}";
    }


}
