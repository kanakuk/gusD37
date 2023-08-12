package controller;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import query.*;

/**Gives ability of the report.fxml to be interacted with*/
public class reportController implements Initializable {
    public TableView reportTable;
    public TableColumn appointmentId1;
    public TableColumn title2;
    public TableColumn description3;
    public TableColumn type4;
    public TableColumn start5;
    public TableColumn end6;
    public TableColumn customerId7;
    public TableView appointmentTypeTable;
    public TableColumn typeB1;
    public TableColumn totalAppointmentsB2;
    public TableView appointmentMonthTable;
    public TableColumn monthC1;
    public TableColumn appointmentsTotalC2;
    public ComboBox contactCombo;
    public TableView appointmentLocationTable;
    public TableColumn LocationD1;
    public TableColumn appointmentsTotalD2;

    /**
     Initializes the four tables.
     lambda 2 efficiently populates contacts into a combo box
     Populate data in the four tables.
     @param url
     @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<contactModel> contactList =
                contactQuery.getContactList();
        ObservableList<String> contactName =
                FXCollections.observableArrayList();
        //lambda 2
        contactList.forEach(contact -> contactName.add
                (contact.getContactName()));
        Collections.sort(contactName);
        contactCombo.setItems(contactName);

        //Inserts data into second table.
        ObservableList<typeModel> appointmentByType =
                appointmentQuery.getAppointmentsByType();
        appointmentTypeTable.setItems(appointmentByType);
        typeB1.setCellValueFactory(new PropertyValueFactory<>("type"));
        totalAppointmentsB2.setCellValueFactory
                (new PropertyValueFactory<>("total"));

        //Inserts data into third table
        ObservableList<monthModel> appointmentsByMonth =
                appointmentQuery.getAppointmentsByMonth();
        appointmentMonthTable.setItems(appointmentsByMonth);
        monthC1.setCellValueFactory(new PropertyValueFactory<>("months"));
        appointmentsTotalC2.setCellValueFactory(new PropertyValueFactory<>("total"));

        //Inserts data into fourth table
        ObservableList<locationModel> appointmentsByLocation =
                countryQuery.getAppointmentsByLocation();
        appointmentLocationTable.setItems(appointmentsByLocation);
        LocationD1.setCellValueFactory
                (new PropertyValueFactory<>("location"));
        appointmentsTotalD2.setCellValueFactory
                (new PropertyValueFactory<>("total"));

    }

    /**Takes the user to the main menu screen.
     @param actionEvent triggered when cancel button is pressed.*/
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load
                (getClass().getResource("/view/mainMenu.fxml"));
        Stage stage = (Stage)
                ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 328, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**Insert data into the first table.
     @param actionEvent inserts data into first table when
     a contact is selected*/
    public void onActionSelectContact(ActionEvent actionEvent) {
        ObservableList<appointmentModel> appointmentList =
                FXCollections.observableArrayList();
        try {
            String sql = "SELECT " +
                    "Appointment_ID," +
                    "Title," +
                    "Description," +
                    "Location, " +
                    "a.Contact_ID, " +
                    "Type, " +
                    "Start, " +
                    "End, " +
                    "Customer_ID, " +
                    "User_ID, " +
                    "c.Contact_Name FROM appointments a\n" +
                    "INNER JOIN contacts c on a.Contact_ID = " +
                    "c.Contact_ID WHERE c.Contact_Name = '" +
                    contactCombo.getValue() + "'";
            PreparedStatement ps = JDBC.getConnection().
                    prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int appointmentID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                int contact = rs.getInt("Contact_ID");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").
                        toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").
                        toLocalDateTime();
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                appointmentModel appointment = new appointmentModel(
                        appointmentID,
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
            reportTable.setItems(appointmentList);
            appointmentId1.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            title2.setCellValueFactory(new PropertyValueFactory<>("title"));
            description3.setCellValueFactory(new PropertyValueFactory<>("description"));
            type4.setCellValueFactory(new PropertyValueFactory<>("type"));
            start5.setCellValueFactory(new PropertyValueFactory<>("start"));
            end6.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerId7.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            reportTable.getSortOrder().addAll(appointmentId1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}



















































