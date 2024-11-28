package models;

public class UserModel {
    
    private int userId;
    private String userName;
    private String email;
    private String password;

    public UserModel(int userId, String userName, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "{" +
            " userId='" + getUserId() + "'" +
            ", userName='" + getUserName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }

}
