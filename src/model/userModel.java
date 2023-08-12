package model;

/**.*/
public class userModel {
    private int userId;
    private String userName;
    private String password;

    /**Constructor for user model.
     @param userId
     @param userName
     @param password
     */
    public userModel(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**Getter for the userId.
     @return userId.*/
    public int getUserId() {
        return userId;
    }
    /**Setter for the userId.
     @param userId */
    public void setUserId(int userId) {
        this.userId = userId;
    }


    /**Getter for the userName.
     @return userName.*/
    public String getUserName() {
        return userName;
    }
    /**Setter for the userName.
     @param userName */
    public void setUserName(String userName) {
        this.userName = userName;
    }


    /**Getter for the password.
     @return password.*/
    public String getPassword() {
        return password;
    }
    /**Setter for the password.
     @param password */
    public void setPassword(String password) {
        this.password = password;
    }
}
