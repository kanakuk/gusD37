package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.contactModel;
import model.customerModel;
import model.userModel;
import query.appointmentQuery;
import query.contactQuery;
import query.customerQuery;
import query.userQuery;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Collections;
import java.util.ResourceBundle;
import model.*;

/**Initializes the modifyAppointment form.*/
public class modifyAppointmentController implements Initializable {
    public TextField appointmentIdField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public ComboBox<contactModel> contactBox;
    public TextField typeField;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public ComboBox startTimeBox;
    public ComboBox endTimeBox;
    public ComboBox customerIdBox;
    public ComboBox userIdBox;
    private appointmentModel selectedAppointment;
    private ZoneId zoneID = ZoneId.of("UTC");
    private ZoneId zoneIdEasternStandardTime = ZoneId.of("America/New_York");
    private ZoneId zoneIdDefault = ZoneId.systemDefault();
    private ObservableList<contactModel> selectContact = FXCollections.observableArrayList();

    /** This method initializes the combo boxes and date pickers.
     Lambdas 1 easily limit the user from modifying appointments on the
     weekend.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedAppointment = appointmentsController.appointmentModify();
        populateContactBox();
        populateCustomerBox();
        populateUserBox();
        //lambda1
        startDatePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate ld, boolean empty) {
                super.updateItem(ld, empty);
                setDisable(empty
                        || ld.compareTo(LocalDate.now()) < 0
                        || ld.getDayOfWeek() == DayOfWeek.SATURDAY
                        || ld.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });

        endDatePicker.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate ld, boolean empty) {
                super.updateItem(ld, empty);
                setDisable(empty
                        || ld.compareTo(LocalDate.now()) < 0
                        || ld.getDayOfWeek() == DayOfWeek.SATURDAY
                        || ld.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });

        ObservableList<String> appointmentTimes =
                FXCollections.observableArrayList();

        LocalTime startTime = LocalTime.of(8, 00);
        LocalTime endTime = LocalTime.of(22, 00);

        appointmentTimes.add((String.valueOf(startTime)));
        while (startTime.isBefore(endTime)){
            startTime = startTime.plusMinutes(15);
            appointmentTimes.add((String.valueOf(startTime)));
        }
        startTimeBox.setItems(appointmentTimes);
        endTimeBox.setItems(appointmentTimes);

        ObservableList<customerModel> customerList =
                customerQuery.getAllCustomers();
        ObservableList<Integer> customerID =
                FXCollections.observableArrayList();

        customerList.forEach(Customer ->
                customerID.add(Customer.getCustomerID()));
        customerIdBox.setItems(customerID);


        ObservableList<userModel> userList = userQuery.getUserList();
        ObservableList<Integer> userID = FXCollections.observableArrayList();

        userList.forEach(user -> userID.add(user.getUserId()));
        Collections.sort(userID);
        userIdBox.setItems(userID);

        //Imports selected appointment into the modify screen.
        appointmentIdField.setText(String.valueOf
                (selectedAppointment.getAppointmentID()));
        titleField.setText(String.valueOf
                (selectedAppointment.getTitle()));
        descriptionField.setText(String.valueOf
                (selectedAppointment.getAppointmentID()));
        locationField.setText(String.valueOf
                (selectedAppointment.getLocation()));
        contactBox.setValue(contactQuery.getContact(selectedAppointment.getContact()));
        typeField.setText(String.valueOf
                (selectedAppointment.getType()));
        startDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
        startTimeBox.setValue(selectedAppointment.
                getStart().toLocalTime());
        endDatePicker.setValue(selectedAppointment.getStart().toLocalDate());
        endTimeBox.setValue(String.valueOf
                (selectedAppointment.getEnd().toLocalTime()));
        customerIdBox.setValue
                (selectedAppointment.getCustomerID());
        userIdBox.setValue
                (selectedAppointment.getUserID());
    }

    /**Puts the contact names in the their list.*/
    public void populateContactBox() {
        ObservableList<contactModel> contactList =
                FXCollections.observableArrayList();
        try {
            contactList =
                    contactQuery.getContactList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        this.contactBox.setItems(contactList);
    }

    /**Puts the customers in the their list.*/
    public void populateCustomerBox() {
        ObservableList<Integer> customerBox =
                FXCollections.observableArrayList();
        try {
            ObservableList<customerModel> selectCustomerID =
                    customerQuery.getAllCustomers();
            for (customerModel customer : selectCustomerID) {
                customerBox.add(customer.getCustomerID());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerIdBox.setItems(customerBox);
    }

    /**Puts the users in their list.*/
    public void populateUserBox() {
        ObservableList<Integer> userBox =
                FXCollections.observableArrayList();
        try {
            ObservableList<userModel> selectUserID =
                    userQuery.getUserList();
            for (userModel user : selectUserID) {
                userBox.add(user.getUserId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        userIdBox.setItems(userBox);
    }

    /**Saves the user's modified input to the calendar.
     @param actionEvent triggered when save button is pressed. */
    public void saveOnAction(ActionEvent actionEvent) {
        try {
            int appointmentID = Integer.parseInt(appointmentIdField.getText());
            String appointmentTitle = titleField.getText();
            String appointmentDescription = descriptionField.getText();
            String appointmentLocation = locationField.getText();
            int contactID = contactBox.getValue().getContactID();
            String appointmentType = typeField.getText();

            LocalDateTime appointmentStart =
                    LocalDateTime.of(startDatePicker.getValue(),
                            LocalTime.parse(startTimeBox.
                                    getSelectionModel().getSelectedItem().toString()));
            LocalDateTime appointmentEnd =
                    LocalDateTime.of(endDatePicker.getValue(),
                            LocalTime.parse(endTimeBox.
                                    getSelectionModel().getSelectedItem().toString()));
            ZonedDateTime startUTC = appointmentStart.atZone(zoneID).
                    withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endUTC = appointmentEnd.atZone(zoneID).
                    withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp startTimeStamp = Timestamp.valueOf
                    (startUTC.toLocalDateTime());
            Timestamp endTimeStamp = Timestamp.valueOf
                    (endUTC.toLocalDateTime());

            int customerID = Integer.parseInt(customerIdBox.getValue().toString());
            int userID = Integer.parseInt(userIdBox.getValue().toString());

            try {
                appointmentQuery.modifySaveAppointment(
                        appointmentID,
                        appointmentTitle,
                        appointmentDescription,
                        appointmentLocation,
                        contactID,
                        appointmentType,
                        startTimeStamp,
                        endTimeStamp,
                        customerID,
                        userID);
                Parent root = FXMLLoader.load
                        (getClass().getResource("/view/appointment.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setScene(scene);
                stage.show();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Takes the user back to the appointment screen
     @param actionEvent triggered when cancel button is pressed. */
    public void cancelOnAction(ActionEvent actionEvent) throws Exception{
        Parent root = FXMLLoader.load
                (getClass().getResource("/view/appointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setScene(scene);
        stage.show();
    }
}







