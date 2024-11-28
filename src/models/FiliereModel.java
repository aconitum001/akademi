package models;

public class FiliereModel {
    private int filiereId;
    private String filiereName;

    public FiliereModel(int filiereId, String filiereName) {
        this.filiereId = filiereId;
        this.filiereName = filiereName;
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
