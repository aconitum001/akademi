package models;

public class TeacherModel {
    
    private String CIN;
    private String fullName;
    private String phoneNumber;
    private String profession;

    public TeacherModel(String CIN, String fullName, String phoneNumber, String profession) {
        this.CIN = CIN;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.profession = profession;
    }

    public boolean checkBlank() {
        return CIN.isBlank() || fullName.isBlank() || phoneNumber.isBlank() || profession.isBlank();
    }

    public boolean isValidPhoneNumber() {
        return phoneNumber.length() == 8 && phoneNumber.matches("\\d{8}");
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
  



    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfession() {
        return this.profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "{" +
            " CIN='" + getCIN() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", profession='" + getProfession() + "'" +
            "}";
    }

    
}
