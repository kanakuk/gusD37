package query;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.contactModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Group of query methods related to contacts.*/
public class contactQuery {

    /**Database query to grab columns from the customer table .*/
    public static ObservableList<contactModel>
    getContactList() {
        ObservableList<contactModel> contactList =
                FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts;";
            PreparedStatement ps =
                    JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                contactModel contact = new contactModel(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email"));
                contactList.add(contact);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return contactList;
    }

    /**Selects one contact out of the contact list.
     @param contactID */
    public static contactModel getContact(int contactID) {
        try {
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                contactModel contact = new contactModel(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email"));
                return contact;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}




























