package query;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Group of query methods related to the customer.*/
public class customerQuery {
    /**Database query to grab data from the customer table .*/
    public static ObservableList<customerModel>
    getAllCustomers() {
        ObservableList<customerModel> customerList =
                FXCollections.observableArrayList();
        try {
            String sql = ("SELECT * FROM customers AS cust " +
                    "INNER JOIN first_level_divisions AS divisions " +
                    "ON cust.Division_ID = divisions.Division_ID " +
                    "INNER JOIN countries AS country " +
                    "ON country.Country_ID = divisions.Country_ID;");
            PreparedStatement ps =
                    JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                customerModel customer = new customerModel(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),

                        rs.getString("Division"),
                        rs.getString("Country"),
                        rs.getInt("division_ID"),
                        rs.getInt("country_ID"));
                    customerList.add(customer);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return customerList;
            }

    /**Creates a new customer in the customer table.
     @param division
     @param postalCode
     @param phone
     @param address
     @param customerName
     */
    public static void createCustomer(String customerName,
                                      String address,
                                      String postalCode,
                                      String phone,
                                      String division)
            throws SQLException {
        divisionModel divisionModel2 = divisionQuery.pullDivisionID(division);
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement
                    ("INSERT INTO customers " +
                            "(Customer_Name, " +
                            "Address, " +
                            "Postal_Code, " +
                            "Phone, " +
                            "Division_ID) " +
                            "VALUES (?, ?, ?, ?, ?);");
            ps.setString(1, customerName);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phone);
            ps.setInt(5, divisionModel2.getDivisionID());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Modifies an existing customer in the customer table.
     @param address
     @param postalCode
     @param division
     @param customerID
     @param name
     @param phoneNumber
     */
    public static boolean modifySaveCustomer(
            String customerID,
            String name,
            String address,
            String postalCode,
            String phoneNumber,
            String division) throws SQLException {
        divisionModel newDivision = divisionQuery.pullDivisionID(division);
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement
                    ("UPDATE customers SET " +
                            "Customer_Name=?, " +
                            "Address=?, " +
                            "Postal_Code=?, " +
                            "Phone=?, " +
                            "Division_ID=? " +
                            "WHERE Customer_ID = ?;");
            //ps.setString(1, customerID);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNumber);
            ps.setString(5, String.valueOf(newDivision.getDivisionID()));
            ps.setString(6, customerID);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }



}



































































