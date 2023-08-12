package controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import model.*;
import query.*;

/** Allows the user to add an appointment to the schedule.*/
public class addAppointmentController implements Initializable {
    public TextField appointmentIdField;
    public TextField titleField;
    public TextField descriptionField;
    public TextField locationField;
    public ComboBox<contactModel> contactBox;
    public TextField typeField;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public ComboBox <String> startTimeBox;
    public ComboBox <String> endTimeBox;
    public ComboBox customerIdBox;
    public ComboBox userIdBox;
    private ZoneId zoneID = ZoneId.of("UTC");
    private ZoneId zoneIdEasternStandardTime = ZoneId.of("America/New_York");
    private ZoneId zoneIdDefault = ZoneId.systemDefault();

    /** This method initializes the combo boxes and date pickers.
     * Lambdas easily limit the user from making appointments on the
     * weekend.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateContactBox();
        populateCustomerBox();
        populateUserBox();

        ObservableList<String> appointmentTimes =
                FXCollections.observableArrayList();
        //limit choosing of time to between 8am and 10pm
        LocalTime startTime = LocalTime.of(8, 00);
        LocalTime endTime = LocalTime.of(22, 00);

        appointmentTimes.add((String.valueOf(startTime)));
        while (startTime.isBefore(endTime)){
            startTime = startTime.plusMinutes(15);
            appointmentTimes.add((String.valueOf(startTime)));
        }
        startTimeBox.setItems(appointmentTimes);
        endTimeBox.setItems(appointmentTimes);
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
        //lambda2
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

    /**Saves the user's input to the calendar.
    @param actionEvent triggered when save button is pressed. */
    public void saveOnAction(ActionEvent actionEvent) {
        boolean appointmentisValid = errorValidation();
        if (appointmentisValid) {
            try {
                String appointmentTitle = titleField.getText();
                String appointmentDescription = descriptionField.getText();
                String appointmentLocation = locationField.getText();
                int contactID = contactBox.getValue().getContactID();
                String appointmentType = typeField.getText();
                LocalDateTime appointmentStart =
                        LocalDateTime.of(startDatePicker.getValue(),
                                LocalTime.parse(startTimeBox.
                                        getSelectionModel().getSelectedItem()));
                LocalDateTime appointmentEnd =
                        LocalDateTime.of(endDatePicker.getValue(),
                                LocalTime.parse(endTimeBox.
                                        getSelectionModel().getSelectedItem()));
                int customerID = (int) customerIdBox.getValue();
                int userID = (int) userIdBox.getValue();

                ZonedDateTime startUTC = appointmentStart.atZone(zoneID).
                        withZoneSameInstant(ZoneId.of("UTC"));
                ZonedDateTime endUTC = appointmentEnd.atZone(zoneID).
                        withZoneSameInstant(ZoneId.of("UTC"));
                Timestamp startTimeStamp = Timestamp.valueOf
                        (startUTC.toLocalDateTime());
                Timestamp endTimeStamp = Timestamp.valueOf
                        (endUTC.toLocalDateTime());
                try {
                    appointmentQuery.createNewAppointment(
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
                            (getClass().getResource
                                    ("/view/appointment.fxml"));
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
    }

    /**Takes the user back to the appointment screen
     @param actionEvent triggered when cancel button is pressed. */
    public void cancelOnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load
                (getClass().getResource("/view/appointment.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 400);
        stage.setScene(scene);
        stage.show();
    }

    /**Prevents user from scheduling two appointments during
     * the same time
     * Prevents user from making the start and end dates
     * from being on different days.*/
    private boolean errorValidation() {
        try {
            LocalDateTime appointmentStart = LocalDateTime.of
                    (startDatePicker.getValue(), LocalTime.parse
                            (startTimeBox.getSelectionModel().
                                    getSelectedItem()));
            LocalDateTime appointmentEnd = LocalDateTime.of
                    (endDatePicker.getValue(), LocalTime.parse
                            (endTimeBox.getSelectionModel().
                                    getSelectedItem()));
            ZonedDateTime startUTC = appointmentStart.atZone(zoneID).
                    withZoneSameInstant(ZoneId.of("UTC"));
            ZonedDateTime endUTC = appointmentEnd.atZone(zoneID).
                    withZoneSameInstant(ZoneId.of("UTC"));
            Timestamp startTimeStamp = Timestamp.valueOf
                    (startUTC.toLocalDateTime());
            Timestamp endTimeStamp = Timestamp.valueOf
                    (endUTC.toLocalDateTime());
            try {
                ObservableList<appointmentModel> appointments =
                        appointmentQuery.pullAppointmentsByCustomerID
                                ((Integer) customerIdBox.
                                        getSelectionModel().getSelectedItem());
                for (appointmentModel currentAppointments : appointments) {
                    LocalDateTime currentAppointmentStart =
                            currentAppointments.getStart();
                    LocalDateTime currentAppointmentEnd =
                            currentAppointments.getEnd();
                    Timestamp appointmentStampStart = Timestamp.valueOf
                            (currentAppointmentStart);
                    Timestamp appointmentStampEnd = Timestamp.valueOf
                            (currentAppointmentEnd);
                    LocalDate startDate = startDatePicker.getValue();
                    LocalDate endDate = endDatePicker.getValue();
                    if ((startTimeStamp.after(appointmentStampStart) &&
                            startTimeStamp.before(appointmentStampEnd)) ||
                            (endTimeStamp.after(appointmentStampStart) &&
                                    endTimeStamp.before(appointmentStampEnd)) ||
                            (startTimeStamp.before(appointmentStampEnd) &&
                                    endTimeStamp.after(appointmentStampStart)) ||
                            (startTimeStamp.equals(appointmentStampStart) &&
                                    endTimeStamp.equals(appointmentStampEnd))||
                            startTimeStamp.equals(appointmentStampStart) ||
                            endTimeStamp.before(startTimeStamp) ||
                            endDate.isAfter(startDate)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Appointment Date Or Time Error!");
                        alert.setContentText("New appointment must not overlap " +
                                "with existing appointment! Appointment start " +
                                "and end dates must be on the same day!");
                        alert.showAndWait();
                        return false;
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}


















































