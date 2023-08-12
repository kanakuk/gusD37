package query;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.appointmentModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import model.*;
import query.*;

/**Group of query methods related to appointments.*/
public class appointmentQuery {
    /**Database query to grab data from the appointment table .*/
    public static ObservableList<appointmentModel>
    getAllAppointments(){ObservableList<appointmentModel>
            appointmentList =
                FXCollections.observableArrayList();
        try {
            String sql = "SELECT " +
                    "Appointment_ID, " +
                    "Title, " +
                    "Description, " +
                    "Location, " +
                    "Contact_ID, " +
                    "Type, " +
                    "Start, " +
                    "End, " +
                    "Customer_ID, " +
                    "User_ID " +
                    "FROM appointments";
            PreparedStatement ps =
                    JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contact = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                appointmentModel appointment = new appointmentModel(id,
                        title,
                        description,
                        location,
                        contact,
                        type,
                        start,
                        end,
                        customerID,
                        userID);
                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**Creates a new appointment in the appointment table.
     @param appointmentTitle
     @param customerID
     @param userID
     @param contact
     @param appointmentDescription
     @param appointmentEnd
     @param appointmentLocation
     @param appointmentStart
     @param appointmentType
     */
    public static void createNewAppointment(
            String appointmentTitle,
            String appointmentDescription,
            String appointmentLocation,
            int contact,
            String appointmentType,
            Timestamp appointmentStart,
            Timestamp appointmentEnd,
            int customerID,
            int userID) throws SQLException {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement
                    ("INSERT INTO appointments " +
                            "(Title, " +
                            "Description, " +
                            "Location, " +
                            "Contact_ID, " +
                            "Type, " +
                            "Start, " +
                            "End, " +
                            "Customer_ID, " +
                            "User_ID) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");

            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setInt(4, contact);
            ps.setString(5, appointmentType);
            ps.setTimestamp(6, appointmentStart);
            ps.setTimestamp(7, appointmentEnd);
            ps.setInt(8,customerID);
            ps.setInt(9, userID);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Modifies an existing appointment in the appointment table.
     @param appointmentStart
     @param appointmentLocation
     @param appointmentEnd
     @param appointmentDescription
     @param contact
     @param userID
     @param customerID
     @param appointmentTitle
     @param appointmentID
     @param appointmentType
     */
    public static boolean modifySaveAppointment(
            int appointmentID,
            String appointmentTitle,
            String appointmentDescription,
            String appointmentLocation,
            int contact,
            String appointmentType,
            Timestamp appointmentStart,
            Timestamp appointmentEnd,
            int customerID,
            int userID) throws SQLException {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement
                    ("UPDATE appointments SET " +
                            "Title=?, " +
                            "Description=?, " +
                            "Location=?, " +
                            "Contact_ID=?, " +
                            "Type=?, " +
                            "Start=?, " +
                            "End=?, " +
                            "Customer_ID=?, " +
                            "User_ID=? " +
                            "WHERE Appointment_ID = ?;");
            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setInt(4, contact);
            ps.setString(5, appointmentType);
            ps.setTimestamp(6, appointmentStart);
            ps.setTimestamp(7, appointmentEnd);
            ps.setInt(8, customerID);
            ps.setInt(9, userID);
            ps.setInt(10, appointmentID);
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**Deletes a selected appointment.*/
    public static boolean deleteAppointment(int appointmentID){
        try {
            String sql =
                    "DELETE FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, appointmentID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**Pulls appointments by associated column "customerID'.*/
    public static ObservableList<appointmentModel>
    pullAppointmentsByCustomerID(int customerID)
            throws SQLException {
        ObservableList<appointmentModel> appointments =
                FXCollections.observableArrayList();
        PreparedStatement ps = JDBC.getConnection().
                prepareStatement
                ("SELECT * FROM appointments " +
                        "AS a INNER JOIN contacts " +
                        "AS c ON a.Contact_ID = c.Contact_ID " +
                        "WHERE Customer_ID = ? ");
        ps.setInt(1, customerID);
        System.out.println(ps.toString());
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                appointmentModel apt = new appointmentModel(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"));
                appointments.add(apt);
            }
            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**Pulls appointments by the column "type'.*/
    public static ObservableList<typeModel> getAppointmentsByType() {
        ObservableList<typeModel> appointmentByTypeList =
                FXCollections.observableArrayList();
        try {
            String sql = "select Type as 'Appointment_Type' , " +
                    "COUNT(*) as 'Total_Appointments' " +
                    "from appointments group by Type;";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String type = rs.getString("Appointment_Type");
                int total = rs.getInt("Total_Appointments");
                typeModel appointments = new typeModel(type, total);
                appointmentByTypeList.add(appointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentByTypeList;
    }

    /**Pulls the appointments by the column 'month'.*/
    public static ObservableList<monthModel> getAppointmentsByMonth() {
        ObservableList<monthModel> appointmentByMonthList =
                FXCollections.observableArrayList();
        try {
            String sql = "SELECT MONTHNAME(Start) as bymonth , " +
                    "COUNT(*) as total from appointments " +
                    "group by MONTHNAME(Start);";
            PreparedStatement ps = JDBC.getConnection().
                    prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String months = rs.getString("bymonth");
                int total = rs.getInt("total");
                monthModel appointments = new monthModel(months, total);
                appointmentByMonthList.add(appointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentByMonthList;
    }

    /**Puts a new appointment into the appointment table from user's input
     @param appointmentType
     @param appointmentTitle
     @param customerID
     @param userID
     @param appointmentDescription
     @param appointmentEnd
     @param appointmentLocation
     @param appointmentStart
     @param contactName
     .*/
    public static void createNewAppointment2(
            String appointmentTitle,
            String appointmentDescription,
            String appointmentLocation,
            String contactName,
            String appointmentType,
            Timestamp appointmentStart,
            Timestamp appointmentEnd,
            int customerID,
            int userID) throws SQLException {
        try {
            PreparedStatement ps = JDBC.connection.prepareStatement(
                    "SELECT " +
                    "Title, " +
                    "Description, " +
                    "a.Location, " +
                    "Contact_Name, " +
                    "Type, " +
                    "Start, " +
                    "End, " +
                    "Customer_ID, " +
                    "User_ID, " +
                    "c.Contact_Name FROM appointments a\n" +
                    "INNER JOIN contacts c on a.Location = " );
            ps.setString(1, appointmentTitle);
            ps.setString(2, appointmentDescription);
            ps.setString(3, appointmentLocation);
            ps.setString(4, contactName);
            ps.setString(5, appointmentType);
            ps.setTimestamp(6, appointmentStart);
            ps.setTimestamp(7, appointmentEnd);
            ps.setInt(8,customerID);
            ps.setInt(9, userID);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<appointmentModel> lookupAppointment
            (String partialTitle,
             ObservableList<appointmentModel> appointments) {
        ObservableList<appointmentModel> filteredAppointments =
                FXCollections.observableArrayList();
        for (appointmentModel appointment : appointments) {
            String A = Integer.toString(appointment.getAppointmentID());
            if (appointment.getTitle().toLowerCase().
                    contains(partialTitle.toLowerCase())) {
                filteredAppointments.add(appointment);
            } else if (A.contains(partialTitle)) {
                filteredAppointments.add(appointment);
            }
        }
        return filteredAppointments;
    }

}



























































































