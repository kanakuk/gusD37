package query;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.userModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Query related to the user.*/
public class userQuery {
    /**Database query to grab data from the user table .*/
    public static ObservableList<userModel>
    getUserList() {
        ObservableList<userModel> userList =
                FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users;";
            PreparedStatement ps =
                    JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                userModel user = new userModel(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"),
                        rs.getString("Password"));
                userList.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
}































