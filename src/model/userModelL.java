package model;

/**.*/
public class userModelL {
    private static int userId;
    private static String username;
    private String password;

    /**Constructor for the userModelL.
     @param user_id
     @param user_name
     @param password
     */
    public userModelL(int user_id, String user_name, String password) {
    }

    /**Empty constructor for userModelL*/
    public userModelL() {}

    /**Getter for the userId.
     @return userId.*/
    public static int getUserId() {
        return userId;
    }
    /**Setter for the userId.
     @param userId */
    public void setUserId(int userId) {
        this.userId = userId;
    }


    /**Getter for the username.
     @return username.*/
    public static String getUsername() {
        return username;
    }
    /**Setter for the username.
     @param username */
    public void setUsername(String username) {
        this.username = username;
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
