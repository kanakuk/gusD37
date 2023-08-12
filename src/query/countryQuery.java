package query;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**Group of query methods related to countries.*/
public class countryQuery {

    /**Database query to grab data from the country table .*/
    public static ObservableList<countryModel>
    getAllCountries() throws SQLException {
        ObservableList<countryModel> countryList =
                FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().prepareStatement
                ("SELECT * FROM countries;");
        try{
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                countryModel country = new countryModel(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"));
                countryList.add(country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryList;
    }

    /**Pulls countries by associated column "countryID'
     @param country .*/
    public static countryModel pullCountryID(String country)
            throws SQLException {
        PreparedStatement ps = JDBC.getConnection().prepareStatement
                ("SELECT * FROM countries WHERE Country = ?;");
        ps.setString(1, country);
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                countryModel country3 = new countryModel(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"));
                return country3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**Pulls appointments by associated column "country'.*/
    public static ObservableList<locationModel>
    getAppointmentsByLocation() {
        ObservableList<locationModel> appointmentByCountry =
                FXCollections.observableArrayList();
        try {
            String sql = "SELECT Location as 'Appointment_Location' , " +
                    "COUNT(*) AS 'Total_Appointments' " +
                    "from appointments group by Location;";
            PreparedStatement ps = JDBC.getConnection().
                    prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String location = rs.getString("Appointment_Location");
                int total = rs.getInt("Total_Appointments");
                locationModel appointments = new locationModel(total, location);
                appointmentByCountry.add(appointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentByCountry;
    }

}



























































