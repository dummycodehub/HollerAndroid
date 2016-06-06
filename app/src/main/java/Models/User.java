package Models;

/**
 * Created by rakeshkoplod on 28/10/15.
 */
public class User {

    private String token;
    private String userID;
    private String email;
    private String phoneNumber;
    private String fullName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public User(String email, String fullName, String phoneNumber, String token, String userID) {

        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.token = token;
        this.userID = userID;
    }
}
