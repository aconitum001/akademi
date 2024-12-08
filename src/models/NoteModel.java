package models;

public class NoteModel {
    private String studentCIN;
    private int matierID;
    private double note;




    public NoteModel(String studentCIN, int matierID, double note) {
        this.studentCIN = studentCIN;
        this.matierID = matierID;
        this.note = note;
    }

    public boolean isValidNote() {
        return note >= 0 && note <=20;
    }

    public String getStudentCIN() {
        return this.studentCIN;
    }

    public void setStudentCIN(String studentCIN) {
        this.studentCIN = studentCIN;
    }

    public int getMatierID() {
        return this.matierID;
    }

    public void setMatierID(int matierID) {
        this.matierID = matierID;
    }

    public double getNote() {
        return this.note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public NoteModel studentCIN(String studentCIN) {
        setStudentCIN(studentCIN);
        return this;
    }

    public NoteModel matierID(int matierID) {
        setMatierID(matierID);
        return this;
    }

    public NoteModel note(int note) {
        setNote(note);
        return this;
    }


    @Override
    public String toString() {
        return "{" +
            " studentCIN='" + getStudentCIN() + "'" +
            ", matierID='" + getMatierID() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }

    
}
