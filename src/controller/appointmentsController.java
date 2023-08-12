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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.appointmentModel;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import query.appointmentQuery;

/**Initializes the appointment table.*/
public class appointmentsController implements Initializable {
    public TableView appointmentsTable;
    public TableColumn appointmentId1;
    public TableColumn title2;
    public TableColumn description3;
    public TableColumn location4;
    public TableColumn contact5;
    public TableColumn type6;
    public TableColumn start7;
    public TableColumn end8;
    public TableColumn customerId9;
    public TableColumn userID10;
    public RadioButton allRadioID;
    public RadioButton monthlyID;
    public RadioButton weeklyID;
    public ToggleGroup toggleGroup;
    private static appointmentModel selectedAppointment;
    public TextField fxidSearch;

    public static appointmentModel appointmentModify(){
        return selectedAppointment;
    }

    /**Populates the appointment table with current info
     * in each column.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<appointmentModel> appointmentList = appointmentQuery.getAllAppointments();
        appointmentsTable.setItems(appointmentList);
        appointmentId1.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        title2.setCellValueFactory(new PropertyValueFactory<>("title"));
        description3.setCellValueFactory(new PropertyValueFactory<>("description"));;
        location4.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact5.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type6.setCellValueFactory(new PropertyValueFactory<>("type"));
        start7.setCellValueFactory(new PropertyValueFactory<>("start"));
        end8.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerId9.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userID10.setCellValueFactory(new PropertyValueFactory<>("userID"));
        appointmentsTable.getSortOrder().addAll(appointmentId1);
    }

    /**Allows all apointments to be viewable.
     @param actionEvent triggered when its radio button
     is selected.*/
    public void onActionAll(ActionEvent actionEvent) {
        if (allRadioID.isSelected()) {
            ObservableList<appointmentModel>
                    appointmentList = appointmentQuery.getAllAppointments();
            appointmentsTable.setItems(appointmentList);
        }
    }

    /**Allows only monthly apointments to be viewable.
     @param actionEvent triggered when its radio button
     is selected.*/
    public void onActionMonth(ActionEvent actionEvent) {
        if (monthlyID.isSelected()) {
            ObservableList<appointmentModel> appointmentList =
                    appointmentQuery.getAllAppointments();
            ObservableList<appointmentModel> monthlyAppointment
                    = FXCollections.observableArrayList();
            appointmentsTable.setItems(appointmentList);
            if (appointmentList != null) {
                appointmentList.forEach(appointment -> {
                    if (appointment.getEnd().isAfter
                            (LocalDateTime.now().minusMonths(1))
                            && appointment.getEnd()
                            .isBefore(LocalDateTime.now().plusMonths(1))) {
                        monthlyAppointment.add(appointment);
                    }
                    appointmentsTable.setItems(monthlyAppointment);
                });
            }
        }
    }

    /**Allows only weekly apointments to be viewable.
     @param actionEvent triggered when its radio button
     is selected.*/
    public void onActionWeekly(ActionEvent actionEvent) {
        if (weeklyID.isSelected()) {
            ObservableList<appointmentModel> appointmentList =
                    appointmentQuery.getAllAppointments();
            ObservableList<appointmentModel> weeklyAppointment
                    = FXCollections.observableArrayList();
            appointmentsTable.setItems(appointmentList);
            if (appointmentList != null) {
                appointmentList.forEach(appointment -> {
                    if (appointment.getEnd().isAfter
                            (LocalDateTime.now().minusWeeks(1))
                            && appointment.getEnd()
                            .isBefore(LocalDateTime.now().plusWeeks(1))) {
                        weeklyAppointment.add(appointment);
                    }
                    appointmentsTable.setItems(weeklyAppointment);
                });
            }
        }
    }

    /**Deletes the chosen appointment from its list.
     @param actionEvent triggered when delete button is pressed.*/
    public void onActionDelete(ActionEvent actionEvent)
            throws IOException, SQLException {
        selectedAppointment = (appointmentModel)
                appointmentsTable.getSelectionModel().
                        getSelectedItem();
        ObservableList<appointmentModel> allAppointments =
                appointmentQuery.getAllAppointments();

        try {
            int appointmentID = selectedAppointment.getAppointmentID();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("are you sure?");
            alert.setContentText("Are you sure you want to delete " +
                    "the selected appointment? \n" +
                    "Appointment ID: " +
                    selectedAppointment.getAppointmentID() +
                    "\n" + "Appointment Type: " +
                    selectedAppointment.getType());
            Optional<ButtonType> deleteConfirmation = alert.showAndWait();
            if (deleteConfirmation.isPresent()
                && deleteConfirmation.get() == ButtonType.OK) {
                appointmentQuery.deleteAppointment(appointmentID);
                appointmentsTable.setItems(allAppointments);
                String sql =
                        "DELETE FROM appointments WHERE Appointment_ID = ?";
                PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
                ps.setInt(1, appointmentID);
                ps.executeUpdate();

                Parent root = FXMLLoader.load
                        (getClass().getResource("/view/appointment.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 400);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**Takes the user to the add appointment screen.
     @param actionEvent triggered when add button is pressed.*/
    public void onActionAdd(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load
                (getClass().getResource("/view/addAppointment.fxml"));
        Stage stage = (Stage)
                ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 617, 464);
        stage.setScene(scene);
        stage.show();
    }

    /**Once the user chooses a row and presses the delete button,
     the selection is deleted.
     @param actionEvent triggered when delete button is pressed.*/
    public void onActionModify(ActionEvent actionEvent) throws IOException {
        if (appointmentsTable.getSelectionModel().getSelectedItem() == null){
            return;
        }
        selectedAppointment = (appointmentModel) appointmentsTable.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load
                (getClass().getResource("/view/modifyAppointment.fxml"));
        Stage stage = (Stage)
                ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 617, 464);
        stage.setScene(scene);
        stage.show();
    }

    public void onActionSearch(ActionEvent actionEvent) {
        String string = fxidSearch.getText();
        ObservableList<appointmentModel> searchAppointment =
                appointmentQuery.lookupAppointment
                        (string, appointmentQuery.getAllAppointments());
        appointmentsTable.setItems(searchAppointment);
    }
}
























































